package com.yhl.http.model;

import de.schlichtherle.license.LicenseContent;


import java.io.Serializable;

/**
 * 许可内容签证官
 *
 * @author Rong.Jia
 * @date 2023/07/23
 */

public class LicenseContentVO implements Serializable {
  public LicenseContentVO() {
  }

  /**
     * 许可证信息
     */
    private LicenseContent content;

    /**
     * 许可证文件
     */
    private byte[] lic;

  public LicenseContent getContent() {
    return content;
  }

  public void setContent(LicenseContent content) {
    this.content = content;
  }

  public byte[] getLic() {
    return lic;
  }

  public void setLic(byte[] lic) {
    this.lic = lic;
  }
}
