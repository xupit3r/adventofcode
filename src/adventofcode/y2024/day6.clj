(ns adventofcode.y2024.day6
  (:require [adventofcode.utils :refer [get-input
                                        get-example]]
            [clojure.string :refer [split]]))

(def input (get-input 2024 6))
(def example (get-example 2024 6))

(defn build-grid [lines]
  (->> lines
       (map #(split % #""))
       to-array-2d))

(defn apply-direction [[x y direction]]
  (cond (= :up direction) [x (- y 1) :up]
        (= :right direction) [(+ x 1) y :right]
        (= :down direction) [x (+ y 1) :down]
        (= :left direction) [(- x 1) y :left]))

(defn rotate [[x y direction]]
  (cond (= :up direction) [x y :right]
        (= :right direction) [x y :down]
        (= :down direction) [x y :left]
        (= :left direction) [x y :up]))

(defn safe-aget [[x y _direction] grid]
  (if (and (> x -1)
           (> y -1)
           (< y (count grid))
           (< x (count (nth grid 0))))
    (aget grid y x)
    :out-of-bounds))

(defn walk [[x y direction] grid steps]
  (let [current (safe-aget [x y direction] grid)
        next (safe-aget (apply-direction [x y direction]) grid)]
    (if (= :out-of-bounds current)
      steps
      (if (= "#" next)
        (recur
         (rotate
          [x y direction]) grid steps)
        (recur
         (apply-direction
          [x y direction]) grid (conj steps [x y]))))))

(defn find-guard [grid]
  (first
   (for [[y row] (map-indexed vector grid)
         [x val] (map-indexed vector row)
         :when (= "^" val)]
     [x y :up])))

(defn part1 [data]
  (let [grid (build-grid data)
        guard (find-guard grid)]
    (distinct (walk guard grid []))))

(count (part1 input))