(ns adventofcode.y2024.day5
  (:require [adventofcode.utils :refer [get-example
                                        get-input]]
            [clojure.string :refer [includes?
                                    split]]))

(def input (get-input 2024 5))
(def example (get-example 2024 5))

(defn filter-and-split [lines token exp]
  (->> lines
       (filter #(includes? % token))
       (map #(split % exp))))

(defn extract [lines]
  {:pages (filter-and-split lines "|" #"\|")
   :rules (filter-and-split lines "," #",")})

(defn part1 [data]
  (->> data
       extract))

(part1 example)
