const Cu = Components.utils;

var y = null

function startup(data, reason) {
    Cu.import("chrome://ghost/content/ghost.js");

    if(!y) {
        y = new ghost();
    }
    y.register();
}
function shutdown(data, reason) {
    if (reason == APP_SHUTDOWN) return;

        if(y) {
            y.unregister();
            y = null;
        }
    Cu.unload("chrome://ghost/content/ghost.js");
}

function install(data, reason) {
}

function uninstall(data, reason) {
}
