package com.yhl.http.handles;

import com.yhl.http.license.listener.VerifyListener;
import com.yhl.http.license.manager.LicenseVerifyManager;
import com.yhl.http.model.DataConfig;
import com.yhl.http.model.LicenseExtraParam;
import com.yhl.http.model.LicenseVerifyProperties;
import de.schlichtherle.license.LicenseContent;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * License verify
 */
public class LicenseVerifyHandler implements Handler<RoutingContext> {
  private static final Logger logger = LoggerFactory.getLogger(LicenseVerifyHandler.class);

  private final Vertx vertx;
  private LicenseVerifyProperties licenseVerifyProperties;

  public LicenseVerifyHandler(Vertx vertx) {
    this.vertx = vertx;
    setLicenseVerifyProperties(vertx.getOrCreateContext().config());
  }

  public LicenseVerifyProperties getLicenseVerifyProperties() {
    return licenseVerifyProperties;
  }

  public void setLicenseVerifyProperties(JsonObject config) {
    DataConfig dataConfig = config.mapTo(DataConfig.class);
    this.licenseVerifyProperties = dataConfig.getLicense().getVerify();
  }

  @Override
  public void handle(RoutingContext routingContext) {
    logger.debug("verify start");
    try {
      // 校验证书是否有效
      LicenseContent content = LicenseVerifyManager.verify(licenseVerifyProperties.getVerifyParam());
      LicenseExtraParam licenseCheck = (LicenseExtraParam) content.getExtra();
      boolean compare = true;
      // 增加业务系统监听，是否自定义验证
      List<VerifyListener> customListenerList = VerifyListener.getListenerList();
      for (VerifyListener listener : customListenerList) {
        boolean verify = listener.verify(licenseCheck);
        compare = compare && verify;
      }
      if (!compare) {
        response401(routingContext.response(), "软件许可验证不通过, 请联系管理员");
      } else {
        routingContext.next();
      }
    } catch (Exception e) {

      response401(routingContext.response(), e.getMessage());
    }
    logger.debug("verify end");
  }

  private void response401(HttpServerResponse response, String message) {
    response.putHeader("Content-Type", "application/json");
    response.putHeader("charset", "UTF-8");
    response.setStatusCode(401).send(JsonObject.of("code", "401", "message", message).encode());
  }
}
