package com.yhl.http;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class TestApplication {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    JsonObject jsonObject = new JsonObject(
     "{\"image\": {\"indexFile\": \"E:\\\\fekkData\\\\data\\\\fepk\\\\world.fepk\", \"dataPath\": [ \"E:\\\\fekkData\\\\data\\\\fepk\", \"E:\\\\fekkData\\\\data\\\\fepk1\", \"E:\\\\fekkData\\\\data\\\\fepk2\" ] }, \"label\": {\"indexFile\": \"E:\\\\fekkData\\\\data\\\\fepk_label\\\\world-label12.fepk\", \"dataPath\": [ \"E:\\\\fekkData\\\\data\\\\fepk_label\" ] }, \"dem\": {\"indexFile\": \"E:\\\\fekkData\\\\data\\\\fepk3\\\\worldDem0-8.fepk\", \"dataPath\": [ \"E:\\\\fekkData\\\\data\\\\fepk3\" ] }, \"httpIP\": \"127.0.0.1\", \"httpPort\": 11111, \"contentType\": \"image/jpeg\"}"
    );
    DeploymentOptions deploymentOptions = new DeploymentOptions();
    deploymentOptions.setConfig(jsonObject);

    vertx.deployVerticle(MainVerticle.class,deploymentOptions);
  }
}
