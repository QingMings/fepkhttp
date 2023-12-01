package com.yhl.http.model;



import java.io.Serializable;
import java.util.Arrays;

/**
 * * License创建V2（生成）需要的参数
 *
 * @author Rong.Jia
 * @date 2023/07/23
 */

public class LicenseCreatorV2Param extends LicenseCreatorParam implements Serializable {

    private static final long serialVersionUID = -7793154252684580872L;

    /**
     * 私钥库 字节文件
     */
    private byte[] privateKeysStore;

  public byte[] getPrivateKeysStore() {
    return privateKeysStore;
  }

  public void setPrivateKeysStore(byte[] privateKeysStore) {
    this.privateKeysStore = privateKeysStore;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LicenseCreatorV2Param that = (LicenseCreatorV2Param) o;
    return Arrays.equals(privateKeysStore, that.privateKeysStore);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(privateKeysStore);
  }
}
