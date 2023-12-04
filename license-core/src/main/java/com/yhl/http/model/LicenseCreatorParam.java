package com.yhl.http.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.yhl.http.util.KeyStoreUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * License创建（生成）需要的参数
 *
 * @author Rong.Jia
 * @date 2022/03/10
 */

public class LicenseCreatorParam implements Serializable {

    private static final long serialVersionUID = -7793154252684580872L;

    /**
     * 证书主题
     */
    private String subject = "软件许可证书";

    /**
     * 私钥密码（需要妥善保管，不能让使用者知道）
     */
    private String keyPwd;

    /**
     * 访问密钥库的密码
     */
    private String storePwd;

    /**
     * 私钥别名
     */
    private String privateAlias = KeyStoreUtils.PRIVATE_KEYS_ALIAS;

    /**
     * 私钥库存储路径
     */
    private String privateKeysStorePath = "/privateKeys.keystore";

    /**
     * 证书失效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expiryTime;

    /**
     * 用户数量
     */
    private Integer consumerAmount = 1;

    /**
     * 描述信息
     */
    private String description = "系统软件许可证书";

    /**
     * 申请码
     */
    private String appCode;

    /**
     * 验证IP地址
     */
    private Boolean checkIpAddress = Boolean.TRUE;

    /**
     * 验证mac地址
     */
    private Boolean checkMacAddress = Boolean.TRUE;


  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getKeyPwd() {
    return keyPwd;
  }

  public void setKeyPwd(String keyPwd) {
    this.keyPwd = keyPwd;
  }

  public String getStorePwd() {
    return storePwd;
  }

  public void setStorePwd(String storePwd) {
    this.storePwd = storePwd;
  }

  public String getPrivateAlias() {
    return privateAlias;
  }

  public void setPrivateAlias(String privateAlias) {
    this.privateAlias = privateAlias;
  }

  public String getPrivateKeysStorePath() {
    return privateKeysStorePath;
  }

  public void setPrivateKeysStorePath(String privateKeysStorePath) {
    this.privateKeysStorePath = privateKeysStorePath;
  }

  public Date getExpiryTime() {
    return expiryTime;
  }

  public void setExpiryTime(Date expiryTime) {
    this.expiryTime = expiryTime;
  }

  public Integer getConsumerAmount() {
    return consumerAmount;
  }

  public void setConsumerAmount(Integer consumerAmount) {
    this.consumerAmount = consumerAmount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAppCode() {
    return appCode;
  }

  public void setAppCode(String appCode) {
    this.appCode = appCode;
  }

  public Boolean getCheckIpAddress() {
    return checkIpAddress;
  }

  public void setCheckIpAddress(Boolean checkIpAddress) {
    this.checkIpAddress = checkIpAddress;
  }

  public Boolean getCheckMacAddress() {
    return checkMacAddress;
  }

  public void setCheckMacAddress(Boolean checkMacAddress) {
    this.checkMacAddress = checkMacAddress;
  }
}
