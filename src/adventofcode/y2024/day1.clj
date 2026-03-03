(ns adventofcode.y2024.day1
  (:require [adventofcode.utils :refer [pull-column
                                        parse-space
                                        get-example
                                        get-input]]))

(def input (get-input 2024 1))
(def example (get-example 2024 1))

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
      (parse-space)
      (diff)
      (sum)))

(defn part2 [data]
  (-> data
      (parse-space)
      (score)
      (sum)))
