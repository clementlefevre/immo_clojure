(ns immo-clojure.tablecloth-json  (:require [clojure.string] [tablecloth.api :as tc]   [clojure.data.json :as json]))

(defn fo
(def mapa (json/read-str (slurp "test.json")  :key-fn keyword))
(keys mapa)
(tc/dataset (:units (:data  mapa)))
(data mapa)

