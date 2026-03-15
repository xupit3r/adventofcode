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
(defn check-incorrect [{correct :correct}] (false? correct))

(defn contains-both [a b rules]
  (filter
   #(and (> (.indexOf % a) -1)
         (> (.indexOf % b) -1)) rules))

(defn lookup-indices [update rules]
  (map (fn [[a b]]
         [(.indexOf update a)
          (.indexOf update b)]) rules))

(defn rules-for [rules update]
  (fn [a b]
    (->> rules
         (contains-both a b)
         (lookup-indices update)
         (map #(apply - %))
         (apply -))))

(defn rules-sort [{rules :rules update :update}]
  (let [lookup (rules-for rules update)]
    (sort lookup update)))

(defn find-medians [update]
  (Integer/parseInt
   (nth update (int (Math/floor (/ (count update) 2))))))

(defn part1 [data]
  (->> data
       extract
       (map check-rules)
       (filter check-correct)
       (:update)
       (map find-medians)
       (apply +)))

(defn part2 [data]
  (->> data
       extract
       (map check-rules)
       (filter check-incorrect)
       (map rules-sort)
       (map find-medians)
       (apply +)))

(part2 input)
