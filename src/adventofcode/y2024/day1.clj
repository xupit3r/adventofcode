(ns adventofcode.y2024.day1
  (:require [clojure.string :refer [split-lines split]]))

(def input (split-lines (slurp "inputs/2024/day1.txt")))
;(def input (split-lines (slurp "examples/2024/day1.txt")))

(defn parse-day1 [lines] (map #(split % #"\s+") lines))
(defn diff [parsed]
  (let [pull-column (fn [col]
                      (map #(Integer/parseInt %)
                           (map #(nth % col) parsed)))]
    (map abs
         (map -
              (sort (pull-column 0))
              (sort (pull-column 1))))))
(defn sum [diffs] (apply + diffs))

(defn part1 [input]
  (-> input
      (parse-day1)
      (diff)
      (sum)))
