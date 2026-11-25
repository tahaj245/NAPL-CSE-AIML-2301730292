(ns java-interop
  (:import (java.util ArrayList HashMap Collections)))


(println "=== 1. Static methods ===")

(println "Math/abs -7         =>" (Math/abs -7))
(println "Math/max 10 25      =>" (Math/max 10 25))
(println "Math/sqrt 144.0     =>" (Math/sqrt 144.0))
(println "(. Math pow 2 10)   =>" (. Math pow 2 10))
(println "Integer/parseInt    =>" (Integer/parseInt "42"))

(let [start (System/currentTimeMillis)
      _     (reduce + (range 1000000))
      end   (System/currentTimeMillis)]
  (println "System/currentTimeMillis now =>" end)
  (println "Elapsed for sum of 1e6 numbers (ms) =>" (- end start)))

(println "Math/PI             =>" Math/PI)
(println "Integer/MAX_VALUE   =>" Integer/MAX_VALUE)


(println "\n=== 2. Creating Java objects ===")

(def alist (new ArrayList))        ; (new ClassName)
(def hmap  (HashMap.))             ; ClassName.
(def sb    (StringBuilder. "Hello"))

(println "ArrayList created:" alist)
(println "HashMap created:  " hmap)
(println "StringBuilder:    " (str sb))


(println "\n=== 3. Instance methods ===")

(.add alist "Clojure")
(.add alist "Java")
(.add alist "JVM")
(println "ArrayList after adds:" alist)
(println ".get 1          =>" (.get alist 1))
(println ".size           =>" (.size alist))
(println ".contains \"JVM\" =>" (.contains alist "JVM"))
(.remove alist "Java")
(println "After .remove \"Java\":" alist)

(.put hmap "one" 1)
(.put hmap "two" 2)
(.put hmap "three" 3)
(println "HashMap after puts:" hmap)
(println ".get \"two\"          =>" (.get hmap "two"))
(println ".containsKey \"four\" =>" (.containsKey hmap "four"))
(println ".keySet             =>" (.keySet hmap))

(.append sb ", ")
(.append sb "Interop!")
(println "StringBuilder .toString =>" (.toString sb))
(println "Reversed via .. macro   =>" (.. (StringBuilder. "interop") reverse toString))

(def nums (doto (ArrayList.)
            (.add 5) (.add 3) (.add 9) (.add 1)))
(Collections/sort nums)
(println "Sorted ArrayList:" nums)

(println ".toUpperCase   =>" (.toUpperCase "clojure"))
(println ".length        =>" (.length "interoperability"))
(println ".substring 0 5 =>" (.substring "interoperability" 0 5))


(println "\n=== 4. Collection conversion ===")


(def java-list (doto (ArrayList.) (.add 10) (.add 20) (.add 30)))
(def clj-vec (vec java-list))
(def clj-set (set java-list))
(println "ArrayList -> vector:" clj-vec (type clj-vec))
(println "ArrayList -> set:   " clj-set (type clj-set))

(def java-map (doto (HashMap.) (.put "a" 1) (.put "b" 2)))
(def clj-map  (into {} java-map))
(println "HashMap -> map:     " clj-map (type clj-map))
(println "With keyword keys:  "
         (into {} (map (fn [[k v]] [(keyword k) v]) java-map)))

;; Clojure -> Java
(def new-java-list (ArrayList. [1 2 3 4 5]))
(.add new-java-list 6)
(println "vector -> ArrayList:" new-java-list (type new-java-list))

(def new-java-map (HashMap. {"x" 100 "y" 200}))
(println "map -> HashMap:     " new-java-map (type new-java-map))

(def int-arr (int-array [3 1 2]))
(println "seq -> int[]:    " (seq int-arr) "length:" (alength int-arr))
(def str-arr (into-array String ["red" "green" "blue"]))
(println "seq -> String[]: " (vec str-arr))
(println "Java array -> vector:" (vec str-arr))

;; Clojure collections already implement Java interfaces
(println "vector is a java.util.List? =>" (instance? java.util.List [1 2 3]))
(println "map is a java.util.Map?      =>" (instance? java.util.Map {:a 1}))
(println "(.size [1 2 3])              =>" (.size [1 2 3]))

(println "\nDone.")