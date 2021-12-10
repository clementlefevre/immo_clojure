(ns immo-clojure.core (:require   [clojure.pprint :as p] [net.cgrand.enlive-html :as html]
                                  [cemerick.url :refer (url)]
                                  [taoensso.timbre :as timbre]
                                  [taoensso.timbre.appenders.core :as appenders]))


(timbre/merge-config!
 {:appenders {:spit (appenders/spit-appender {:fname "immo.log"})}})
(timbre/merge-config! {:appenders {:hostname_ "cououc"}})
(timbre/get-hostname)
(timbre/info "Test Logging")



(def url-main-page "https://www.krentz.de/immobilien/kaufen/")



(defn get-links [url tags]
  (let [content (-> url
                    (java.net.URL.)
                    (html/html-resource)
                    (html/select tags))]
    (mapcat #(html/attr-values % :href) content)))

(def page-content (get-links url-main-page [:div.objekt-item-inner-text :a]))
(p/pprint page-content)

(get (url url-main-page) :host)

(def root-url (get (url url-main-page) :host))
(p/pprint root-url)
(map #(str (format "%s%s" root-url %)) page-content)

