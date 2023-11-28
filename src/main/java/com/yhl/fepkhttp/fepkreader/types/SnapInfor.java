package com.yhl.fepkhttp.fepkreader.types;

public class SnapInfor {

    public SnapInfor() {
    }

    public SnapInfor(long level, long offset) {
      this.level = level;
      this.offset = offset;
    }

    private long level;
    private long offset;

    public long getLevel() {
      return level;
    }

    public void setLevel(long level) {
      this.level = level;
    }

    public long getOffset() {
      return offset;
    }

    public void setOffset(long offset) {
      this.offset = offset;
    }
  }
