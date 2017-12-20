# re-frame-interval-fx

[![Build Status](https://travis-ci.org/district0x/re-frame-interval-fx.svg?branch=master)](https://travis-ci.org/district0x/re-frame-interval-fx)

[re-frame](https://github.com/Day8/re-frame) effect handler for setting up event calling in certain interval.

## Installation
Add `[district0x/re-frame-interval-fx "1.0.0"]` into your project.clj    
Include `[district0x.re-frame.interval-fx]` in your CLJS file

## Usage
```clojure
(reg-event-fx
  :some-event
  (fn []
    {:dispatch-interval {:dispatch [:my-repeated-event]
                         :id :my-repeated-event-interval
                         :ms 1000}}))
                         
(reg-event-fx
  :some-other-event
  (fn []
    {:clear-interval {:id :my-repeated-event-interval}}))
```
## Development
```bash
lein deps

# To run tests and rerun on changes
lein doo chrome tests
```