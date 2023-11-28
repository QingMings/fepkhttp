package com.yhl.http;


import com.yhl.http.handles.ReadHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    HttpServer server = vertx.createHttpServer();
    Router router = Router.router(vertx);
    router.get("/api/:apiType").handler(new ReadHandler(vertx));
    server.requestHandler(router);
    server.listen(config().getInteger("httpPort"),
      config().getString("httpIP"), http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port "+config().getInteger("httpPort"));
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}