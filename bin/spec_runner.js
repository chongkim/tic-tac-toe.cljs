#!/usr/bin/env phantomjs

var fs = require("fs");
var p = require("webpage").create();

p.onConsoleMessage = function(x) {
  fs.write("/dev/stdout", x, "w");
};

p.injectJs(phantom.args[0]);

result = p.evaluate(function() {
  specljs.run.standard.armed = true;
  return specljs.run.standard.run_specs(
    cljs.core.keyword("color"), true,
    cljs.core.keyword("reporters"), ["documentation"]
  );
});

phantom.exit(result);

