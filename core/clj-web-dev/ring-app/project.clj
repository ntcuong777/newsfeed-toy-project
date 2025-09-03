(defproject ring-app "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [metosin/muuntaja "0.6.7"] ;; middlewares fpr content negotiation, encoding, etc. for various common data formats
                 [metosin/reitit "0.5.11"]
                 [metosin/ring-http-response "0.9.1"] ;; utilities for generating HTTP responses
                 [ring "1.8.2"]]
  :plugins [[cider/cider-nrepl "0.57.0"]]
  :repl-options {:init-ns ring-app.core}
  :main ring-app.core)
