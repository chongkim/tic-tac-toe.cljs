(ns ttt.position-spec
  (:require-macros [specljs.core :refer [describe context it should= should-not should]])
  (:require [specljs.core]
            [ttt.position :refer [init-position choose move possible-moves win?]]))

(describe "ttt.position"
  (context "init-position"
    (it "stores the initial position"
      (should= {:board '[- - -, - - -, - - -] :turn 'x}
        init-position))
    (it "chooses depending on turn"
      (should= 'x (choose 'x 'x 'o)))
    (it "makes a move"
      (should= {:board '[x - -, - - -, - - -] :turn 'o}
        (move init-position 0)))
    (it "lists possible moves"
      (should= [2,3,6,7,8]
        (possible-moves {:board '[x x -, - o o, - - -] :turn 'x})))
    (it "determines no win"
      (should-not (win? init-position 'x)))
    (it "determines a win for x in first row"
      (should (win? {:board '[x x x, - - -, - - -] :turn 'x} 'x)))
    (it "determines a win for x in second row"
      (should (win? {:board '[- - -, x x x, - - -] :turn 'x} 'x)))
    (it "determines a win for x in third row"
      (should (win? {:board '[- - -, - - -, x x x] :turn 'x} 'x)))
    (it "determines a win for o in first col"
      (should (win? {:board '[o - -
                              o - -
                              o - -] :turn 'o} 'o)))
    (it "determines a win for o in second col"
      (should (win? {:board '[- o -
                              - o -
                              - o -] :turn 'o} 'o)))
    (it "determines a win for o in third col"
      (should (win? {:board '[- - o
                              - - o
                              - - o] :turn 'o} 'o)))
    (it "determines a win for o in major diagonal"
      (should (win? {:board '[o - -
                              - o -
                              - - o] :turn 'o} 'o)))
    (it "determines a win for o in minor diagonal"
      (should (win? {:board '[- - o
                              - o -
                              o - -] :turn 'o} 'o)))
    )
  )
