(ns immo-clojure.core (:require [clojure.data.json :as json]
                                [immo-clojure.scraper :as scraper])
    (:gen-class))

(def url "https://www.kirschundkirsch.de/kaufen.html")


(defn create-map [offer url]
  (merge {:url (scraper/get-link offer)}
         (scraper/get-text offer)
         {:img-src (scraper/get-img-url offer url)}))


(defn get-all-offers [url page-index]
  (mapv #(create-map % url) (scraper/get-offers url page-index)))


(defn store-results [all-results  page-index]
  (spit (format "test_%s.json" page-index) (json/write-str all-results) :append false))

(def last-page (atom false))
(def page-index (atom 1))
(def all-results (atom {}))

(while (false? @last-page)
  (try
    (let [result (get-all-offers url (str @page-index))]
      (println (count result))
      (swap!  all-results assoc (keyword (str @page-index)) result)
      (println (format "page : %s - offers found : %s" @page-index (count result))))
    (swap! page-index inc)
    (catch Exception  e
      (println e)
      (println "last page reached.")
      (store-results @all-results "all")
      (reset! last-page true))))



(defn -main []
  (let [config-data 0]))
