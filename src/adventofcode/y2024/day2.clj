(ns adventofcode.y2024.day2
  (:require [adventofcode.utils
             :refer [parse-space
                     parse-numbers
                     get-input
                     get-example]]))

(def input (get-input 2024 2))
(def example (get-example 2024 2))

(defn pairs [lines]
  (map (fn [line]
         (partition 2 1 line))
       lines))

(defn diffs [lines]
  (map (fn [line]
         (map
          #(apply - %)
          line))
       lines))

(defn safe? [line]
   (and (<= (apply max (map abs line)) 3)
        (or
         (every? #(> % 0) line)
         (every? #(< % 0) line))))

(->> input
     (parse-space)
     (parse-numbers)
     (pairs)
     (diffs)
     (map safe?)
     (filter true?)
     (count))
