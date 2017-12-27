const PROXY_CONFIG = {
  "/api/*": {
    "target": "http://localhost:8085",
    "secure": false,
    "logLevel": "debug",
    "bypass": function (req, res, proxyOptions) {
      if (req.headers["cookie"]) {
        var vdabAuth = /vdabauthorization=([^;]*);?/g.exec(req.headers["cookie"]);
        if (vdabAuth && vdabAuth.length > 1) {
          req.headers["vdabauthorization"] = vdabAuth[1];
        }
      }
    }
  }
};

module.exports = PROXY_CONFIG;
