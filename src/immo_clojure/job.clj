(ns immo-clojure.job (:require [clojure.data.json :as json])
    (:gen-class))



(defn store-results [all-results  page-index]
  (spit (format "test_%s.json" page-index) (json/write-str all-results) :append false))




