;---
; Excerpted from "Web Development with Clojure, Third Edition",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material,
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose.
; Visit http://www.pragmaticprogrammer.com/titles/dswdcloj3 for more book information.
;---
(defproject ring-app "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  ;
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [metosin/muuntaja "0.6.7"]
                 [metosin/reitit "0.5.11"]
                 [metosin/ring-http-response "0.9.1"]
                 [ring "1.8.2"]]
  ;
  :repl-options {:init-ns ring-app.core}
  :main ring-app.core)
