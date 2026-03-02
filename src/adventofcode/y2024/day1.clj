(ns adventofcode.y2024.day1
  (:require [clojure.string :refer [split-lines split]]))

(def input (split-lines (slurp "inputs/2024/day1.txt")))
(def example (split-lines (slurp "examples/2024/day1.txt")))

(defn parse [lines] (map #(split % #"\s+") lines))

(defn pull-column [parsed col]
  (map #(Integer/parseInt %)
       (map #(nth % col) parsed)))

(defn score [parsed] 
  (let [reference (frequencies (pull-column parsed 1))]
    (map #(* % (if (reference %) 
                 (reference %) 0)) 
         (pull-column parsed 0))))

(defn diff [parsed] 
    (map abs
         (map -
              (sort (pull-column parsed 0))
              (sort (pull-column parsed 1)))))
(defn sum [diffs] (apply + diffs))

(defn part1 [data]
  (-> data
      (parse)
      (diff)
      (sum)))

(defn part2 [data]
  (-> data
      (parse)
      (score)
      (sum)))
