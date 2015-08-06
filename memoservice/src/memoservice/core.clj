(ns memoservice.core
  (:require [clojure.data.json :as json]))

(def *current-idx* (atom 1))

(defn- read-questions [path]
  (-> path
      slurp
      json/read-json
      :questions))

(def *vip-idxs*
  (-> "lib/very-imp-q.json" read-questions atom))

(def *mip-idxs*
  (-> "lib/most-imp-q.json" read-questions atom))

(defn reset-vip []
  (reset! *vip-idxs* (-> "lib/very-imp-q.json" read-questions)))

(defn reset-mip []
  (reset! *mip-idxs* (-> "lib/most-imp-q.json" read-questions)))
