(ns veloterrain.handlers.api
  (:require [clj-strava.api :as strava]
            [clojure.tools.logging :as log]
            [ring.util.response :as response]))

(defn get-time [req]
  {:time (System/currentTimeMillis)
   :req (merge req {:async-channel nil})})

(defn token-exchange [req]
  (response/redirect
   (str "/home?access-token="
        (strava/access-token ((:query-params req) "code")))))

