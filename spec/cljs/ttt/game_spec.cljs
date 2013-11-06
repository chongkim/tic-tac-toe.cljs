(ns ttt.game-spec
  (:require-macros [specljs.core :refer [describe before it should= should]])
  (:require [domina :as dom]
            [domina.events :as event]
            [specljs.core]
            [ttt.game :refer [init *position*]]
            [ttt.position :refer [init-position]]))

(describe "ttt.game"
  (before (init))
  (it "has the current position"
    (should= init-position *position*))
  (it "has ttt-table"
    (should (dom/by-id "ttt-table")))
  (it "has cells"
    (should (dom/by-id "t0"))
    (should (dom/by-id "t8")))
  (it "marks the first square"
    (event/dispatch! (dom/by-id "t0") :click {})
    (should= "x" (dom/html (dom/by-id "t0"))))
  (it "updates the current position after a click"
    (event/dispatch! (dom/by-id "t0") :click {})
    (should= 'x (get (:board *position*) 0)))
  (it "makes the computer move after human clicks"
    (set! *position* {:board '[- x -, - o o, - - -] :turn 'x})
    (event/dispatch! (dom/by-id "t0") :click {})
    (should= 'o (get (:board *position*) 3))
    (should= "o" (dom/html (dom/by-id "t3")))
    )
  )
