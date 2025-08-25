;---
; Excerpted from "Web Development with Clojure, Third Edition",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material,
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose.
; Visit http://www.pragmaticprogrammer.com/titles/dswdcloj3 for more book information.
;---
;
(ns db-examples.core
  (:require
    [next.jdbc :as jdbc]
    [next.jdbc.sql :as sql]
    [next.jdbc.result-set :as rs]))
;

;
(def ds (jdbc/get-datasource
          {:subprotocol "postgresql"
           :subname "//localhost/reporting"
           :user "admin"
           :password "admin"}))
;

;
(defn create-users-table! [ds]
  (jdbc/execute! ds
    ["create table users (
       id varchar(32) primary key,
       pass varchar(100)
      )"]))
;

;
(defn get-user [ds id]
  (first (sql/query ds ["select * from users where id = ?" id])))
;

;
(defn add-user! [ds user]
  (sql/insert! ds :users user))
;

;
(defn add-users! [ds users]
  (sql/insert-multi! ds :users [:id :pass] users))
;

;
(defn set-pass! [ds id pass]
  (sql/update!
    ds
    :users
    {:pass pass}
    ["id=?" id]))
;

;
(defn remove-user! [ds id]
  (sql/delete! ds :users ["id=?" id]))
;
