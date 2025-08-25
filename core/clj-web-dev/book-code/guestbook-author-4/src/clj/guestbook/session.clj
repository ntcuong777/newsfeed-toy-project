;---
; Excerpted from "Web Development with Clojure, Third Edition",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material,
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose.
; Visit http://www.pragmaticprogrammer.com/titles/dswdcloj3 for more book information.
;---
(ns guestbook.session
  (:require
   [ring-ttl-session.core :refer [ttl-memory-store]]))

(defonce store (ttl-memory-store (* 60 30)))

(defn ring-req->session-key [req]
  (get-in req [:cookies "ring-session" :value]))

(defn read-session [req]
  (.read-session store (ring-req->session-key req)))

(defn write-session [req v]
  (.write-session store (ring-req->session-key req) v))
