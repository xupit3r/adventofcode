(ns adventofcode.y2024.day5
  (:require [adventofcode.utils :refer [get-example
                                        get-input]]
            [clojure.string :refer [includes?
                                    split]]))

(def input (get-input 2024 5))
(def example (get-example 2024 5))

(defn filter-and-split [lines token exp]
  (->> lines
       (filter #(includes? % token))
       (map #(vec (split % exp)))))

(defn identify-rules [rules update]
  (let [su (set update)]
    (filterv
     (fn [[a b]]
       (and (contains? su a)
            (contains? su b))) 
     rules)))

(defn extract [lines]
  (let [updates (filter-and-split lines "," #",")
        rules   (filter-and-split lines "|" #"\|")]
    (map
     #(hash-map
       :rules (identify-rules rules %),
       :update %)
     updates)))

(defn check-rules [{rules :rules update :update}]
  {:rules rules
   :update update
   :correct (every?
             #(< (.indexOf update (first %))
                 (.indexOf update (second %)))
             rules)})

(defn check-correct [{correct :correct}] (true? correct))

(defn find-medians [{update :update}]
  (Integer/parseInt
   (nth update (int (Math/floor (/ (count update) 2))))))

(defn part1 [data]
  (->> data
       extract
       (map check-rules)
       (filter check-correct)
       (map find-medians)
       (apply +)))

(part1 input)
