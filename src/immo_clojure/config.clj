(ns immo-clojure.config (:require  [cheshire.core :as cc] [cemerick.url :refer (url)]))



(defn load-config []
  (let [json-data (slurp "resources/config.json")]
    (cc/parse-string json-data true)))

(clojure.pprint/pprint (load-config))

(def config-data (load-config))

(defn get-host [map-config]
  (get (url (get map-config :url)) :host))


(def config-full (map #(assoc % :root (get-host %)) config-data))

(clojure.pprint/pprint config-full)
(cc/parse-string ((first config-full) :xpath_listing))