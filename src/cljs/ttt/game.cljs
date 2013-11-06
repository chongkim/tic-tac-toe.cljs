(ns ttt.game
  (:require-macros [hiccups.core :as h])
  (:require [domina :as dom]
            [domina.css :as css]
            [domina.events :as event]
            [hiccups.runtime]
            [ttt.engine :refer [best-move]]
            [ttt.position :refer [init-position size move]]))

(def *position* nil)

(defn computer-move []
  (let [idx (best-move *position*)]
    (dom/set-html! (dom/by-id (str "t" idx)) (str (:turn *position*)))
    (set! *position* (move *position* idx))
    ))

(defn ^:export init []
  (set! *position* init-position)
  (dom/set-html! (css/sel "body")
                 (h/html
                   [:div#ttt-table
                    (for [idx (range 0 size)]
                      [:div.cell {:id (str "t" idx)}])]))
  (doseq [idx (range 0 size)]
    (let [cell (dom/by-id (str "t" idx))]
      (event/listen! cell :click
                     #(do
                        (dom/set-html! cell (str (:turn *position*)))
                        (set! *position* (move *position* idx))
                        (computer-move)
                        ))))
  )
