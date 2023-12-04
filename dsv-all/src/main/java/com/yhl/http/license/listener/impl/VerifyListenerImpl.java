package com.yhl.http.license.listener.impl;

import com.yhl.http.exceptions.LicenseException;
import com.yhl.http.handles.LicenseVerifyHandler;
import com.yhl.http.license.listener.VerifyListener;
import com.yhl.http.model.LicenseExtraParam;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerifyListenerImpl implements VerifyListener {
  private static final Logger logger = LoggerFactory.getLogger(LicenseVerifyHandler.class);
  public VerifyListenerImpl(Vertx vertx) {
      listeners.add(this);
  }

  @Override
  public boolean verify(LicenseExtraParam param) throws LicenseException {
    logger.info(Json.encode(param));
    return true;
  }
}
