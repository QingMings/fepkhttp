package com.yhl.license.handles;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.yhl.http.license.manager.LicenseCreatorManager;
import com.yhl.http.model.LicenseCreatorParam;
import com.yhl.http.model.LicenseCreatorV2Param;
import com.yhl.http.util.FileUtils;
import com.yhl.http.util.KeyStoreUtils;
import com.yhl.http.util.ServerInfoUtils;
import de.schlichtherle.license.LicenseContent;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

public class LicenseGenerateHandler implements Handler<RoutingContext> {
  private static final Logger logger = LoggerFactory.getLogger(LicenseGenerateHandler.class);
  private final Vertx vertx;

  public LicenseGenerateHandler(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public void handle(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    JsonObject  params = routingContext.body().asJsonObject();

    String keyPwd = params.getString("keyPwd");
    String storePwd = params.getString("storePwd");
    String privateKeysStorePath = params.getString("privateKeysStorePath");
    String consumerAmount = params.getString("consumerAmount");
    String expiryTime = params.getString("expiryTime");
    String appCode = params.getString("appCode");
    try {
      Assert.notNull(keyPwd, "keyPwd 不能为空");
      Assert.notNull(storePwd, "storePwd 不能为空");
      Assert.notNull(privateKeysStorePath, "privateKeysStorePath 不能为空");
      Assert.notNull(consumerAmount, "consumerAmount 不能为空");
      Assert.notNull(expiryTime, "expiryTime 不能为空");
      Assert.notNull(appCode, "appCode 不能为空");
      LicenseCreatorParam licenseCreatorParam = new LicenseCreatorParam();
      licenseCreatorParam.setKeyPwd(keyPwd);
      licenseCreatorParam.setStorePwd(storePwd);
      licenseCreatorParam.setPrivateKeysStorePath(privateKeysStorePath);
      licenseCreatorParam.setConsumerAmount(Integer.valueOf(consumerAmount));
      licenseCreatorParam.setExpiryTime(DateUtil.parseDateTime(expiryTime));
      licenseCreatorParam.setAppCode(appCode);
      String licensePath = generateLicense(licenseCreatorParam);
      Assert.isTrue(Files.exists(Paths.get(licensePath)), "文件不存在");
      Buffer buffer = Buffer.buffer(FileUtil.readBytes(licensePath));
      response.setStatusCode(200).putHeader("Content-Type", "application/octet-stream")
        .putHeader("Content-Length", String.valueOf(buffer.length()))
        .putHeader("content-disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode(FileUtils.LICENSE_FILE, "UTF-8"))
        .send(buffer);
    } catch (Exception e) {
      response.putHeader("Content-Type", "application/json");
      response.putHeader("charset", "UTF-8");
      response.setStatusCode(200).send(JsonObject.of("code", "500", "message", "创建License失败" + e.getMessage()).encode());
    }
  }

  public String generateLicense(LicenseCreatorParam param) {

    String licDir = StrUtil.replace(ServerInfoUtils.getServerTempPath(), File.separator, FileUtils.SEPARATOR) + FileUtils.LIC_DIR;
    FileUtil.mkdir(licDir);
    String fileName = licDir + FileUtils.LICENSE_FILE;
    LicenseContent licenseContent = LicenseCreatorManager.generateLicense(param, licDir + FileUtils.LICENSE_FILE);

    String message = MessageFormat.format("证书生成成功，证书有效期：{0} - {1}",
      DateUtil.format(licenseContent.getNotBefore(), DatePattern.NORM_DATETIME_FORMAT),
      DateUtil.format(licenseContent.getNotAfter(), DatePattern.NORM_DATETIME_FORMAT));

    logger.info(message);

    return fileName;
  }
}
