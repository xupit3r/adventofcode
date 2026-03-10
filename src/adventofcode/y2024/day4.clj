(ns adventofcode.y2024.day4
  (:require [adventofcode.utils :refer [get-example
                                        get-input]]
            [clojure.string :refer [split]]))

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

(defn build-grid [lines]
  (to-array-2d (map #(split % #"") lines)))

(defn in-bounds? [grid x y]
  (and (> x -1)
       (> y -1)
       (< x (count grid))
       (< y (count (nth grid 0)))))

(defn create-sequence [grid x y dx dy num]
  (apply
   str
   (map
    (fn [i]
      (let [nx (+ x (* dx i))
            ny (+ y (* dy i))]
        (if (in-bounds? grid nx ny)
          (aget grid nx ny)
          ".")))
    (range num))))

(defn neighbors [grid x y]
  (map
   (fn [[dx dy]]
     (create-sequence grid x y dx dy 4))
   directions))

(defn walk [grid]
  (map-indexed
   (fn [x row]
     (map-indexed
      (fn [y _v]
        (neighbors grid x y)) row)) grid))

(defn count-xmas [sequences]
  (get (frequencies sequences) "XMAS"))

(defn part1 [data]
  (->> data
       build-grid
       walk
       flatten
       count-xmas))

(part1 input)