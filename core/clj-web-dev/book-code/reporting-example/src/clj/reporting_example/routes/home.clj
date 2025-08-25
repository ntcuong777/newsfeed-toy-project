;---
; Excerpted from "Web Development with Clojure, Third Edition",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material,
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose.
; Visit http://www.pragmaticprogrammer.com/titles/dswdcloj3 for more book information.
;---
;
(ns reporting-example.routes.home
  (:require
   [reporting-example.layout :as layout]
   [reporting-example.db.core :as db]
   [clojure.java.io :as io]
   [clojure.tools.logging :as log]
   [reporting-example.middleware :as middleware]
   [ring.util.http-response :as response]
   [reporting-example.reports :as reports]))
;

;
(defn home-page [request]
  (layout/render request "home.html"))
;

(defn about-page [request]
  (layout/render request "about.html"))

;
(defn write-response [report-bytes]
  (with-open [in (java.io.ByteArrayInputStream. report-bytes)]
    (-> (response/ok in)
        (response/header "Content-Disposition" "filename=document.pdf")
        (response/header "Content-Length" (count report-bytes))
        (response/content-type "application/pdf"))))
;

;
(defn generate-report [{:keys [path-params] :as request}]
  (try
    (let [report-type (:report-type path-params)
          out (java.io.ByteArrayOutputStream.)]
      (condp = (keyword report-type)
        :table (reports/table-report out)
        :list  (reports/list-report out))
      (write-response (.toByteArray out)))
    (catch Exception ex
      (log/error ex "failed to render report!")
      (layout/render request "home.html" {:error (.getMessage ex)}))))
;

;
(defn home-routes []
  [""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/about" {:get about-page}]
   ["/report/:report-type" {:get generate-report}]])
;
