(ns immo-clojure.curl-to-clj-http (:require
                                   [clojure.core]
                                   [clojure.pprint]
                                   [clj-http.client]
                                   [cheshire.core :as cc]
                                   [net.cgrand.enlive-html :as html]))

(defn get-www-locals-de [page-index] (let [t (clj-http.client/post
                                              "https://www.locals.de/"

                                              {:headers
                                               {:origin "https://www.locals.de"
                                                :user-agent "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36"
                                                :content-type "application/x-www-form-urlencoded; charset=UTF-8"
                                                :accept "application/json, text/javascript, */*; q=0.01'"
                                                :x-requested-with "XMLHttpRequest"
                                                :getimmodata "11"}
                                               :form-params {:view "cols" :marketing_type "BUY" :page page-index}})]

                                       (println (str "erkauft found :" (count (re-seq #"erkauft" (:body t)))))
                                       (println (str "vermiete found :" (count (re-seq #"ermiete" (:body t)))))
                                       (spit "test.json" (:body t))
                                       (:body t)))



(defn call [this & that]
  (apply (resolve (symbol this)) that))

(def output (cc/parse-string (call "get-www-locals-de" 1)))
(type output)
(clojure.pprint/pprint output)



(defn get-agas-immobilien [] (let [t (clj-http.client/get
                                      "https://smartsite2.myonoffice.de/kunden/agasimmobilien/17/immobilien-nach-vermarktungsstatus.xhtml?s[20999-39]=1&s[20999-37]=1&p[obj0]=5"
                                      {:headers
                                       {:user-agent "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36"
                                        :accept "application/json, text/javascript, */*; q=0.01'"
                                        :content-type "application/x-www-form-urlencoded; charset=UTF-8"}})]
                               t))

(def result (get-agas-immobilien))
(type (:body result))
(def parsed-html (html/html-snippet (:body result)))
(clojure.pprint/pprint parsed-html)
(html/select parsed-html [:div])
;Niedrigenergiehaus