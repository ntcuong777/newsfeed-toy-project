;---
; Excerpted from "Web Development with Clojure, Third Edition",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material,
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose.
; Visit http://www.pragmaticprogrammer.com/titles/dswdcloj3 for more book information.
;---
(ns guestbook.author
  (:require [guestbook.db.core :as db]))

(defn get-author [login]
  (db/get-user* {:login login}))

(defn set-author-profile [login profile]
  (db/set-profile-for-user* {:login login
                             :profile profile}))