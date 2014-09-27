(ns clj-strava.strava
  (:require [clojure.string :as str]
            [org.httpkit.client :as http]
            [clojure.tools.logging :as log]
            [clojure.data.json :as json]
            [clj-strava.client :as client])
  (:import [java.net URI URLEncoder])
  (:use     [clojure.core.async :as async :only [chan go >! <!!]]))

(def strava-url "https://www.strava.com")
(def endpoint (str strava-url "/api"))
(def secret "353814fb18b33784240e9bcd4799580b7d44b709")

(defn url-encode [s] (URLEncoder/encode (str s) "utf8"))
(defn auth-header [token] {:headers {"Authorization" (str "Bearer " token)}})

;;TODO pass in secret as local config.  It really shouldn't be visible in github.
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

(defn replace-keywords
  "Replace url params like so: /v3/activities/:id {:id 123} -> /v3/activities/123"
  [url keymap]
  (if (empty? keymap)
    url
    (let [k (first (keys keymap))]
      (replace-keywords
       (str/replace url (re-pattern (str k)) (str (get keymap k)))
       (dissoc keymap k)))))

(defn url-builder
  ([url]
   (str endpoint url))
  ([url params]
   (url-builder (replace-keywords url params))))

;TODO could simplify this macro
(defmacro defapifn
  ([name url]
  `(defn ~name [token#]
       (<!! (client/json-get
             (url-builder ~url)
             (auth-header token#)))))
  ([name url & param-names] ;TODO could validate param-names
    `(defn ~name [token# params#]
       (<!! (client/json-get
             (url-builder ~url params#)
             (auth-header token#))))))

#_(defmacro tm [arg & ps]
  `(defn a ~(if ps '[arg f] '[arg]) f))

#_(macroexpand '(tm 1))

(defapifn activities "/v3/athlete/activities")
(defapifn activity "/v3/activities/:id" :id)
(defapifn activity-streams "/v3/activities/:id/streams" :id)

(defapifn athlete "/v3/athlete")
(defapifn athletes "/v3/athletes/:id" :id)
(defapifn friends "/v3/athlete/friends")
(defapifn athlete-friends "/v3/athletes/:id/friends" :id)
(defapifn followers "/v3/athlete/followers")
(defapifn athlete-followers "/v3/athletes/:id/followers" :id)
(defapifn both-following "/v3/athletes/:id/both-following" :id)

(defapifn clubs "/v3/clubs/:id" :id)
(defapifn gear "/v3/gear/:id" :id)

(defapifn segments "/v3/segments/:id" :id)
(defapifn leaderboard "/v3/segments/:id/leaderboard" :id)
(defapifn all-efforts "/v3/segments/:id/all_efforts" :id)

(defapifn uploads "/v3/uploads")

