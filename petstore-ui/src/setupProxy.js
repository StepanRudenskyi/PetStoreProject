const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = function (app) {
  app.use(
    "/api",
    createProxyMiddleware({
      target: "https://localhost:8443",
      changeOrigin: true,
      secure: false,
    })
  );

  app.use(
    "/oauth2/authorization",
    createProxyMiddleware({
      target: "https://localhost:8443",
      changeOrigin: true,
      secure: false,
    })
  );
};
