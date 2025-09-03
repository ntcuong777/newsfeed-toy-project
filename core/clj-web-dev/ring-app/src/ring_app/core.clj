(ns ring-app.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.util.http-response :as response]
            [ring.middleware.reload :refer [wrap-reload]]
            [reitit.ring :as reitit]
            [muuntaja.middleware :as muuntaja]))

(defn html-handler
  [req-map]
  (response/ok
   (str "<html><body>Hello, Your IP is: " (:remote-addr req-map) "</body></html>")))

(defn json-handler [req]
  (response/ok
   {:result (get-in req [:body-params :id])
    :original-params (:body-params req)}))

(def routes
  [["/" {:get html-handler
         :post html-handler}]
   ["/echo/:id" {:get (fn [{{:keys [id]} :path-params}]
                        (response/ok (str "<p>The value id " id "</p>")))}]
   ["/api" {:middleware [muuntaja/wrap-format]}
    ["/multiply" {:post
                  (fn [{{:keys [a b]} :body-params}]
                    (response/ok {:result (* a b)}))}]]])

(def handler
  (reitit/ring-handler
   (reitit/router routes)
   (reitit/routes
    (reitit/create-resource-handler {:path "/static"})
    (reitit/create-default-handler
     {:not-found
      (constantly (response/not-found "404 - Page not found"))

      :method-not-allowed
      (constantly (response/method-not-allowed "405 - Method not allowed"))

      :not-acceptable
      (constantly (response/not-acceptable "406 - Not acceptable"))}))))

(defn wrap-nocache
  [handler]
  (fn [req]
    (-> req
        handler
        (assoc-in [:headers "Pragma"] "no-cache"))))

(defn wrap-formats [handler]
  (-> handler
      (muuntaja/wrap-format)))

(defn -main
  [& args]
  (jetty/run-jetty
   ;; Use a var for `handler` so that `wrap-reload` can pick up changes to it
   ;; If not passing a var, `handler` will resolve to the original function
   ;; and changes to `handler` will not be picked up
   ;; NOTE: The function can still be called when passed as a var
   (-> #'handler
       wrap-nocache
       wrap-reload)
   {:port 3000 :join? true}))
