(ns tests.all
  (:require
    [cljs.core.async :refer [<! >! chan alts! timeout]]
    [cljs.spec.alpha :as s]
    [cljs.test :refer [deftest is testing run-tests async]]
    [district0x.re-frame.interval-fx]
    [re-frame.core :refer [reg-event-fx dispatch-sync]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(s/check-asserts true)

(reg-event-fx
  ::my-event
  (fn [_ [_ ch]]
    (go (>! ch 1))
    nil))

(reg-event-fx
  ::setup-interval
  (fn [_ [_ ch]]
    {:dispatch-interval {:dispatch [::my-event ch]
                         :id :my-event-interval
                         :ms 500}}))

(reg-event-fx
  ::clear-interval
  (fn [_ [_ ch]]
    {:clear-interval {:id :my-event-interval}}))

(deftest interval-fx-tests
  (async done
    (go
      (let [ch (chan 3)]
        (dispatch-sync [::setup-interval ch])
        (is (= 1 (<! ch)))
        (is (= 1 (<! ch)))
        (is (= 1 (<! ch)))
        (dispatch-sync [::clear-interval])
        (is (nil? (first (alts! [ch (timeout 600)]))))
        (done)))))
