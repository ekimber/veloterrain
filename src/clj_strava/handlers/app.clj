(ns clj-strava.handlers.app
  (:require [clj-strava.tmpls :as tmpl]
            [clojure.tools.logging :as log]
            [clj-strava.strava :as strava]
            [clj-strava.polyline :as poly]))

(defn access_token [req]
  ((:query-params req) "access_token"))

(defn show-landing [req]
  (tmpl/landing {:user-agent (get-in req [:headers "user-agent"])
                 :title "Velo Terrain"
                 :list ["list item 1"
                        "list item 2"]}))

(defn get-access-token [req]
  (-> req :params :access-token))

(defn with-token [req f]
  (f (get-access-token req)))

(defn show-user-home [req]
  (tmpl/user {:access-token (get-access-token req)}))

(defn activities [req]
  (with-token req strava/activities))

(defn show-activities [req]
  (tmpl/activities {:activities (activities req)
                    :access-token (get-access-token req)}))

(defn show-activity [id req]
  (let [activity (strava/activity id (get-access-token req))]
    (tmpl/activity (merge
                    activity
                    {:polyline (-> activity :map :polyline poly/decode vec)}))))
