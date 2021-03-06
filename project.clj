(defproject veloterrain "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main veloterrain.main
;;  :aot [veloterrain.main]
  :uberjar-name "veloterrain-standalone.jar"
  ;; :plugins [[lein-swank "1.4.4"]]
  :dependencies [[org.clojure/clojure "1.7.0-alpha2"]
                 [org.clojure/tools.cli "0.2.2"]
                 [ekimber/clj-strava "0.1.0"]
                 [compojure "1.1.5"]
                 [ring/ring-core "1.1.8"]
                 [org.clojure/data.json "0.2.1"]
                 [http-kit "2.1.16"]
                 ;; [org.fressian/fressian "0.6.3"]
                 ;; for serialization clojure object to bytes
                 ;; [com.taoensso/nippy "1.1.0"]
                 ;; Redis client & message queue
                 ;; [com.taoensso/carmine "1.5.0"]
                 ;; logging,  another option [com.taoensso/timbre "1.5.2"]
                 [org.clojure/tools.logging "0.2.6"]
                 [ch.qos.logback/logback-classic "1.0.1"]
                 ;; template
                 [me.shenfeng/mustache "1.1"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.clojure/math.numeric-tower "0.0.4"]]
  :plugins [[lein-midje "3.1.3"]]
  :profiles {:dev {:dependencies [[midje "1.5.1"]]}})
