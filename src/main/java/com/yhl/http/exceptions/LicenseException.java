package com.yhl.http.exceptions;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;


/**
 * 许可证异常
 *
 * @author Rong.Jia
 * @date 2022/03/10
 */

public class LicenseException extends RuntimeException {

    public LicenseException(Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    public LicenseException(String message) {
        super(message);
    }

    public LicenseException(String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params));
    }

    public LicenseException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public LicenseException(Throwable throwable, String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params), throwable);
    }


  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
}
