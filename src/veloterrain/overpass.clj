(ns veloterrain.overpass
   (:require [org.httpkit.client :as http]))

(def overpass-url "http://overpass-api.de/api/interpreter?data=")

(def base-is-in-query "[out:json]")

(defn is-in [[lat lon]]
  (str "is_in(" lat ", " lon ");"))

(defn is-ins [points]
  (apply str (map is-in points)))

(defn make-is-in-query [points]
  (str ))

(defn around [[lat lon]]
  (str "node(around.a:25, " lat "," lon ");"))

(defn make-arounds [points]
  (apply str (map around points)))

(defn complex-around [[lat lon]]
  (str "(node(around:25, " lat "," lon ") - "
       "(node(around:25, " lat "," lon ") - "
       "(way(around:25, " lat "," lon ")[highway];>)));"))

(defn make-compl-arounds [points]
  (apply str (map complex-around points)))

(defn overpass-query [str]
  (http/get ()))
