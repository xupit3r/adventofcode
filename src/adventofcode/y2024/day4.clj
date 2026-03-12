(ns adventofcode.y2024.day4
  (:require [adventofcode.utils :refer [get-example
                                        get-input
                                        build-grid
                                        safe-lookup]]))

(def input (get-input 2024 4))
(def example (get-example 2024 4))

(def directions [[0 1]
                 [0 -1]
                 [1 0]
                 [-1 0]
                 [1 1]
                 [1 -1]
                 [-1 1]
                 [-1 -1]])

(defn create-sequence [grid x y dx dy num]
  (apply
   str
   (map
    #(safe-lookup
      grid
      (+ x (* dx %))
      (+ y (* dy %)))
    (range num))))

(defn walk-sequence [grid]
  (map-indexed
   (fn [x row]
     (map-indexed
      (fn [y _v]
        (map
         #(create-sequence
           grid
           x
           y
           (first %)
           (second %) 4)
         directions))
      row))
   grid))

(defn neighbors [grid x y]
  (map #(safe-lookup
         grid
         (+ x (first %))
         (+ y (second %)))
       directions))

(defn check-crisscross [cell]
  (apply
   str
   [(nth cell 4)
    (nth cell 7)
    (nth cell 5)
    (nth cell 6)]))

(defn walk-crisscross [grid]
  (map-indexed
   (fn [x row]
     (map-indexed
      (fn [y _v]
        (if (= (aget grid x y) "A")
          (check-crisscross
           (neighbors grid x y))
          nil)) 
      row))
   grid))

(defn count-xmas [freqs]
  (get freqs "XMAS"))

(defn count-crisscross [freqs]
  (+ (get freqs "MSSM")
     (get freqs "MSMS")
     (get freqs "SMSM")
     (get freqs "SMMS")))

(defn part1 [data]
  (->> data
       build-grid
       walk-sequence
       flatten
       frequencies
       count-xmas))

(defn part2 [data]
  (->> data
       build-grid
       walk-crisscross
       flatten
       frequencies
       count-crisscross))

(part1 input)
;(part2 input)