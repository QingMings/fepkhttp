package com.yhl.http.reader.types;

public class Real2 {
    public Real2() {
    }

    public Real2(double x, double y) {
      this.x = x;
      this.y = y;
    }

    private double x = 0d;
    private double y = 0d;

    public double getX() {
      return x;
    }

    public void setX(double x) {
      this.x = x;
    }

    public double getY() {
      return y;
    }

    public void setY(double y) {
      this.y = y;
    }
  }
