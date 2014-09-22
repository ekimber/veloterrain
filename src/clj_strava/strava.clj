(ns clj-strava.strava
  (:require [org.httpkit.client :as http]
            [clojure.tools.logging :as log]
            [clojure.data.json :as json]
            [clj-strava.polyline :as poly]
            [clj-strava.client :as client])
  (:import [java.net URI URLEncoder])
  (:use     [clojure.core.async :as async :only [chan go >! <!!]]))

(def strava-url "https://www.strava.com")
(def endpoint (str strava-url "/api"))
(def secret "353814fb18b33784240e9bcd4799580b7d44b709")

(defn url-encode [s] (URLEncoder/encode (str s) "utf8"))
(defn auth-header [token] {:headers {"Authorization" (str "Bearer " token)}})


(defn get-body [uri opts]
  (let [response @(http/get uri opts)]
    (log/info "Got: " (:body response))
    (:body response)))

;;TODO pass in secret
(defn swap-tokens [code]
  (:body
   @(http/post (str strava-url "/oauth/token")
               {:form-params
                {:client_id 2792
                 :client_secret (url-encode secret)
                 :code (url-encode code)}})))

(defn access-token [code]
  ((json/read-str (swap-tokens code)) "access_token"))

(defn exchange-tokens [code]
   {:access-token ((access-token code) "access_token")})

(defn activities [token]
  (<!! (client/json-get (str endpoint "/v3/athlete/activities") (auth-header token))))

(defn activity [activity-id token]
  (<!! (client/json-get (str endpoint "/v3/activities/" activity-id) (auth-header token))))