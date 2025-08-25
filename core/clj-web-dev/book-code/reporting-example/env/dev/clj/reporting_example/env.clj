;---
; Excerpted from "Web Development with Clojure, Third Edition",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material,
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose.
; Visit http://www.pragmaticprogrammer.com/titles/dswdcloj3 for more book information.
;---
(ns reporting-example.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [reporting-example.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[reporting-example started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[reporting-example has shut down successfully]=-"))
   :middleware wrap-dev})
