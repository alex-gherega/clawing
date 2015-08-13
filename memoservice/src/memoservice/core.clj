(ns memoservice.core
  (:require [clojure.data.json :as json]
            [memoservice.utils :as memoti]))

(def *test-question-answer* (atom :question))

(defn swap-test-qa []
  (condp = @*test-question-answer*
    :question (reset! *test-question-answer* :answer)
    (reset! *test-question-answer* :question)))

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

(def *others-idxs* (atom (filter #(not (memoti/contains?
                                        %
                                        (into @*vip-idxs*
                                              @*mip-idxs*)))
                                 (range))))

(defn reset-vip []
  (reset! *vip-idxs* (-> "lib/very-imp-q.json" read-questions)))

(defn reset-mip []
  (reset! *mip-idxs* (-> "lib/most-imp-q.json" read-questions)))

(defn reset-others []
  (reset! *others-idxs* (into (-> "lib/very-imp-q.json" read-questions)
                             (-> "lib/most-imp-q.json" read-questions))))
