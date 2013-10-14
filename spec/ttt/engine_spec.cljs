(ns ttt.engine-spec
  (:require-macros [specljs.core :refer [describe context it should=]])
  (:require [specljs.core]
            [ttt.engine :refer [minimax best-move]]))

(describe "ttt.engine"
  (context "minimax"
    (it "determines a win for x"
      (should= 100 (minimax {:board '[x x x, - - -, - - -] :turn 'x})))
    (it "determines a win for o"
      (should= -100 (minimax {:board '[o o o, - - -, - - -] :turn 'o})))
    (it "determines a draw"
      (should= 0 (minimax {:board '[o x o, x o x, x o x] :turn 'x})))
    (it "determines a win for x in 1 move"
      (should= 107 (minimax {:board '[x x -, - - -, - - -] :turn 'x})))
    (it "determines a win for o in 1 move"
      (should= -107 (minimax {:board '[o o -, - - -, - - -] :turn 'o}))))
  (context "best-move"
    (it "determines the best move for x"
      (should= 2 (best-move {:board '[x x -, - o o, - - -] :turn 'x})))
    (it "determines the best move for o"
      (should= 3 (best-move {:board '[x x -, - o o, - - -] :turn 'o})))))
