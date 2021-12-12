(ns immo-clojure.config (:require [clojure.string] [clojure.pprint  :refer [pprint] :rename {pprint p}] [cheshire.core :as cc] [cemerick.url :refer (url)]))



(defn load-config-file [filepath]
  (let [json-data (slurp filepath)]
    (cc/parse-string json-data true)))


(defn get-host [map-config]
  (get (url (get map-config :url-kaufen)) :host))


(defn get-tags [map-config]
  (let [arr  (clojure.string/split (get map-config :xpath_listing) #"\ ")]
    (mapv #(keyword (clojure.string/replace % #":" "")) arr)))

(defn parse-config [raw-conf]
  (assoc raw-conf  :root-url (get-host raw-conf) :xpath_listing (get-tags raw-conf)))

(defn load-conf [filepath]  (map #(parse-config %) (load-config-file filepath)))
(p (load-conf "resources/config.json"))




