(ns immo-clojure.scraper (:require  [net.cgrand.enlive-html :as html] [immo-clojure.services]))


(defn get-offers [url page-index]
 ; (println (str (format "%s?page_n10=%s" url page-index)))
  (let [contento (html/html-resource (java.net.URL. (str (format "%s?page_n10=%s" url page-index))))]

    (html/select contento [:div.layout_latest])))

(defn get-link [offer]
  (let [link-url (first (mapcat #(html/attr-values % :href) (html/select offer [:h3 (html/attr? :href)])))]

    link-url))



(defn get-text [offer]
  (let [description (clojure.string/join " " (mapv html/text (html/select  offer [:td])))]
    {:description description :md5 (immo-clojure.services/md5 description)}))


(defn get-img-url [offer url]
  (let [raw-url (-> offer
                    (html/select  [:figure.w50.image_container :img])
                    (first)
                    (html/attr-values :src)
                    (first))] (format "%s/%s" url raw-url)))

