(ns immo-clojure.services  (:import (java.security MessageDigest) (java.math BigInteger)))


(defn md5
  [^String s]
  (->> s
       .getBytes
       (.digest (MessageDigest/getInstance "MD5"))
       (BigInteger. 1)
       (format "%032x")))


(defn create-key [value]
  (-> value
      (format "%s")
      (str)
      (keyword)))
(create-key "test")



