;---
; Excerpted from "Web Development with Clojure, Third Edition",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material,
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose.
; Visit http://www.pragmaticprogrammer.com/titles/dswdcloj3 for more book information.
;---
(ns liberator-service.handler
  (:require
   [reitit.ring :as reitit-ring]
   [liberator-service.middleware :refer [middleware]]
   [hiccup.page :refer [include-js include-css html5]]
   [config.core :refer [env]]
   [clojure.java.io :as io]
   [liberator.core :refer [defresource resource request-method-in]]
   [ring.util.anti-forgery :refer [anti-forgery-field]]))

(def mount-target
  [:div#app
   [:h2 "Welcome to liberator-service"]
   [:p "please wait while Figwheel is waking up ..."]
   [:p "(Check the js console for hints if nothing exciting happens.)"]])

(defn head []
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css (if (env :dev) "/css/site.css" "/css/site.min.css"))])

(defn loading-page [request]
  (html5
   [:html
    (head)
    [:body
     (anti-forgery-field)
     [:p (str (anti-forgery-field))]
     mount-target
     (include-js "/js/app.js")]]))

(defresource home
  :allowed-methods [:get]
  :handle-ok loading-page
  :etag "fixed-etag"
  :available-media-types ["text/html"])

(defresource items
  :allowed-methods [:get :post]
  :handle-ok (fn [_] (io/file "items"))
  :available-media-types ["text/plain"]

  :post!
  (fn [context]
      (let [item (-> context :request :params :item)]
           (spit (io/file "items") (str item "\n") :append true)))
  :handle-created "ok"

  :malformed? (fn [context]
                  (-> context :request :params :item empty?))
  :handle-malformed "item value cannot be empty!")

(def app
  (reitit-ring/ring-handler
   (reitit-ring/router
    [["/" {:get home}]
     ["/items" items]])
   (reitit-ring/routes
    (reitit-ring/create-resource-handler {:path "/" :root "/public"})
    (reitit-ring/create-default-handler))
   {:middleware middleware}))
