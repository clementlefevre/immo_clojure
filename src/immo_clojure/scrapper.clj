(ns immo-clojure.scrapper  (:require [net.cgrand.enlive-html :as html]))

(defn get-links [url tags]
  (let [content (-> url
                    (java.net.URL.)
                    (html/html-resource)
                    (html/select tags))]
    (mapcat #(html/attr-values % :href) content)))


