(ns ttt.engine
  (:require [ttt.position :refer [win? possible-moves move choose]]))

(declare minimax)
(defn store-minimax [{:keys [board turn] :as position}]
  (let [moves (possible-moves position)]
    (cond (win? position 'x) 100
          (win? position 'o) -100
          (empty? moves) 0
          :else ((choose turn + -)
                 (apply (choose turn max min)
                        (map #(minimax (move position %)) moves))
                 (count moves))
          )))

(def minimax (memoize store-minimax))

(defn best-move [{:keys [board turn] :as position}]
  (let [moves (possible-moves position)]
    (second
      (apply (choose turn max-key min-key)
             first
             (map vector
                  (map #(minimax (move position %))
                       moves)
                  moves)))
    ))
