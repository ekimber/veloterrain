(ns clj-strava.pure)

(defn longitudes [points]
  (map :longitude points))

(defn latitudes [points]
  (map :latitude points))

(defn box [points]
  (let [longs (longitudes points) lats (latitudes points)]
    [[(min lats) (min longs)]
     [(min lats) (max longs)]
     [(max lats) (min longs)]
     [(max lats) (max longs)]]))