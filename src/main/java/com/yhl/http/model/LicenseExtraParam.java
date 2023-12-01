package com.yhl.http.model;


import java.io.Serializable;
import java.util.List;

/**
 * 自定义需要校验的License参数
 *
 * @author Rong.Jia
 * @date 2022/03/10
 */

public class LicenseExtraParam implements Serializable {

    private static final long serialVersionUID = 8600137500316662317L;

  public LicenseExtraParam() {
  }

  /**
     * 可被允许的IP地址
     */
    private List<String> ipAddress;

    /**
     * 可被允许的mac地址
     */
    private List<String> macAddress;

    /**
     * 可被允许的CPU序列号
     */
    private String cpuSerial;

    /**
     * 可被允许的主板序列号
     */
    private String mainBoardSerial;

    /**
     * 验证IP地址
     */
    private Boolean checkIpAddress = Boolean.TRUE;

    /**
     * 验证mac地址
     */
    private Boolean checkMacAddress = Boolean.TRUE;


  public List<String> getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(List<String> ipAddress) {
    this.ipAddress = ipAddress;
  }

  public List<String> getMacAddress() {
    return macAddress;
  }

  public void setMacAddress(List<String> macAddress) {
    this.macAddress = macAddress;
  }

  public String getCpuSerial() {
    return cpuSerial;
  }

  public void setCpuSerial(String cpuSerial) {
    this.cpuSerial = cpuSerial;
  }

  public String getMainBoardSerial() {
    return mainBoardSerial;
  }

  public void setMainBoardSerial(String mainBoardSerial) {
    this.mainBoardSerial = mainBoardSerial;
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
