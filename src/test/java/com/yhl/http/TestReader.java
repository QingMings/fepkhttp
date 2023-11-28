package com.yhl.http;

import cn.hutool.core.util.ObjUtil;
import com.yhl.http.reader.Reader;
import com.yhl.http.reader.types.Int3;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class TestReader {

  public static void main(String[] args) throws FileNotFoundException {
    Vertx vertx = Vertx.vertx();
    JsonObject jsonObject = new JsonObject("{\"indexFile\": \"D:\\\\FE\\\\data\\\\fepk\\\\world.fepk\", \"dataPath\": [ \"D:\\\\FE\\\\data\\\\fepk\", \"D:\\\\FE\\\\data\\\\fepk1\", \"D:\\\\FE\\\\data\\\\fepk2\" ], \"httpIP\": \"127.0.0.1\", \"httpPort\": 11111, \"contentType\": \"image/jpeg\"}");
    Reader reader = new Reader(jsonObject);
    byte[] imageData = reader.getPackTileImageV1(new Int3(0,1,1));

    FileOutputStream fileOutputStream = new FileOutputStream(new File("E:/yhl_work/fepkhttp/target/classes/a.jpeg"));
    try {
      if (ObjUtil.isNotNull(imageData)){
        writeImageFromByteArray(imageData,"jpg",fileOutputStream);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
  public static void writeImageFromByteArray(byte[] imageData, String outputFormat, OutputStream outputStream) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
    BufferedImage outputImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = outputImage.createGraphics();
    g2d.drawImage(bufferedImage, 0, 0, null);
    g2d.dispose();
    ImageIO.write(outputImage, outputFormat, outputStream);
  }
}
