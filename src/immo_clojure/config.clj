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

(def test-str (get (first config-full) :xpath_listing))

(defn parse-json-string [s]
  (let [arr  (clojure.string/split s #"\ ")]

    (map #(clojure.string/replace % #":" "") arr)))

(def x (parse-json-string test-str))
(keyword (first x))
