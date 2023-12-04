import com.yhl.license.MainVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class TestLicenseGenApplication {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    JsonObject jsonObject = new JsonObject(
      "{\"httpIP\": \"127.0.0.1\", \"httpPort\": 11112}"
    );
    DeploymentOptions deploymentOptions = new DeploymentOptions();
    deploymentOptions.setConfig(jsonObject);

    vertx.deployVerticle(MainVerticle.class, deploymentOptions);
  }
}
