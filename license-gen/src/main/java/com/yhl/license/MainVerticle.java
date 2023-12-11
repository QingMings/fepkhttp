package com.yhl.license;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.yhl.http.license.manager.LicenseVerifyManager;
import com.yhl.http.model.LicenseVerifyProperties;
import com.yhl.http.util.FileUtils;
import com.yhl.license.handles.LicenseGenerateHandler;
import com.yhl.license.handles.PrivateKeyHandler;
import com.yhl.license.handles.PublicCertsHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

public class MainVerticle extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(MainVerticle.class);
  private final AtomicReference<String> md5 = new AtomicReference<>(StrUtil.EMPTY);
  LicenseVerifyProperties licenseVerifyProperties;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    HttpServer server = vertx.createHttpServer();
    Router router = Router.router(vertx);
    router.get("/api/license/privateKeys").handler(new PrivateKeyHandler(vertx));
    router.get("/api/license/publicCerts").handler(new PublicCertsHandler(vertx));
    router.post("/api/license/generate").handler(BodyHandler.create());
    router.post("/api/license/generate").handler(new LicenseGenerateHandler(vertx));
    router.route("/*").handler(StaticHandler.create());
    server.requestHandler(router);
    server.listen(config().getInteger("httpPort"),
      config().getString("httpIP"), http -> {
        if (http.succeeded()) {
          startPromise.complete();
          logger.info("DSV licence service : HTTP server started on port " + config().getInteger("httpPort"));

        } else {
          startPromise.fail(http.cause());
        }
      });
  }






}
