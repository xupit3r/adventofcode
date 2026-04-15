(ns adventofcode.y2024.day6
  (:require [adventofcode.utils :refer [get-input
                                        get-example]]
            [clojure.string :refer [split]]))

(def input (get-input 2024 6))
(def example (get-example 2024 6))
(def keepers #{"#" "^"})
(def guard #{"^" ">"})
(def directions [:up :right :down :left])

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

(defn dpreds [direction x y]
  (cond
    (= direction :up) (fn [node] (and (= x (nth node 0)) (> y (nth node 1))))
    (= direction :right) (fn [node] (and (= y (nth node 1)) (< x (nth node 0))))
    (= direction :down) (fn [node] (and (= x (nth node 0)) (< y (nth node 1))))
    (= direction :left) (fn [node] (and (= y (nth node 1)) (> x (nth node 0))))))

(defn dvecs [direction]
  (cond
    (= direction :up) [0 1]
    (= direction :right) [-1 0]
    (= direction :down) [0 -1]
    (= direction :left) [1 0]))

(defn distance-comp [gx gy]
  (fn [[x1 y1] [x2 y2]]
    (- (+ (abs (- gx x1)) (abs (- gy y1)))
       (+ (abs (- gx x2)) (abs (- gy y2))))))

(defn closest [gx gy nodes]
  (sort-by #((nth % 0) (nth % 1)) (distance-comp gx gy) nodes))

(defn find-guard [nodes]
  (first
   (filter
    #(guard (nth % 2))
    nodes)))

(defn find-next [direction gx gy nodes]
  (map +
       (dvecs direction)
       (first
        (closest
         gx
         gy
         (filter
          (dpreds direction gx gy)
          nodes)))))

(defn guard-walk [nodes]
  (loop [route []
         direction 0
         guard (find-guard nodes)]
    (if (empty? guard)
      route
      (recur (cons guard route)
             (+ direction 1)
             (find-next
              (nth directions (mod direction 4))
              (nth guard 0)
              (nth guard 1)
              nodes)))))

(defn diffs [[a b]] 
  (abs 
   (- (+ (nth a 0) (nth a 1)) 
      (+ (nth b 0) (nth b 1)))))

(defn part1 [data]
  (->> data
       build-grid
       get-nodes
       areas-of-interest
       guard-walk
       (partition 2 1)))

(part1 example)