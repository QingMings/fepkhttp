package com.yhl.http;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.spi.resolver.ResolverProvider;

public class Launcher extends io.vertx.core.Launcher {
  private static final Logger logger = LoggerFactory.getLogger(Launcher.class);
  public static void main(String[] args) {
    new Launcher().dispatch(args);
  }
  @Override
  public void beforeDeployingVerticle(DeploymentOptions deploymentOptions) {
    super.beforeDeployingVerticle(deploymentOptions);
    System.setProperty(ResolverProvider.DISABLE_DNS_RESOLVER_PROP_NAME, "true");
  }
  @Override
  public void beforeStartingVertx(VertxOptions options) {
    super.beforeStartingVertx(options);
  }
  @Override
  public void afterStartingVertx(Vertx vertx) {
    super.afterStartingVertx(vertx);
  }
}
