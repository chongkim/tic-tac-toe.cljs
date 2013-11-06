(defproject ttt "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[domina "1.0.1"]
                 [hiccups "0.2.0"]
                 [org.clojure/clojure "1.5.1"]]

  :profiles {:dev {:dependencies [[speclj "2.5.0"]
                                  [specljs "2.7.4"]]}}
  :plugins [[speclj "2.5.0"]
            [specljs "2.7.4"]
            [lein-cljsbuild "0.3.2"]]

  :cljsbuild ~(let [run-specs ["phantomjs" "bin/specljs_runner.js"  "public/javascript/ttt_dev.js"]]
          {:builds {:dev {:source-paths ["src/cljs" "spec/cljs"]
                               :compiler {:output-to "public/javascript/ttt_dev.js"
                                          :optimizations :whitespace
                                          :pretty-print true}
                          :notify-command run-specs}

                       :prod {:source-paths ["src/cljs"]
                               :compiler {:output-to "public/javascript/ttt.js"
                                          :optimizations :simple}}}

              :test-commands {"test" run-specs}})

  :source-paths ["src/clj" "src/cljs"]
  :test-paths ["spec/clj"])
