package com.yhl.http.license.listener.impl;

import com.yhl.http.exceptions.LicenseException;
import com.yhl.http.license.listener.VerifyListener;
import com.yhl.http.model.LicenseExtraParam;
import io.vertx.core.Vertx;

public class VerifyListenerImpl implements VerifyListener {
  public VerifyListenerImpl(Vertx vertx) {
      listeners.add(this);
  }

  @Override
  public boolean verify(LicenseExtraParam param) throws LicenseException {
    return false;
  }
}
