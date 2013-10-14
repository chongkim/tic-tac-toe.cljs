(ns ttt.game-spec
  (:require-macros [specljs.core :refer [before describe context it should= should]])
  (:require [domina :as dom]
            [domina.css :as css]
            [domina.events :as event]
            [specljs.core]
            [ttt.game :refer [render ask-for-player ask-for-move computer-move get-position set-position get-player set-player main-loop play]]
            [ttt.position :refer [init-position move size]]))

(describe "ttt.game"
  (before
    (set-player nil))
  (context "render"
    (it "renders the position"
      (render {:board '[x x -, - o o, - - -] :turn 'x})
      (should= "X" (dom/html (dom/by-id "t0")))
      (should= "X" (dom/html (dom/by-id "t1")))
      (should= ""  (dom/html (dom/by-id "t2")))
      (should= ""  (dom/html (dom/by-id "t3")))
      (should= "O" (dom/html (dom/by-id "t4")))
      (should= "O" (dom/html (dom/by-id "t5")))
      (should= ""  (dom/html (dom/by-id "t6")))
      (should= ""  (dom/html (dom/by-id "t7")))
      (should= ""  (dom/html (dom/by-id "t8")))))
  (context "ask-for-player"
    (it "asks for player"
      (with-redefs [main-loop (fn [])]
        (ask-for-player)
        (event/dispatch! (dom/by-id "human") :click {})
        (should= "human" (get-player)))))
  (context "ask-for-move"
    (it "listens for moves"
      (set-position init-position)
      (render)
      (ask-for-move)
      (should= init-position (get-position))
      (event/dispatch! (dom/by-id "t0") :click {})
      (should= (move init-position 0) (get-position))))
  (context "computer-move"
    (it "picks the best move"
      (set-position {:board '[x x -, - o o, - - -] :turn 'x})
      (computer-move)
      (should= {:board '[x x x, - o o, - - -] :turn 'o} (get-position))))
  (context "play"
    (it "plays a complete game"
      (play)
      (event/dispatch! (dom/by-id "human") :click {})
      (should= "human" (get-player))
      (should (dom/by-id "board"))
      (event/dispatch! (dom/by-id "t0") :click {}))))
