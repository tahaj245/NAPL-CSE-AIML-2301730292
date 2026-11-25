(ns core
  (:require [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]
            [ring.adapter.jetty :refer [run-jetty]]))


(def students
  [{:name "asha"  :marks 82}
   {:name "ravi"  :marks 35}
   {:name "meena" :marks 91}
   {:name "john"  :marks 48}
   {:name "sita"  :marks 29}])

(def xf
  (comp (filter #(>= (:marks %) 40))
        (map #(update % :name (fn [n] (.toUpperCase ^String n))))))   ; Java interop

(defn process [data]
  (let [passed (into [] xf data)
        total  (reduce + (map :marks passed))
        avg    (if (seq passed) (/ total (count passed) 1.0) 0)]
    {:passed  passed
     :count   (count passed)
     :average (Math/round (double avg))                 
     :date    (str (java.time.LocalDate/now))}))        


(def ds (jdbc/get-datasource {:jdbcUrl "jdbc:h2:mem:demo;DB_CLOSE_DELAY=-1"}))

(defn init-db! []
  (try
    (jdbc/execute! ds ["create table if not exists students (name varchar(50), marks int)"])
    (catch Exception e (println "DB init error:" (.getMessage e)))))

(defn save-student! [{:keys [name marks]}]
  (try
    (jdbc/execute-one! ds ["insert into students(name, marks) values(?, ?)" name marks])
    (catch Exception e (println "Insert error:" (.getMessage e)))))

(defn read-students []
  (try
    (jdbc/execute! ds ["select * from students"]
                   {:builder-fn rs/as-unqualified-lower-maps})
    (catch Exception e
      (println "Read error:" (.getMessage e))
      [])))


(defn handler [req]
  (case (:uri req)
    "/results" {:status 200
                :headers {"Content-Type" "text/plain"}
                :body (pr-str {:processed (process students)
                               :from-database (read-students)})}
    {:status 404 :body "Not found. Try /results"}))

(defn -main []
  (init-db!)
  (doseq [s (:passed (process students))]
    (save-student! s)) 
  (println "Server running at http://localhost:3001/results")
 (run-jetty handler {:port 3001}))