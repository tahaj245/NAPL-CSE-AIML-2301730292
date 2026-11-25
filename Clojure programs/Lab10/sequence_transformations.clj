
(require '[clojure.string :as str])

(def nums  [1 2 3 4 5 6 7 8 9 10])
(def words ["clojure" "is" "fun" "and" "powerful"])

(println "Collection 1 (nums) :" nums)
(println "Collection 2 (words):" words)

(println)
(println "===== 1. MAP =====")
(println "Square each number    :" (map #(* % %) nums))
(println "Length of each word   :" (map count words))

(println)
(println "===== 2. FILTER =====")
(println "Only even numbers     :" (filter even? nums))
(println "Words longer than 3   :" (filter #(> (count %) 3) words))

(println)
(println "===== 3. REDUCE =====")
(println "Sum of nums           :" (reduce + nums))
(println "Maximum of nums       :" (reduce max nums))
(println "Total letters in words:" (reduce + (map count words)))

(println)
(println "===== 4. TAKE and DROP =====")
(println "take 3 nums           :" (take 3 nums))
(println "drop 7 nums           :" (drop 7 nums))
(println "take 2 words          :" (take 2 words))
(println "drop 3 words          :" (drop 3 words))

(println)
(println "===== 5. THREADING MACROS =====")

(println "->> evens, squared, summed :"
         (->> nums
              (filter even?)
              (map #(* % %))
              (reduce +)))
(println "->> add 1 to each, take 5  :"
         (->> nums
              (map inc)
              (take 5)))
(println "->> words >2 letters, uppercase, take 3 :"
         (->> words
              (filter #(> (count %) 2))
              (map str/upper-case)
              (take 3)))

(println "->  (5 + 3) * 2 - 4        :"
         (-> 5 (+ 3) (* 2) (- 4)))
(println "->  build a map            :"
         (-> {:a 1} (assoc :b 2) (assoc :c 3)))