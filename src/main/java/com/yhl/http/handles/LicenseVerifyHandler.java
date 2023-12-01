package com.yhl.http.handles;

import com.yhl.http.MainVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * License verify
 */
public class LicenseVerifyHandler implements Handler<RoutingContext> {
  private static final Logger logger = LoggerFactory.getLogger(LicenseVerifyHandler.class);

  private final Vertx vertx;
  public LicenseVerifyHandler(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public void handle(RoutingContext routingContext) {
    logger.info("verify start");
     routingContext.next();
    logger.info("verify end");
  }
}
