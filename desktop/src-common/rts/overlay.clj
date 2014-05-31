(ns rts.overlay
  (:require [play-clj.core :refer :all]
            [play-clj.g2d :refer :all]
            [play-clj.ui :refer :all]))

(defn create-selector [x y]
  "Creates a selection rectangle on pos x and y"
  (println (str "Creating rect at: (" x "," y ")" ))
  (assoc (shape :filled 
         :set-color (color :red)
         :rect x y 0 0)
         :id :selector
         :origin-x x
         :origin-y y))
  
(defn abs "(abs n) is the absolute value of n" [n]
  (cond
   (not (number? n)) (throw (IllegalArgumentException.
                             "abs requires a number"))
   (neg? n) (- n)
   :else n))

(defn resize-selector [selector new-x new-y]
  "Resizes the selector to the new x and y position"
  (let [old-x (:origin-x selector)
        old-y (:origin-y selector)
        width (abs (- old-x new-x))
        height (abs (- old-y new-y))
        ; if we drag down-rightwards, the origin will change to the lowest bottom corner
        ; so just get the minimal coord and use it as a rect
        new-origin-x (min new-x old-x)
        new-origin-y (min new-y old-y)
    ]
  (println (str "old-x" old-x))
  (println (str "old-y" old-y))
  (shape selector :rect new-origin-x new-origin-y width height)
  ))

