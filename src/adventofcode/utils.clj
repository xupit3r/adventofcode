(ns adventofcode.utils
  (:require [clojure.string :refer [split-lines split]]))

(defn get-txt [category year day]
  (split-lines
   (slurp
    (format
     "%s/%s/day%d.txt"
     category
     year
     day))))

(defn get-input [year day]
  (get-txt "inputs" year day))

(defn get-example [year day]
  (get-txt "examples" year day))

(defn parse-space [lines]
  (map
   #(split % #"\s+") lines))

(defn parse-numbers [lines]
  (map 
   (fn [line]
     (map
      #(Integer/parseInt %)
      line)) 
   lines))

(defn pull-column [parsed col]
  (map #(Integer/parseInt %)
       (map #(nth % col) parsed)))

(defn build-grid [lines]
  (to-array-2d (map #(split % #"") lines)))

(defn in-bounds? [grid x y]
  (and (> x -1)
       (> y -1)
       (< x (count grid))
       (< y (count (nth grid 0)))))
