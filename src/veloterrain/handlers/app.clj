(ns veloterrain.handlers.app
  (:require [clojure.core.async :refer [<!!]]
            [veloterrain.tmpls :as tmpl]
            [clojure.tools.logging :as log]
            [clj-strava.api :as strava]
            [veloterrain.polyline :as poly]
            [veloterrain.pure :refer :all]))

(defn access_token [req]
  ((:query-params req) "access_token"))

(defn show-landing [req]
  (tmpl/landing {:user-agent (get-in req [:headers "user-agent"])
                 :title "Velo Terrain"
                 :list ["list item 1"
                        "list item 2"]}))

(defn get-access-token [req]
  (-> req :params :access-token))

(defn show-user-home [req]
  (tmpl/user {:access-token (get-access-token req)}))

(defn show-activities [req]
  (tmpl/activities {:activities (<!! (strava/activities (get-access-token req)))
                    :access-token (get-access-token req)}))

(defn show-activity [id req]
  (let [activity (<!! (strava/activity (get-access-token req) {:id id}))
        points (-> activity :map :polyline poly/decode)]
    (tmpl/activity (merge
                    activity
                    {:polyline (-> points vec)
                     :activity-path (str "["(stringify-points points) "]")
                     :map-centre (-> points centre-box commify-vec)
                     :zoom-level (->> points box-size (apply rads) zoom-level round-vec (map dec) (apply max))}))))

(comment
  "The overpass job will return an id and go off and do its work in the background.  Client can then use the id to retrieve
  the result or a progress update if the query has not yet finished running.")
