package com.yhl.http;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.yhl.http.handles.AppCodeHandler;
import com.yhl.http.handles.LicenseVerifyHandler;
import com.yhl.http.handles.ReadHandler;
import com.yhl.http.license.manager.LicenseVerifyManager;
import com.yhl.http.model.DataConfig;
import com.yhl.http.model.LicenseVerifyProperties;
import com.yhl.http.util.FileUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
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
    router.route("/api/*").handler(new LicenseVerifyHandler(vertx));
    router.get("/api/:apiType").handler(new ReadHandler(vertx));
    router.get("/app/getCode").handler(new AppCodeHandler());
    router.route("/*").handler(StaticHandler.create());
    server.requestHandler(router);
    server.listen(config().getInteger("httpPort"),
      config().getString("httpIP"), http -> {
        if (http.succeeded()) {
          startPromise.complete();
          logger.info("HTTP server started on port " + config().getInteger("httpPort"));
          validateLicense();
        } else {
          startPromise.fail(http.cause());
        }
      });
  }

  @Override
  public void stop() throws Exception {
    super.stop();
    LicenseVerifyManager.uninstall(licenseVerifyProperties.getVerifyParam());
  }

  private void validateLicense() {
    DataConfig config = config().mapTo(DataConfig.class);
    licenseVerifyProperties = config.getLicense().getVerify();
    if (StrUtil.isNotEmpty(licenseVerifyProperties.getLicensePath())) {
      if (FileUtil.exist(licenseVerifyProperties.getLicensePath()) && install()) {
        String readMd5 = FileUtils.getMd5(licenseVerifyProperties.getLicensePath());
        if (StrUtil.isBlank(md5.get()) && StrUtil.isNotBlank(readMd5)) {
          md5.set(readMd5);
        }
      }
    } else {
      logger.warn("未检测到license文件，请提供");
    }
    vertx.setPeriodic(5000,l-> licenseChecker());
  }

  private void licenseChecker(){
    if (FileUtil.exist(licenseVerifyProperties.getLicensePath())) {
      String readMd5 = FileUtils.getMd5(licenseVerifyProperties.getLicensePath());
      // 不相等，说明lic变化了
      if (!StrUtil.equals(md5.get(), readMd5) && StrUtil.isNotBlank(readMd5) && install()) {
        md5.set(readMd5);
      }
    }else {
      logger.warn("未检测到license文件，请提供");
      if (StrUtil.isNotBlank(md5.get())) {
        LicenseVerifyManager.uninstall(licenseVerifyProperties.getVerifyParam());
        md5.set(StrUtil.EMPTY);
      }
    }
  }
  private Boolean install() {
    logger.info("++++++++ 开始安装证书 ++++++++");

    // 走定义校验证书并安装
    try {
      LicenseVerifyManager.install(licenseVerifyProperties.getVerifyParam());
      logger.info("++++++++ 证书安装成功 ++++++++");
      return Boolean.TRUE;
    } catch (Exception e) {
      logger.error("++++++++ 证书安装失败 ++++++++");
    }
    return Boolean.FALSE;
  }


}
