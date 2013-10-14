(defproject ttt "0.0.0"
  :dependencies [[domina "1.0.1"]
                 [hiccups "0.2.0"]
                 [org.clojure/clojurescript "0.0-1806"]
                 [specljs "2.7.4"]]
  :plugins [[lein-cljsbuild "0.3.3"]
            [specljs "2.7.4"]]
  :cljsbuild ~(let [run-specs ["phantomjs" "bin/spec_runner.js" "public/javascript/ttt_dev.js"]]
                {:builds
                 {:dev {:source-paths ["src" "spec"]
                        :compiler {:output-to "public/javascript/ttt_dev.js"
                                   :pretty-print true
                                   :optimizations :whitespace}
                        :notify-command run-specs}
                  :prod {:source-paths ["src"]
                         :optimizations :simple}
                  :test-commands {"test" run-specs}}})
  )

