;---
; Excerpted from "Web Development with Clojure, Third Edition",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material,
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose.
; Visit http://www.pragmaticprogrammer.com/titles/dswdcloj3 for more book information.
;---
;
(ns guestbook.core-test
  (:require [cljs.test :refer-macros [is are deftest testing use-fixtures]]
            [pjstadig.humane-test-output]
            [reagent.dom.server :as dom]
            [guestbook.components :as gc]))
;

;
(deftest test-md
  (is (= "<p class=\"markdown\" data-reactroot=\"\"><h3>Hello</h3></p>"
         (dom/render-to-string (gc/md "### Hello")))))
;