(ns clj-strava.easy-tests
  (:require [clj-strava.pure :refer :all]
            [clj-strava.polyline :as poly]
            [clojure.test :refer :all])
  (:use midje.sweet))


(def points [{:longitude 0.5 :latitude 1.0}
             {:longitude -0.5 :latitude 0.5}
             {:longitude 1.2 :latitude 0.1}])

(fact "Can translate points into string for leaflet"
      (stringify-points points)    =>   "[1.0, 0.5], [0.5, -0.5], [0.1, 1.2]")

(fact "Can calculate bounding box for some points"
      (box points)     =>     [[0.1 -0.5]
                               [0.1 1.2]
                               [1.0 -0.5]
                               [1.0 1.2]])

(fact "Can calulate box size for some points"
      (box-size points)   =>     [0.9 1.7])

(fact "Can find centre of a bounding box"
      (centre-box points) =>     [0.55 0.35])

(fact "Calulates zoom levels"
      (zoom-level [(rads 170.1022) pi2])               =>         [0.0 0.0]
      (zoom-level [(rads 0.0415) (rads 0.0879)])       =>         (just (roughly 12.0) (roughly 12.0))
      (zoom-level [(rads 42.5256) (rads 90)])          =>         (just (roughly 2.0) (roughly 2.0)))

(def poly-string "y`tsHnhv[gI|FoAfF`@nC~IhBtPgAvDzBjGdb@R`U~AbI~M~D|WF`QcGzIBnh@pIpR`[jd@zUnHpOt]jIxd@bYvt@|GsKba@qAvQuJve@aHbNiIdBwEnFMci@qKeaAwAq[{VmN}\\kIgHgO_OkKgUcJmRwZ}i@oIaIDmOzFeYOqMiE{AeIW}ToGka@{DgC{PhAuIeCGeDtBeGpEwD")

(def  raw-points (poly/decode poly-string))

(fact "Correct calculation of OSM tile"
      (tile-numbers (apply rads (centre-box raw-points)) 12)   =>   [1994 1379])

(def b-size (-> poly-string poly/decode box-size))
(centre-box raw-points)
(zoom-level (apply rads b-size))


(->> points box-size (apply rads) zoom-level round-vec (map dec)  (apply max))
