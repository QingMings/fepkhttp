package com.yhl.http.model;




import java.io.Serializable;

/**
 * License校验类需要的参数
 *
 * @author Rong.Jia
 * @date 2022/03/10
 */

public class LicenseVerifyParam implements Serializable {

    private static final long serialVersionUID = 8969466786494577755L;

  public LicenseVerifyParam() {
  }

  /**
     * 证书主题
     */
    private String subject;

    /**
     * 公钥别名
     */
    private String publicAlias;

    /**
     * 公钥访问密码
     */
    private String publicPwd = "";

    /**
     * 秘钥库访问密码
     */
    private String storePwd = "";

    /**
     * 证书生成路径
     */
    private String licensePath;

    /**
     * 公钥库存储路径
     */
    private String publicKeysStorePath;

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getPublicAlias() {
    return publicAlias;
  }

  public void setPublicAlias(String publicAlias) {
    this.publicAlias = publicAlias;
  }

  public String getPublicPwd() {
    return publicPwd;
  }

  public void setPublicPwd(String publicPwd) {
    this.publicPwd = publicPwd;
  }

  public String getStorePwd() {
    return storePwd;
  }

  public void setStorePwd(String storePwd) {
    this.storePwd = storePwd;
  }

  public String getLicensePath() {
    return licensePath;
  }

  public void setLicensePath(String licensePath) {
    this.licensePath = licensePath;
  }

  public String getPublicKeysStorePath() {
    return publicKeysStorePath;
  }

  public void setPublicKeysStorePath(String publicKeysStorePath) {
    this.publicKeysStorePath = publicKeysStorePath;
  }
}
