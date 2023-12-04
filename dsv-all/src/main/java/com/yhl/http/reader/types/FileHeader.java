package com.yhl.http.reader.types;

import io.vertx.core.buffer.Buffer;

/**
 * 文件头读取
 */
public class FileHeader {
    public FileHeader() {
      this.vStart = new Real2();
      this.vEnd = new Real2();
      this.levOff = new SnapInfor[24];
    }

    private int wgs84;
    private Real2 vStart;
    private Real2 vEnd;
    private SnapInfor[] levOff;

    public int getWgs84() {
      return wgs84;
    }

    public void setWgs84(int wgs84) {
      this.wgs84 = wgs84;
    }

    public Real2 getvStart() {
      return vStart;
    }

    public void setvStart(Real2 vStart) {
      this.vStart = vStart;
    }

    public Real2 getvEnd() {
      return vEnd;
    }

    public void setvEnd(Real2 vEnd) {
      this.vEnd = vEnd;
    }

    public SnapInfor[] getLevOff() {
      return levOff;
    }

    public void setLevOff(SnapInfor[] levOff) {
      this.levOff = levOff;
    }

    public void fillFromBuffer(Buffer buffer) {
      this.getvStart().setX(Double.longBitsToDouble(buffer.getLongLE(48)));
      this.getvStart().setY(Double.longBitsToDouble(buffer.getLongLE(56)));
      this.getvEnd().setX(Double.longBitsToDouble(buffer.getLongLE(64)));
      this.getvEnd().setY(Double.longBitsToDouble(buffer.getLongLE(72)));

      for (int i = 0; i < 24; ++i) {
        this.getLevOff()[i] = new SnapInfor();
        this.getLevOff()[i].setLevel(255);
        this.getLevOff()[i].setOffset(0L);
      }

      for (int i = 0; i < 24; ++i) {
        long int64 = buffer.getLongLE(80 + i * 8);
        this.getLevOff()[i].setLevel(int64 & 255);
        this.getLevOff()[i].setOffset(int64 >> 8 & Long.MAX_VALUE);
      }
    }
  }
