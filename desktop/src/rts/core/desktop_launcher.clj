(ns rts.core.desktop-launcher
  (:require [rts.core :refer :all])
  (:import [com.badlogic.gdx.backends.lwjgl LwjglApplication]
           [org.lwjgl.input Keyboard])
  (:gen-class))

(defn -main
  []
  (LwjglApplication. rts "rts" 800 600)
  (Keyboard/enableRepeatEvents true))
