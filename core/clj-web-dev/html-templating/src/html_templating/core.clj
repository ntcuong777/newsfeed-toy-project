(ns html-templating.core
  (:require [selmer.parser :as selmer]
            [selmer.filters :as filters]
            [selmer.middleware :as middleware :refer [wrap-error-page]]))

(defn renderer []
  (wrap-error-page
   (fn [template]
     {:status 200
      :body (selmer/render-file template {})})))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
