(ns adventofcode.y2024.day6
  (:require [adventofcode.utils :refer [get-input
                                        get-example]]
            [clojure.string :refer [split]]))

(def input (get-input 2024 6))
(def example (get-example 2024 6))
(def keepers #{"#" "^"})
(def guard #{"^" ">"})

(defn build-grid [lines]
  (->> lines
       (map #(split % #""))
       to-array-2d))

(defn get-nodes [grid]
  (apply
   concat
   (map-indexed
    (fn [y row]
      (map-indexed
       (fn [x val] [x y val])
       row))
    grid)))

(defn areas-of-interest [nodes]
  (filter
   #(contains? keepers (nth % 2))
   nodes))

(defn find-guard [nodes]
  (first (filter #(guard (nth % 2)) nodes)))

(defn guard-walk [nodes]
  (let [[gx gy gval] (find-guard nodes)]
    (filter (fn [[x y val]] (= x gx)) nodes)))

(defn part1 [data]
  (->> data
       build-grid
       get-nodes
       areas-of-interest
       guard-walk))

(part1 example)