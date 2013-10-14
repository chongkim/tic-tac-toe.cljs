(ns ttt.game
  (:require-macros [hiccups.core :as h])
  (:require [clojure.string :as string]
            [domina :as dom]
            [domina.css :as css]
            [domina.events :as event]
            [ttt.engine :refer [best-move]]
            [ttt.position :refer [init-position size move win?]]))

(defn set-position [position]
  (set! (.-position js/window) position))

(defn get-position []
  (.-position js/window))

(defn set-player [player]
  (set! (.-player js/window) player))

(defn get-player []
  (.-player js/window))

(defn log [args]
  (.log js/console args))

(defn render
  ([] (render (get-position)))
  ([{:keys [board] :as position}]
   (when (nil? (dom/by-id "board"))
     (dom/append! (css/sel "body") (h/html [:div#board]))
     (dom/append! (css/sel "body") (h/html [:div#debug "debug"]))
     (dotimes [i size]
       (dom/append! (dom/by-id "board")
                    (str "<div id=\"t" i "\" class=\"square\"></div>"))))
   (dotimes [i size]
     (let [el (dom/by-id (str "t" i))
           piece (get board i)]
       (if (= '- piece)
         (dom/set-text! el "")
         (dom/set-text! el (string/upper-case (str piece))))))))

(declare ask-for-move)
(declare computer-move)
(declare main-loop)

(defn ask-for-player []
  (when (nil? (dom/by-id "ask-for-player"))
    (dom/append!
      (css/sel "body")
      (h/html [:div#ask-for-player
               [:div "Who do you want to go first?"]
               [:div#computer "1. computer"]
               [:div#human "2. human"]]))
    (event/listen! (dom/by-id "computer") :click
                   (fn [e]
                     (set-player "computer")
                     (render)
                     (ask-for-move)
                     (main-loop)))
    (event/listen! (dom/by-id "human") :click
                   (fn [e]
                     (set-player "human")
                     (render)
                     (ask-for-move)
                     (main-loop)))))

(defn switch-player []
  (let [player (get-player)]
    (cond (= "computer" player) (set-player "human")
          (= "human" player) (set-player "computer"))))

(defn ask-for-move []
  (dotimes [i size]
    (event/listen! (dom/by-id (str "t" i)) :click
                   (fn [e]
                     (event/unlisten! (dom/by-id (str "t" i)) :click)
                     (set-position (move (get-position) i))
                     (switch-player)
                     (render)
                     (main-loop)))))

(defn computer-move []
  (let [position (get-position)
        idx (best-move position)]
    (set-position (move position idx))
    (switch-player)
    (render)))

(defn other [player]
  (cond (= "computer" player) "human"
        (= "human" player) "computer"
        :else nil))

(defn main-loop []
  (let [{:keys [board turn] :as position} (get-position)
        player (get-player)]
    (cond (or (win? position 'x)
              (win? position 'o))
          (dom/append! (css/sel "body") (str "<div>" (other player) " wins </div>"))

          (= "computer" player) (do
                                  (computer-move)
                                  (main-loop)))))

(defn play []
  (set-position init-position)
  (ask-for-player))
