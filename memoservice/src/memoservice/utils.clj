(ns ^{:author "avr. alex gherega"} memoservice.utils
    (:require [clojure.string :as string]))

(defn fetch-raw-html [url]
  (slurp url))

(defn make-test-question [html-question]
  (string/replace html-question "#33FF33" "#FFFFFF"))
