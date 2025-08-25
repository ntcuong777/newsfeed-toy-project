;---
; Excerpted from "Web Development with Clojure, Third Edition",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material,
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose.
; Visit http://www.pragmaticprogrammer.com/titles/dswdcloj3 for more book information.
;---
<code file="code/liberator-snippets/home.clj" part="home-resource"/>
(ns liberator-service.routes.home
  (:require [compojure.core :refer :all]
            [liberator.core
             :refer [defresource resource request-method-in]]))

(defn loading-page [request]
      (html5
        (head)
        [:body {:class "body-container"}
         mount-target
         (include-js "/js/app.js")]))

(def app
  (reitit-ring/ring-handler
    (reitit-ring/router
      [["/" {:get (resource
                    :handle-ok loading-page
                    :etag "fixed-etag"
                    :available-media-types ["text/html"])}]])
    (reitit-ring/routes
      (reitit-ring/create-resource-handler {:path "/" :root "/public"})
      (reitit-ring/create-default-handler))
    {:middleware middleware}))

(defresource home
             :handle-ok loading-page
             :etag "fixed-etag"
             :available-media-types ["text/html"])

(def app
  (reitit-ring/ring-handler
    (reitit-ring/router
      [["/" {:get home}]])
    (reitit-ring/routes
      (reitit-ring/create-resource-handler {:path "/" :root "/public"})
      (reitit-ring/create-default-handler))
    {:middleware middleware}))

(defresource home
             :service-available? false
             :handle-ok loading-page
             :etag "fixed-etag"
             :available-media-types ["text/html"])

(defresource home
  :method-allowed?
  (fn [context]
    (= :get (get-in context [:request :request-method])))
  :handle-ok loading-page
  :etag "fixed-etag"
  :available-media-types ["text/html"])

(defresource home
  :allowed-methods [:get]
  :handle-ok loading-page
  :etag "fixed-etag"
  :available-media-types ["text/html"])

(defresource home
  :service-available? true

  :method-allowed? (request-method-in :get)

  :handle-method-not-allowed
  (fn [context]
    (str (get-in context [:request :request-method]) " is not allowed"))

  :handle-ok loading-page
  :etag "fixed-etag"
  :available-media-types ["text/html"])

(defresource home
  :service-available? false
  :handle-service-not-available
  "service is currently unavailable..."

  :method-allowed? (request-method-in :get)
  :handle-method-not-allowed
  (fn [context]
    (str (get-in context [:request :request-method]) " is not allowed"))

  :handle-ok loading-page
  :etag "fixed-etag"
  :available-media-types ["text/html"])

(defresource items
  :allowed-methods [:get]
  :handle-ok (fn [_] (io/file "items"))
  :available-media-types ["text/plain"])

(defresource items
  :allowed-methods [:get :post]
  :handle-ok (fn [_] (io/file "items"))
  :available-media-types ["text/plain"]

  :post!
  (fn [context]
    (let [item (-> context :request :params :item)]
      (spit (io/file "items") (str item "\n") :append true)))
  :handle-created (io/file "items"))




