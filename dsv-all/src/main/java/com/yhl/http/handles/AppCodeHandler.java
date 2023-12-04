package com.yhl.http.handles;

import com.yhl.http.util.EncryptionUtils;
import com.yhl.http.util.ServerInfoUtils;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class AppCodeHandler implements Handler<RoutingContext> {
  @Override
  public void handle(RoutingContext routingContext) {
    String code = EncryptionUtils.encode(ServerInfoUtils.getServerInfos());
    routingContext.response().send(code);
  }
}
