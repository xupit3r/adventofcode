(ns adventofcode.y2024.day3
  (:require [adventofcode.utils :refer [get-input
                                        get-example]]))

(def input (get-input 2024 3))
(def example (get-example 2024 3))


(defn pull-relevants [line]
  (re-seq #"do\(\)|don't\(\)|mul\(\d+,\d+\)" line))

(defn slice-up [capture? line]
  (reduce
   (fn [acc x]
     (if (capture? x)
       (conj acc [x])
       (conj (if (empty? acc) acc (pop acc)) (conj (peek acc) x))))
   []
   line))

(defn keep-dos [line]
  (mapcat
   #(if (= (first %) "do()")
      (rest %)
      []) line))

(defn pull-matches [line]
  (re-seq #"mul\(\d{1,3},\d{1,3}\)" line))

(defn pull-numbers [match]
  (re-seq #"(\d{1,3}),(\d{1,3})" match))

(defn multiply [pair]
  (->> pair
       (map #(Integer/parseInt %))
       (apply *)))

(defn part1 [data]
  (->> data
       (map pull-matches)
       (flatten)
       (map pull-numbers)
       (map first)
       (map rest)
       (map multiply)
       (apply +)))

(defn part2 [data]
  (->> data
       (map pull-relevants)
       (map #(slice-up #{"do()" "don't()"} %))
       (map keep-dos)
       (flatten)
       (map pull-numbers)
       (map first)
       (map rest)
       (map multiply)
       (apply +)))

(part2 input)