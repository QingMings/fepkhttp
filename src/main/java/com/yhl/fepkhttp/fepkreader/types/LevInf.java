package com.yhl.fepkhttp.fepkreader.types;

import io.vertx.core.buffer.Buffer;

public class LevInf {
    private Int2 start;
    private Int2 end;
    private long offset;
    private long dataSize;
    private int lev;
    private int user;

    public LevInf() {
      this.start = new Int2();
      this.end = new Int2();
    }

    public Int2 getStart() {
      return start;
    }

    public void setStart(Int2 start) {
      this.start = start;
    }

    public Int2 getEnd() {
      return end;
    }

    public void setEnd(Int2 end) {
      this.end = end;
    }

    public long getOffset() {
      return offset;
    }

    public void setOffset(long offset) {
      this.offset = offset;
    }

    public long getDataSize() {
      return dataSize;
    }

    public void setDataSize(long dataSize) {
      this.dataSize = dataSize;
    }

    public int getLev() {
      return lev;
    }

    public void setLev(int lev) {
      this.lev = lev;
    }

    public int getUser() {
      return user;
    }

    public void setUser(int user) {
      this.user = user;
    }

    public void fillFromBuffer(Buffer buffer) {
      this.getStart().setX(buffer.getIntLE(0));
      this.getStart().setY(buffer.getIntLE(4));
      this.getEnd().setX(buffer.getIntLE(8));
      this.getEnd().setY(buffer.getIntLE(12));
      this.setOffset(buffer.getLongLE(16));
      this.setDataSize(buffer.getLongLE(24));
      this.setLev(buffer.getIntLE(28));  // ?这里index错了吧
      this.setUser(buffer.getIntLE(32));
    }
  }
