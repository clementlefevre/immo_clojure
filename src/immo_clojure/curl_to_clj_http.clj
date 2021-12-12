(ns immo-clojure.curl-to-clj-http (:require
                                   [clojure.core]
                                   [clojure.pprint]
                                   [clj-http.client]
                                   [cheshire.core :as cc]))

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

(slurp "https%3A%2F%2Fsmartsite2.myonoffice.de%2Fkunden%2Fagasimmobilien%2F17%2Fimmobilien-nach-vermarktungsstatus.xhtml%3Fs%5C%5B20999-39%5C%5D%3D1%26s%5C%5B20999-37%5C%5D%3D1%26p%5C%5Bobj0%5C%5D%3D5")


(defn get-agas-immobilien [] (let [t (clj-http.client/get
                                      "https://smartsite2.myonoffice.de/kunden/agasimmobilien/17/immobilien-nach-vermarktungsstatus.xhtml?s[20999-39]=1&s[20999-37]=1&p[obj0]=5"
                                      {:headers
                                       {:user-agent "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36"
                                        :accept "application/json, text/javascript, */*; q=0.01'"}})]
                               (:body t)))

(def result (get-agas-immobilien))
(def parsed (cc/parse-string result))
(println result)
;Niedrigenergiehaus