;---
; Excerpted from "Web Development with Clojure, Third Edition",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material,
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose.
; Visit http://www.pragmaticprogrammer.com/titles/dswdcloj3 for more book information.
;---
;
;
(ns ring-app.core
  (:require
   ;
   ;;...
   ;
   ;
   [ring.adapter.jetty :as jetty]
   [ring.util.http-response :as response]
   ;
   [ring.middleware.reload :refer [wrap-reload]]))
;

(defn handler [request-map]
  (response/ok
   (str "<html><body> your IP is: "
        (:remote-addr request-map)
        "</body></html>")))
;

(defn wrap-nocache [handler]
  (fn [request]
    (-> request
        handler
        (assoc-in [:headers "Pragma"] "no-cache"))))

;

(defn -main []
  (jetty/run-jetty
   (-> handler
       var
       wrap-nocache
       wrap-reload)
   {:port 3000
    :join? false}))
;
