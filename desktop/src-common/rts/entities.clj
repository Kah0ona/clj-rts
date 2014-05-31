(ns rts.entities
  (:require [play-clj.core :refer :all]
            [play-clj.g2d :refer :all]
            [play-clj.ui :refer :all]))


(defn create-tank [id x y]
  "Creates a tank with id on x y coords"
  (assoc (shape :filled
         :set-color (color :green)
         :rect x y 30 10)
         :tank? true
         :unit? true
         :id id
         :origin-x x
         :origin-y y
         :health 100
         ))

(defn overlaps? [selector unit]
  true)

(defn get-units-under-selection [selector units]
  (let [selected-units (filter #(overlaps? selector %) units)]
  (println (str "units: " (count units)))
  (println (str "selector: " selector))
  (println (str "selected units: " (count selected-units)))
  selected-units))

(defn highlight-unit [unit] 
  "Highlights a tank by changing its color to red"
  (println (str "Highlighting: " unit))
  (assoc unit :set-color (color :red)))

(defn damage-tank [tank dmg]
  "Damages a tank with dmg amount dmg. Does not destroy it when it's below 0 health, so no side-effects, beyond hp "
  (assoc tank :health (- (:health tank) dmg)))
