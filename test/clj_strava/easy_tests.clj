(ns clj-strava.easy-tests
  (:require [clj-strava.pure :refer :all]
            [clojure.test :refer :all])
  (:use midje.sweet))

#_(poly/decode "y`tsHnhv[gI|FoAfF`@nC~IhBtPgAvDzBjGdb@R`U~AbI~M~D|WF`QcGzIBnh@pIpR`[jd@zUnHpOt]jIxd@bYvt@|GsKba@qAvQuJve@aHbNiIdBwEnFMci@qKeaAwAq[{VmN}\\kIgHgO_OkKgUcJmRwZ}i@oIaIDmOzFeYOqMiE{AeIW}ToGka@{DgC{PhAuIeCGeDtBeGpEwD")

(def points [{:longitude 0.5 :latitude 1.0}
             {:longitude -0.5 :latitude 0.5}
             {:longitude 1.2 :latitude 0.1}])

(fact "Can calculate bounding box for some points"
      (box points)     =>     [[0.1 -0.5]
                               [0.1 1.2]
                               [1.0 -0.5]
                               [1.0 1.2]])

(fact "Can find centre of a bounding box"
      (centre-box points) =>     [0.55 0.35])

(fact "Calulates zoom levels"
      (zoom-level [(rads 170.1022) pi2])               =>         [0.0 0.0]
      (zoom-level [(rads 0.0415) (rads 0.0879)])       =>         (just (roughly 12.0) (roughly 12.0))
      (zoom-level [(rads 42.5256) (rads 90)])          =>         (just (roughly 2.0) (roughly 2.0)))
