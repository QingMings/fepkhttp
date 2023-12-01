package com.yhl.http.model;

import cn.hutool.core.lang.Assert;
import com.yhl.http.util.KeyStoreUtils;

import java.util.ArrayList;
import java.util.List;

public class LicenseVerifyProperties {

  /**
   * 主题
   */
  private String subject = "软件许可证书";

  /**
   * 公钥存储路径
   */
  private String publicKeysStorePath = "/publicCerts.keystore";

  /**
   * 公钥访问密码
   */
  private String publicPwd = "";

  /**
   * 秘钥库访问密码
   */
  private String storePwd = "";

  /**
   * 公钥别名
   */
  private String publicAlias = KeyStoreUtils.PUBLIC_CERT_ALIAS;

  /**
   * 许可证文件路径
   */
  private String licensePath = "classpath:license.lic";

  /**
   * 需要跳过验证授权的接口
   */
  private List<String> excludePathPatterns = new ArrayList<>();

  public LicenseVerifyParam getVerifyParam() {
    Assert.notBlank(storePwd, "秘钥库密码不能为空");
    Assert.notBlank(publicPwd, "公钥密码不能为空");

    LicenseVerifyParam param = new LicenseVerifyParam();
    param.setSubject(subject);
    param.setPublicAlias(publicAlias);
    param.setPublicPwd(publicPwd);
    param.setStorePwd(storePwd);
    param.setLicensePath(licensePath);
    param.setPublicKeysStorePath(publicKeysStorePath);
    return param;
  }


  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getPublicKeysStorePath() {
    return publicKeysStorePath;
  }

  public void setPublicKeysStorePath(String publicKeysStorePath) {
    this.publicKeysStorePath = publicKeysStorePath;
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

  public String getPublicAlias() {
    return publicAlias;
  }

  public void setPublicAlias(String publicAlias) {
    this.publicAlias = publicAlias;
  }

  public String getLicensePath() {
    return licensePath;
  }

  public void setLicensePath(String licensePath) {
    this.licensePath = licensePath;
  }

  public List<String> getExcludePathPatterns() {
    return excludePathPatterns;
  }

  public void setExcludePathPatterns(List<String> excludePathPatterns) {
    this.excludePathPatterns = excludePathPatterns;
  }
}
