package com.yhl.license.handles;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import com.yhl.http.exceptions.LicenseException;
import com.yhl.http.util.FileUtils;
import com.yhl.http.util.KeyStoreUtils;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PublicCertsHandler implements Handler<RoutingContext> {
  private final Vertx vertx;
  private static final String PWD_REG = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$";

  public PublicCertsHandler(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public void handle(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    String validity = routingContext.request().getParam("validity");
    String storePwd = routingContext.request().getParam("storePwd");
    String publicPwd = routingContext.request().getParam("publicPwd");

    try {
      ObjectUtil.defaultIfNull(validity, "1");
      byte[] publicCerts = genPublicCerts(Long.valueOf(validity), storePwd, publicPwd);
      Buffer buffer = Buffer.buffer(publicCerts);
      response.setStatusCode(200).putHeader("Content-Type", "application/octet-stream")
        .putHeader("Content-Length", String.valueOf(buffer.length()))
        .putHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(KeyStoreUtils.PUBLIC_CERTS, "UTF-8"))
        .send(buffer);
    } catch (Exception e) {
      response.putHeader("Content-Type", "application/json");
      response.putHeader("charset", "UTF-8");
      response.setStatusCode(200).send(JsonObject.of("code", "500", "message", "创建公钥失败" + e.getMessage()).encode());
    }
  }

  public byte[] genPublicCerts(Long validity, String storePwd, String publicPwd) {

    Assert.notBlank(storePwd, "密钥库密码不能为空");
    Assert.notBlank(publicPwd, "公钥库密码不能为空");
    Assert.isTrue(ReUtil.isMatch(PWD_REG, storePwd), "密钥库密码必须由字母和数字组成的至少6个字符组成");
    Assert.isTrue(ReUtil.isMatch(PWD_REG, publicPwd), "公钥库密码必须由字母和数字组成的至少6个字符组成");

    FileUtil.mkdir(FileUtils.KEY_STORE_DIR);

    // 如果私钥不存在，先生成私钥
    if (!FileUtil.exist(FileUtils.KEY_STORE_DIR + KeyStoreUtils.KEY_STORE)) {
      throw new LicenseException("私钥不存在, 请先生成私钥");
    }

    byte[] cer = KeyStoreUtils.getCer(FileUtil.readBytes(FileUtils.KEY_STORE_DIR + KeyStoreUtils.KEY_STORE), KeyStoreUtils.PRIVATE_KEYS_ALIAS, storePwd);
    FileUtils.del(FileUtils.KEY_STORE_DIR + KeyStoreUtils.KEY_STORE);

    return KeyStoreUtils.getPublicKey(cer, KeyStoreUtils.PUBLIC_CERT_ALIAS, publicPwd);
  }
}
