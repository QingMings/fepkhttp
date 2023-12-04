package com.yhl.license.handles;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
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

public class PrivateKeyHandler implements Handler<RoutingContext> {
  private final Vertx vertx;
  private static final String PWD_REG = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$";

  public PrivateKeyHandler(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public void handle(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    String validity = routingContext.request().getParam("validity");
    String storePwd = routingContext.request().getParam("storePwd");
    String keyPwd = routingContext.request().getParam("keyPwd");

    try {
      ObjectUtil.defaultIfNull(validity,"1");
      byte[] privateKeys = genPrivateKeys(Long.valueOf(validity), storePwd, keyPwd);
      Buffer buffer = Buffer.buffer(privateKeys);
      response.setStatusCode(200).putHeader("Content-Type", "application/octet-stream")
        .putHeader("Content-Length", String.valueOf(buffer.length()))
        .putHeader("content-disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode(KeyStoreUtils.PRIVATE_KEYS, "UTF-8"))
        .send(buffer);
    } catch (Exception e) {
      response.putHeader("Content-Type", "application/json");
      response.putHeader("charset", "UTF-8");
      response.setStatusCode(200).send(JsonObject.of("code", "500", "message", "创建私钥失败" + e.getMessage()).encode());
    }


  }

  public byte[] genPrivateKeys(Long validity, String storePwd, String keyPwd) {

    Assert.notBlank(storePwd, "秘钥库密码不能为空");
    Assert.notBlank(keyPwd, "私钥密码不能为空");

    Assert.isTrue(ReUtil.isMatch(PWD_REG, storePwd), "秘钥库密码必须由字母和数字组成的至少6个字符组成");
    Assert.isTrue(ReUtil.isMatch(PWD_REG, keyPwd), "私钥密码必须由字母和数字组成的至少6个字符组成");

    FileUtil.mkdir(FileUtils.KEY_STORE_DIR);
    if (validity <= 0) validity = 1L;
    byte[] keyPair = KeyStoreUtils.genkeyPair(KeyStoreUtils.PRIVATE_KEYS_ALIAS, validity, storePwd, keyPwd);
    FileUtil.writeBytes(keyPair, FileUtils.KEY_STORE_DIR + KeyStoreUtils.KEY_STORE);

    byte[] privateKey = KeyStoreUtils.getPrivateKey(keyPair);
    FileUtil.writeBytes(privateKey, FileUtils.KEY_STORE_DIR + KeyStoreUtils.PRIVATE_KEYS);

    return privateKey;
  }
}
