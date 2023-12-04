package com.yhl.http;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class TestApplication {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    JsonObject jsonObject = new JsonObject(
//     "{\"image\": {\"indexFile\": \"E:\\\\mercator\\\\world.dat\", \"dataPath\": [\"E:\\\\mercator1\",\"E:\\\\mercator3\",\"E:\\\\mercator-oz\",\"F:\\\\china\",\"F:\\\\mercator\", \"F:\\\\mercator2\",\"F:\\\\mercator3\",\"G:\\\\mercator\\\\fepk\",\"G:\\\\mercator1\"]}, \"label\": {\"indexFile\": \"E:\\\\zh_label\\\\world-label12.dat\", \"dataPath\": [ \"E:\\\\zh_label\" ]}, \"dem\": {\"indexFile\": \"D:\\\\services\\\\data\\\\fepk3\\\\worldDem0-8.dat\", \"dataPath\": [ \"D:\\\\services\\\\data\\\\fepk3\" ]}, \"httpIP\": \"127.0.0.1\", \"httpPort\": 11111, \"contentType\": \"image/jpeg\", \"binaryFormat\": \".dat\", \"license\": {\"verify\": {\"storePwd\":\"123456abc\", \"publicPwd\": \"12345678910abc\", \"licensePath\": \"\", \"publicKeysStorePath\": \"\"}}}"
      "{\"image\": {\"indexFile\": \"E:\\\\mercator\\\\world.dat\", \"dataPath\": [\"E:\\\\mercator1\",\"E:\\\\mercator3\",\"E:\\\\mercator-oz\",\"F:\\\\china\",\"F:\\\\mercator\", \"F:\\\\mercator2\",\"F:\\\\mercator3\",\"G:\\\\mercator\\\\fepk\",\"G:\\\\mercator1\"]}, \"label\": {\"indexFile\": \"E:\\\\zh_label\\\\world-label12.dat\", \"dataPath\": [ \"E:\\\\zh_label\" ]}, \"dem\": {\"indexFile\": \"D:\\\\services\\\\data\\\\fepk3\\\\worldDem0-8.dat\", \"dataPath\": [ \"D:\\\\services\\\\data\\\\fepk3\" ]}, \"httpIP\": \"127.0.0.1\", \"httpPort\": 11111, \"contentType\": \"image/jpeg\", \"binaryFormat\": \".dat\", \"license\": {\"verify\": {\"storePwd\":\"123456abc\", \"publicPwd\": \"12345678910abc\", \"licensePath\": \"D:\\\\code\\\\fepkhttp\\\\conf\\\\license.lic\", \"publicKeysStorePath\": \"D:\\\\code\\\\fepkhttp\\\\conf\\\\publicCerts.keystore\"}}}"
    );
    DeploymentOptions deploymentOptions = new DeploymentOptions();
    deploymentOptions.setConfig(jsonObject);

    vertx.deployVerticle(MainVerticle.class,deploymentOptions);
  }
}
