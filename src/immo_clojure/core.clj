(ns immo-clojure.core (:require [clojure.string]
                                [immo-clojure.config]
                                [immo-clojure.scrapper]
                                [immo-clojure.job]
                                [clojure.data.json :as json]))



(def all-conf  (immo-clojure.config/load-conf "resources/config.json"))



(defn iterate-scrap [url-base tags last-page page-index all-results]
  (while (false? @last-page)
    (try
      (let [result (immo-clojure.scrapper/get-links  (str url-base @page-index) tags)]
        (swap!  all-results concat  result)
        (println (format "page : %s - offers found : %s" @page-index (count result))))
      (swap! page-index inc)
      (catch Exception  e
        ;(println e)
        (println "last page reached.")
        (reset! last-page true)))))


(defn recur-get-links [url-base tags]
  (let [last-page (atom false) page-index (atom 1) all-results (atom [])]
    (iterate-scrap url-base tags last-page page-index all-results)
    (#(deref all-results))))

(defn links-from-conf [conf]
  (if (clojure.string/blank? (:next_page_prefix conf))
    (hash-map :url (:root-url conf) :results (immo-clojure.scrapper/get-links (:url-kaufen conf) (:xpath_listing conf)))
    (hash-map :url (:root-url conf) :results (recur-get-links (str (:url-kaufen conf) (:next_page_prefix conf)) (:xpath_listing conf)))))

(def results (map #(links-from-conf %) all-conf))

(defn drop-dupes [all-results]
  (let [unique-results (map #(hash-map :url (:url %) :results (set (:results %))) all-results)]
    (doall unique-results)))

(clojure.pprint/pprint (drop-dupes results))
(immo-clojure.job/store-results (drop-dupes results) "_all")

(def loaded-results (json/read-str (slurp "test__all.json")
                                   :key-fn keyword))

(clojure.pprint/pprint loaded-results)


