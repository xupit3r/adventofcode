(ns adventofcode.y2024.day2
  (:require [adventofcode.utils
             :refer [parse-space
                     parse-numbers
                     get-input
                     get-example]]))

(def input (get-input 2024 2))
(def example (get-example 2024 2))

(defn pairs [line] (partition 2 1 line))

(defn diffs [line] (map #(apply - %) line))

(defn safe? [line]
  (and (<= (apply max (map abs line)) 3)
       (or
        (every? #(> % 0) line)
        (every? #(< % 0) line))))

(defn build-combos [line]
  (let [n (count line)]
    (for [i (range n)]
      (let [[head tail] (split-at i line)]
        (concat head (rest tail))))))

(defn any-safe? [combos]
  (some
   #(->> % pairs diffs safe?)
   combos))

(defn part1 [data]
  (->> data
       (parse-space)
       (parse-numbers)
       (map pairs)
       (map diffs)
       (map safe?)
       (filter true?)
       (count)))

(defn part2 [data]
  (->> data
       (parse-space)
       (parse-numbers)
       (map build-combos)
       (map any-safe?)
       (filter true?)
       (count)))