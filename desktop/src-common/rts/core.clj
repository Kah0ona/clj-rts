(ns rts.core
  (:require [play-clj.core :refer :all]
            [play-clj.g2d :refer :all]
            [play-clj.ui :refer :all]
            [rts.overlay :as o]))

(declare rts main-screen overlay-screen)

(defn restart-game []
  (app! :post-runnable #(set-screen! rts main-screen overlay-screen )))

; shows the selector rectangle and other HUD elements
(defscreen main-screen 
  :on-show
  (fn [screen entities]
    (update! screen :renderer (stage))
    (println "showing main-screen")
    [(texture "bg.jpg")]
      )
  
  :on-render
  (fn [screen entities]
    (clear!)
    (render! screen entities)))





;;;;;;;;;;;
;;; Overlay screen, rendering the selector rectangle and the FPS etc.
;;;;;;;;;;;
(defscreen overlay-screen 
  :on-show
  (fn [screen entities]
    (update! screen :camera (orthographic) :renderer (stage))
    (assoc (label "0" (color :white))
           :id :fps
           :x 5))

  :on-key-down
  (fn [screen entities]
    (println "key"))
  
  :on-touch-down
  (fn [screen entities]
    (println "touch")
    ;create a selector window, and add it to the entities map on the given position
    (let [pos (input->screen screen (:input-x screen) (:input-y screen))]
    (conj entities (o/create-selector (:x pos) (:y pos)))))

  :on-touch-dragged 
  (fn [screen entities]
    (println "drag")
    (let [pos (input->screen screen (:input-x screen) (:input-y screen))]
    (->> (for [entity entities]
           (case (:id entity)
             :selector (o/resize-selector entity (:x pos) (:y pos) )
             entity)))))

  :on-touch-up
  (fn [screen entities]
    (println "up")
    (->> (for [entity entities]
           (case (:id entity)
             :selector (remove entity entities)
             entity))))

  :on-render
  (fn [screen entities]
    (->> (for [entity entities]
           (case (:id entity)
             :fps (doto entity (label! :set-text (str (game :fps))))
             entity))
         (render! screen)))

  :on-resize
  (fn [screen entities]
    (height! screen 600)))



; if runtime errors are caught, this screen shows up. Any keypress will then restart the game.
(defscreen blank-screen
  :on-render
  (fn [screen entities]
    (clear!))

  :on-touch-down
  (fn [screen entities]
   (restart-game))

  :on-key-down
  (fn [screen entities]
   (restart-game)))

(set-screen-wrapper! (fn [screen screen-fn]
                       (try (screen-fn)
                         (catch Exception e
                           (.printStackTrace e)
                           (set-screen! rts blank-screen)))))

(defgame rts
  :on-create
  (fn [this]
    (set-screen! this main-screen overlay-screen)))

(-> overlay-screen :entities deref)

;(app! :post-runnable #(set-screen! rts main-screen overlay-screen ))
