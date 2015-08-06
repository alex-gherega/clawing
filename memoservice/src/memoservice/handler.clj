(ns memoservice.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response :as rur]
            [memoservice.core :as memoco]))

(defn- spit-url-str [a swap-fn a-fn]
  (let [cix @a]
    (swap! a swap-fn)
    (str "http://e-drpciv.ro/intrebare/" (a-fn cix))))

(defn- spit-main-html [a swap-fn a-fn]
  (str "<iframe src='"
       (spit-url-str a swap-fn a-fn)
       "' "
       "height=100% "
       "width=100%>"
       "</iframe>"))

(defroutes app-routes
  (GET "/" [] (spit-main-html memoco/*current-idx* inc identity))

  (GET "/reverse" [] (spit-main-html memoco/*current-idx* dec identity))

  (GET "/vip" [] (spit-main-html memoco/*vip-idxs*
                                 rest
                                 first))

  (GET "/mip" [] (spit-main-html memoco/*mip-idxs*
                                 rest
                                 first))

  (GET "/reset/vip" []
       (memoco/reset-vip)
       (rur/redirect "/vip"))

  (GET "/reset/mip" []
       (memoco/reset-mip)
       (rur/redirect "/mip"))

  (GET "/reset/:val" [val]
       (reset! memoco/*current-idx* (read-string val))
       (rur/redirect "/"))

  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
