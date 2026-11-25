

(println "===== 1. LIST =====")
(def my-list '(10 20 30 40))
(println "Original list      :" my-list)
(println "first              :" (first my-list))
(println "rest               :" (rest my-list))
(def new-list (conj my-list 5))          ; conj adds to the FRONT of a list
(println "conj 5 -> new list :" new-list)
(println "Original (unchanged):" my-list)

(println)
(println "===== 2. VECTOR =====")
(def my-vec [1 2 3 4])
(println "Original vector    :" my-vec)
(println "get by index (nth 2):" (nth my-vec 2))
(println "get by index (get 0):" (get my-vec 0))
(println "count              :" (count my-vec))
(def new-vec (conj my-vec 5))            ; conj adds to the END of a vector
(println "conj 5 -> new vec  :" new-vec)
(println "Original (unchanged):" my-vec)

(println)
(println "===== 3. MAP =====")
(def my-map {:name "Asha" :age 20})
(println "Original map       :" my-map)
(println "get :name          :" (get my-map :name))
(def map2 (assoc my-map :city "Delhi"))  ; add / update a key
(println "assoc :city        :" map2)
(def map3 (dissoc map2 :age))            ; remove a key
(println "dissoc :age        :" map3)
(println "Original (unchanged):" my-map)

(println)
(println "===== 4. SET =====")
(def my-set #{1 2 3})
(println "Original set       :" my-set)
(def set2 (conj my-set 4))
(println "conj 4             :" set2)
(def set3 (disj set2 2))
(println "disj 2             :" set3)
(println "contains? 3        :" (contains? my-set 3))
(println "contains? 9        :" (contains? my-set 9))
(println "Original (unchanged):" my-set)