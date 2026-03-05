(ns adventofcode.y2024.day2
  (:require [adventofcode.utils
             :refer [parse-space
                     get-input
                     get-example]]))

(def input (get-input 2024 2))
(def example (get-example 2024 2))

(-> example
    (parse-space))
