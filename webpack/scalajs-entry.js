if (process.env.NODE_ENV === "production") {
    const opt = require("./slinky-diode-todomvc-opt.js");
    opt.main();
    module.exports = opt;
} else {
    var exports = window;
    exports.require = require("./slinky-diode-todomvc-fastopt-entrypoint.js").require;
    window.global = window;

    const fastOpt = require("./slinky-diode-todomvc-fastopt.js");
    fastOpt.main()
    module.exports = fastOpt;

    if (module.hot) {
        module.hot.accept();
    }
}
