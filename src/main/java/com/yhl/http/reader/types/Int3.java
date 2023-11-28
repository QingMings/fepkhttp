package com.yhl.http.reader.types;

public class Int3 {

    public Int3() {
    }

    public Int3(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }

    private int x = 0;
    private int y = 0;
    private int z = 0;

    public int getX() {
      return x;
    }

    public void setX(int x) {
      this.x = x;
    }

    public int getY() {
      return y;
    }

    public void setY(int y) {
      this.y = y;
    }

    public int getZ() {
      return z;
    }

    public void setZ(int z) {
      this.z = z;
    }
  }
