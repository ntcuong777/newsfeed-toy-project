;---
; Excerpted from "Web Development with Clojure, Third Edition",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material,
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose.
; Visit http://www.pragmaticprogrammer.com/titles/dswdcloj3 for more book information.
;---
(ns guestbook.auth.ring
  (:require
   [clojure.tools.logging :as log]
   [guestbook.auth :as auth]
   [reitit.ring :as ring]))

(defn authorized? [roles req]
  (if (seq roles)
    (->> req
         :session
         :identity
         auth/identity->roles
         (some roles)
         boolean)
    (do
      (log/error "roles: " roles " is empty for route: " (:uri req))
      false)))

(defn get-roles-from-match [req]
  (-> req
      (ring/get-match)
      (get-in [:data ::auth/roles] #{})))

(defn wrap-authorized [handler unauthorized-handler]
  (fn [req]
    (if (authorized? (get-roles-from-match req) req)
      (handler req)
      (unauthorized-handler req))))
