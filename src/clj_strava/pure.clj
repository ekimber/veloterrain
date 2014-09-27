(ns clj-strava.pure
  (:require [clojure.math.numeric-tower :refer [abs]]))

(def pi Math/PI)
(def pi2 (* pi 2))

(defn log2 [n]
  (/ (Math/log n) (Math/log 2)))

(defn rads
  ([v]
   (if (seq? v)
     (vec (map  #(Math/toRadians %) v))
     (Math/toRadians v)))
  ([a & args]
   (vec (map #(Math/toRadians %) (cons a args)))))

(defn zoom-level [[lat lon]]
  [(abs (log2 (/ lat (rads 170.1022))))
   (abs (log2 (/ lon pi2)))])

(defn longitudes [points]
  (map :longitude points))

(defn latitudes [points]
  (map :latitude points))

(defn box [points]
  (let [lats (latitudes points)
        lons (longitudes points)
        min-lats (apply min lats)
        min-lons (apply min lons)
        max-lats (apply max lats)
        max-lons (apply max lons)]
    [[min-lats min-lons]
     [min-lats max-lons]
     [max-lats min-lons]
     [max-lats max-lons]]))

(defn centre [v]
  (let [mini (apply min v) maxi (apply max v)]
    (+ mini (/ (- maxi mini) 2))))

(defn centre-box [points]
  [(centre (latitudes points))
   (centre (longitudes points))])
