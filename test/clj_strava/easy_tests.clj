(ns clj-strava.easy-tests
  (:require [clj-strava.polyline :as poly]
            [clojure.test :refer :all]))

(poly/decode "y`tsHnhv[gI|FoAfF`@nC~IhBtPgAvDzBjGdb@R`U~AbI~M~D|WF`QcGzIBnh@pIpR`[jd@zUnHpOt]jIxd@bYvt@|GsKba@qAvQuJve@aHbNiIdBwEnFMci@qKeaAwAq[{VmN}\\kIgHgO_OkKgUcJmRwZ}i@oIaIDmOzFeYOqMiE{AeIW}ToGka@{DgC{PhAuIeCGeDtBeGpEwD")

(def points [{:longitude 0.5 :latitude 1}
             {:longitude -0.5 :latitude 0.5}
             {:longitude 1.2 :latitude 0.1}])

(deftest finding-bounding-box
         (is 
           (= [[0.1 -0.5]
               [0.1 1.2]
               [1.2 -0.5]
               [1.2 1.2]])))