(ns memoservice.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response :as rur]
            [memoservice.core :as memoco]))

(defn- spit-url-str []
  (let [cix @memoco/*current-idx*]
    (swap! memoco/*current-idx* inc)
    (str "http://e-drpciv.ro/intrebare/" cix)))

(defn- spit-main-html []
  (str "<iframe src='"
       (spit-url-str)
       "' "
       "height=100% "
       "width=100%>"
       "</iframe>"))

(defroutes app-routes
  (GET "/" [] (spit-main-html))
  (GET "/reset/:val" [val]
       (reset! memoco/*current-idx* (read-string val))
       (rur/redirect "/"))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
