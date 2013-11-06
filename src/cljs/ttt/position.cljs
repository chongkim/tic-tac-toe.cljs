(ns ttt.position)

(def dim 3)
(def size (* dim dim))

(def init-position {:board (vec (replicate size '-))
                    :turn 'x})

(defn choose [turn x o]
  (if (= 'x turn) x o))

(defn move [{:keys [board turn]} idx]
  {:board (assoc board idx turn)
   :turn (choose turn 'o 'x)})

(defn possible-moves [{:keys [board]}]
  (remove nil? (map-indexed #(if (= '- %2) %1) board)))

(defn win? [{:keys [board]} turn]
  (let [rows (partition dim board)
        line-match (fn [line] (every? #(= turn %) line))]
    (or (some line-match rows)
        (some line-match (apply map vector rows))
        (line-match (map #(get board %) (range 0 size (inc dim))))
        (line-match (map #(get board %) (range (dec dim) (dec size) (dec dim))))
        )
    )
  )
