(ns adventofcode.y2024.day3
  (:require [adventofcode.utils :refer [get-input
                                        get-example]]))

(def input (get-input 2024 3))
(def example (get-example 2024 3))

(defn pull-matches [line]
  (re-seq #"mul\(\d{1,3},\d{1,3}\)" line))

(defn pull-numbers [match]
  (re-seq #"(\d{1,3}),(\d{1,3})" match))

(defn multiply [pair]
  (->> pair
       (map #(Integer/parseInt %))
       (apply *)))

(->> input
     (map pull-matches)
     (flatten)
     (map pull-numbers)
     (map first)
     (map rest)
     (map multiply)
     (apply +))