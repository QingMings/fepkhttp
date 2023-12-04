package com.yhl.http.license.listener;

import com.yhl.http.exceptions.LicenseException;
import com.yhl.http.license.listener.impl.VerifyListenerImpl;
import com.yhl.http.model.LicenseExtraParam;
import io.vertx.core.Vertx;

import java.util.ArrayList;
import java.util.List;

public interface VerifyListener {

  List<VerifyListener> listeners = new ArrayList<>();

  static VerifyListener create(Vertx vertx) {
    return new VerifyListenerImpl(vertx);
  }

  static List<VerifyListener> getListenerList() {
    return listeners;
  }

  boolean verify(LicenseExtraParam param) throws LicenseException;
}
