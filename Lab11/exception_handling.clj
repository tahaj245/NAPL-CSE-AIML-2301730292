

(ns exception-handling
  (:import (clojure.lang ExceptionInfo)))

(println "=== 1. try / catch / finally ===")


(defn safe-divide [a b]
  (try
    (println "Dividing" a "by" b)
    (/ a b)
    (catch ArithmeticException e
      (println "Caught ArithmeticException:" (.getMessage e))
      nil)
    (finally
      (println "finally: safe-divide finished\n"))))

(safe-divide 10 2)
(safe-divide 10 0)


(defn string-length [s]
  (try
    (.length s)                       
    (catch NullPointerException e
      (println "Caught NullPointerException: tried to call .length on nil")
      -1)
    (finally
      (println "finally: string-length finished\n"))))

(println "Length of \"Clojure\" =>" (string-length "Clojure"))
(println "Length of nil       =>" (string-length nil))

;; NumberFormatException
(defn parse-number [s]
  (try
    (Integer/parseInt s)
    (catch NumberFormatException e
      (println "Caught NumberFormatException:" (.getMessage e))
      0)
    (finally
      (println "finally: parse-number finished\n"))))

(println "Parsed \"123\" =>" (parse-number "123"))
(println "Parsed \"abc\" =>" (parse-number "abc"))


(try
  (+ 1 (cast Long "not a number"))
  (catch ClassCastException e
    (println "Caught ClassCastException:" (.getMessage e)))
  (finally
    (println "finally: ClassCastException demo finished\n")))


(defn risky [x]
  (try
    (cond
      (= x :arith) (/ 1 0)
      (= x :npe)   (.toUpperCase nil)
      (= x :index) (.get (java.util.ArrayList.) 5)
      :else        "no error")
    (catch ArithmeticException e  (str "arith -> " (.getMessage e)))
    (catch NullPointerException e (str "npe -> null value"))
    (catch IndexOutOfBoundsException e (str "index -> " (.getMessage e)))
    (catch Exception e            (str "other -> " (.getMessage e)))
    (finally (println "finally: risky" x "done"))))

(doseq [k [:arith :npe :index :ok]]
  (println "  result:" (risky k)))


(println "\n=== 2. Custom exceptions with ex-info ===")

(defn validate-age [age]
  (cond
    (not (number? age))
    (throw (ex-info "Age must be a number"
                    {:type :type-error :field :age :value age}))

    (neg? age)
    (throw (ex-info "Age cannot be negative"
                    {:type :validation-error :field :age :value age
                     :hint "Provide a value >= 0"}))

    (> age 150)
    (throw (ex-info "Age is unrealistic"
                    {:type :validation-error :field :age :value age
                     :hint "Provide a value <= 150"}))

    :else age))

(defn withdraw [balance amount]
  (if (> amount balance)
    (throw (ex-info "Insufficient funds"
                    {:type :business-error
                     :balance balance
                     :requested amount
                     :shortfall (- amount balance)}))
    (- balance amount)))


(println "\n=== 3. Recovery with ex-message and ex-data ===")

(defn try-validate [age]
  (try
    (let [ok (validate-age age)]
      (println "Valid age:" ok)
      {:status :ok :age ok})
    (catch ExceptionInfo e
      (println "Caught ExceptionInfo")
      (println "  ex-message:" (ex-message e))
      (println "  ex-data   :" (ex-data e))
      ;; recover based on data in the exception
      (case (:type (ex-data e))
        :validation-error
        (let [fallback (if (neg? (:value (ex-data e))) 0 150)]
          (println "  Recovery  : using fallback age" fallback)
          {:status :recovered :age fallback})
        :type-error
        (do (println "  Recovery  : defaulting to 18")
            {:status :recovered :age 18})
        {:status :failed}))
    (finally
      (println "  finally: validation of" (pr-str age) "complete\n"))))

(println (try-validate 25))
(println (try-validate -5))
(println (try-validate 200))
(println (try-validate "abc"))

;; Recovery in a business scenario
(defn safe-withdraw [balance amount]
  (try
    (withdraw balance amount)
    (catch ExceptionInfo e
      (let [{:keys [shortfall]} (ex-data e)]
        (println (ex-message e) "- short by" shortfall "; withdrawing full balance instead")
        0))))

(println "Balance after withdraw 500 from 1000 =>" (safe-withdraw 1000 500))
(println "Balance after withdraw 500 from 300  =>" (safe-withdraw 300 500))


(defn load-config [s]
  (try
    (Integer/parseInt s)
    (catch NumberFormatException e
      (throw (ex-info "Config value is invalid"
                      {:type :config-error :raw s}
                      e)))))             ; e becomes the cause

(try
  (load-config "port")
  (catch ExceptionInfo e
    (println "Wrapped error:" (ex-message e) (ex-data e))
    (println "Original cause:" (ex-message (ex-cause e)))))

(println "\nDone.")