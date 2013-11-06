(ns ttt.position-spec
  (:require-macros [specljs.core :refer [describe context it
                                         should= should-not should]])
  (:require [specljs.core]
            [ttt.position :refer [init-position move possible-moves win?]]))

(describe "ttt.position"
  (context "init-position"
    (it "gets the initial position"
      (should= {:board '[- - -, - - -, - - -] :turn 'x} init-position)))
  (context "move"
    (it "make a move for x"
      (should= {:board '[x - -, - - -, - - -] :turn 'o} (move init-position 0)))
    (it "make a move for o"
      (should=
        {:board '[x o -, - - -, - - -] :turn 'x}
        (move (move init-position 0) 1))))
  (context "possible-moves"
    (it "lists possible moves for a position"
      (should= [2 3 6 7 8] (possible-moves {:board '[x x -, - o o, - - -]
                                            :turn 'x}))))
  (context "win?"
    (it "determines no win"
      (should-not (win? init-position 'x)))
    (it "determines a win on first row for x"
      (should (win? {:board '[x x x, - - -, - - -] :turn 'x} 'x)))
    (it "determines a win on second row for x"
      (should (win? {:board '[- - -, x x x, - - -] :turn 'x} 'x)))
    (it "determines a win on third row for x"
      (should (win? {:board '[- - -, - - -, x x x] :turn 'x} 'x)))
    (it "determines a win on first col for o"
      (should (win? {:board '[o - -
                              o - -
                              o - -] :turn 'x} 'o)))
    (it "determines a win on second col for o"
      (should (win? {:board '[- o -
                              - o -
                              - o -] :turn 'x} 'o)))
    (it "determines a win on third col for o"
      (should (win? {:board '[- - o
                              - - o
                              - - o] :turn 'x} 'o)))
    (it "determines a win on major diagonal for o"
      (should (win? {:board '[o - -
                              - o -
                              - - o] :turn 'x} 'o)))
    (it "determines a win on minor diagonal for o"
      (should (win? {:board '[- - o
                              - o -
                              o - -] :turn 'x} 'o)))
    )
  )
