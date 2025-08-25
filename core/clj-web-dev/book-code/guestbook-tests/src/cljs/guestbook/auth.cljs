;---
; Excerpted from "Web Development with Clojure, Third Edition",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material,
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose.
; Visit http://www.pragmaticprogrammer.com/titles/dswdcloj3 for more book information.
;---
;
(ns guestbook.auth
  (:require
   [clojure.string :as string]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [reitit.frontend.easy :as rtfe]
   [guestbook.modals :as m]
   [ajax.core :refer [POST]]))

(rf/reg-fx
 :auth/reload
 (fn [[k params query :as args]]
   (apply rtfe/replace-state args)))

(rf/reg-event-fx
 :session/load
 (fn [{:keys [db]} _]
   {:db (assoc db :session/loading? true)
    :ajax/get {:url "/api/session"
               :success-path [:session]
               :success-event [:session/set]}}))

(rf/reg-event-db
 :session/set
 (fn [db [_ {:keys [identity]}]]
   (assoc db
          :auth/user identity
          :session/loading? false)))

(rf/reg-sub
 :session/loading?
 (fn [db _]
   (:session/loading? db)))

(rf/reg-event-fx
 :auth/handle-login
 (fn [{:keys [db]} [_ {:keys [identity]}]]
   (let [{{:keys [name]} :data
          :keys [path-params
                 query-params]} (:router/current-route db)]
     {:db (assoc db :auth/user identity)
      :auth/reload [name path-params query-params]})))

(rf/reg-event-db
 :auth/handle-logout
 (fn [db _]
   (dissoc db :auth/user)))

(rf/reg-sub
 :auth/user
 (fn [db _]
   (:auth/user db)))

(rf/reg-sub
 :auth/user-state
 :<- [:auth/user]
 :<- [:session/loading?]
 (fn [[user loading?]]
   (cond
     (true? loading?) :loading
     user             :authenticated
     :else            :anonymous)))

(defn login-button []
  ;; Copied from guestbook.core...
  ;
  (r/with-let
    [fields (r/atom {})
     error    (r/atom nil)

     do-login
     (fn [_]
       (reset! error nil)
       (POST "/api/login"
         {:headers {"Accept" "application/transit+json"}
          :params @fields
          :handler (fn [response]
                     (reset! fields {})
                     (rf/dispatch [:auth/handle-login response])
                     (rf/dispatch [:app/hide-modal :user/login]))
          :error-handler (fn [error-response]
                           (reset! error
                                   (or (:message (:response error-response))
                                       (:status-text error-response)
                                       "Unknown Error")))}))]
    [m/modal-button :user/login
     ;; Title
     "Log In"
     ;; Body
     [:div
      (when-not (string/blank? @error)
        [:div.notification.is-danger
         @error])
      [:div.field
       [:div.label "Login"]
       [:div.control
        [:input.input
         {:type "text"
          :value (:login @fields)
          :on-change #(swap! fields assoc :login (.. % -target -value))}]]]
      [:div.field
       [:div.label "Password"]
       [:div.control
        [:input.input
         {:type "password"
          :value (:password @fields)
          :on-change #(swap! fields assoc :password (.. % -target -value))
          ;; Submit login form when `Enter` key is pressed
          :on-key-down #(when (= (.-keyCode %) 13)
                          (do-login))}]]]]
     ;; Footer
     [:button.button.is-primary.is-fullwidth
      {:on-click do-login
       :disabled (or (string/blank? (:login @fields)) (string/blank? (:password @fields)))}
      "Log In"]])
  ;
  )

(defn logout-button []
  [:button.button
   {:on-click #(POST "/api/logout"
                 {:handler (fn [_] (rf/dispatch [:auth/handle-logout]))})}
   "Log Out"])

;
(defn nameplate [{:keys [login]}]
  [:a.button.is-primary
   {:href (rtfe/href :guestbook.routes.app/profile)}
   login])
;
;

;
(defn register-button []
  ;; Copied from guestbook.core...
  ;
  (r/with-let
    [fields (r/atom {})
     error    (r/atom nil)

     do-register
     (fn [_]
       (reset! error nil)
       (POST "/api/register"
         {:headers {"Accept" "application/transit+json"}
          :params @fields
          :handler (fn [response]
                     (reset! fields {})
                     (rf/dispatch [:app/hide-modal :user/register])
                     (rf/dispatch [:app/show-modal :user/login]))
          :error-handler (fn [error-response]
                           (reset! error
                                   (or (:message (:response error-response))
                                       (:status-text error-response)
                                       "Unknown Error")))}))]
    [m/modal-button :user/register
     ;; Title
     "Create Account"
     ;; Body
     [:div
      (when-not (string/blank? @error)
        [:div.notification.is-danger
         @error])
      [:div.field
       [:div.label "Login"]
       [:div.control
        [:input.input
         {:type "text"
          :value (:login @fields)
          :on-change #(swap! fields assoc :login (.. % -target -value))}]]]
      [:div.field
       [:div.label "Password"]
       [:div.control
        [:input.input
         {:type "password"
          :value (:password @fields)
          :on-change #(swap! fields assoc :password (.. % -target -value))}]]]
      [:div.field
       [:div.label "Confirm Password"]
       [:div.control
        [:input.input
         {:type "password"
          :value (:confirm @fields)
          :on-change #(swap! fields assoc :confirm (.. % -target -value))
          ;; Submit login form when `Enter` key is pressed
          :on-key-down #(when (= (.-keyCode %) 13)
                          (do-register))}]]]]
     ;; Footer
     [:button.button.is-primary.is-fullwidth
      {:on-click do-register
       :disabled (or (string/blank? (:login @fields))
                     (string/blank? (:password @fields))
                     (string/blank? (:confirm @fields)))}
      "Create Account"]])
  ;
  )
;
;
