package com.yhl.http.handles;

import cn.hutool.core.util.ObjUtil;
import com.yhl.http.reader.Reader;
import com.yhl.http.reader.types.Int3;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;


public class ReadHandler implements Handler<RoutingContext> {

  public static final String READ_IMAGE = "readImage";
  public static final String READ_DEM = "readDem";
  public static final String READ_LABEL = "readLabel";

  public ReadHandler(Vertx vertx) {
    this.reader = new Reader(vertx.getOrCreateContext().config());
  }

  private final Reader reader;

  @Override
  public void handle(RoutingContext routingContext) {
    String apiType = routingContext.pathParam("apiType");
    if (!READ_IMAGE.equals(apiType) && !READ_DEM.equals(apiType) && !READ_LABEL.equals(apiType)) {
      routingContext.response().setStatusCode(500).end();
    }
    String col = routingContext.request().getParam("col");
    String row = routingContext.request().getParam("row");
    String lev = routingContext.request().getParam("lev");
    if (col != null && row != null && lev != null) {
      HttpServerResponse response = routingContext.response();
      try {
        if (READ_IMAGE.equals(apiType)) {
          byte[] packTileImageV1 = reader.getPackTileImageV1(new Int3(Integer.parseInt(col), Integer.parseInt(row), Integer.parseInt(lev)));
          bufferHandle(response, packTileImageV1,"image/jpeg");
        } else if (READ_LABEL.equals(apiType)) {
          byte[] packTileLabelV1 = reader.getPackTileLabelV1(new Int3(Integer.parseInt(col), Integer.parseInt(row), Integer.parseInt(lev)));
          bufferHandle(response, packTileLabelV1,"image/jpeg");
        } else if (READ_DEM.equals(apiType)) {
          byte[] packTileDemV1 = reader.getPackTileDemV1(new Int3(Integer.parseInt(col), Integer.parseInt(row), Integer.parseInt(lev)));
          bufferHandle(response, packTileDemV1,"application/octet-stream");
        }
      } catch (Exception e) {
        response.setStatusCode(500).end();
      }
    } else {
      routingContext.response().setStatusCode(400).end("Missing required parameters");
    }
  }

  private void bufferHandle(HttpServerResponse response, byte[] packTileBytes,String contentType) {
    if (ObjUtil.isNotNull(packTileBytes)) {
      Buffer buffer = Buffer.buffer(packTileBytes);
      response.putHeader("Content-Type", contentType);
      response.putHeader("Content-Length", String.valueOf(buffer.length()));
      response.send(buffer);
    } else {
      response.setStatusCode(404).end();
    }
  }
}

