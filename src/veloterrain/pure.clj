(ns veloterrain.pure
  (:require [clojure.math.numeric-tower :refer [abs expt]]))

(def pi Math/PI)
(def pi2 (* pi 2))

(defn log2 [n]
  (/ (Math/log n) (Math/log 2)))

(defn round-vec [v]
  (map #(Math/round %) v))

(defn commify-vec [v]
  (str  "[" (apply str(interpose "," v)) "]"))

(defn rads
  ([v]
   (if (seq? v)
     (vec (map  #(Math/toRadians %) v))
     (Math/toRadians v)))
  ([a & args]
   (vec (map #(Math/toRadians %) (cons a args)))))

(defn zoom-level
  "Calulate OSM zoom level given box width and height latitude and longitude."
  [[lat lon]]
  [(abs (log2 (/ lat (rads 170.1022))))
   (abs (log2 (/ lon pi2)))])

(defn longitudes [points]
  (map :longitude points))

(defn latitudes [points]
  (map :latitude points))


(defn stringify-points [points]
  (->> points
       (map #(str "[" (:latitude %) ", " (:longitude %) "]"))
       (interpose ", ")
       (apply str)))

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

(defn max-diff [n]
  (- (apply max n) (apply min n)))

(defn box-size [points]
  [(max-diff (latitudes points))
   (max-diff (longitudes points))])

(defn centre [v]
  (let [mini (apply min v) maxi (apply max v)]
    (+ mini (/ (- maxi mini) 2))))

(defn centre-box [points]
  [(centre (latitudes points))
   (centre (longitudes points))])

(defn tile-numbers [[lat lon] zoom]
   (let [n (expt 2 zoom)]
     (round-vec
      [(-> lon (+ pi) (/ pi2) (* n))
       (/ (* n (- 1 (/ (Math/log
                        (+ (Math/tan lat) (/ 1 (Math/cos lat))))
                       pi))) 2)])))
