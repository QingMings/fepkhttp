package com.yhl.http.model;

/**
 * 配置文件 pojo
 */
public class DataConfig {

  private DataType image;
  private DataType dem;
  private DataType label;
  private String httpPort;
  private String httpIP;
  private String contentType;
  private String binaryFormat;


  public DataType getImage() {
    return image;
  }

  public void setImage(DataType image) {
    this.image = image;
  }

  public DataType getDem() {
    return dem;
  }

  public void setDem(DataType dem) {
    this.dem = dem;
  }

  public DataType getLabel() {
    return label;
  }

  public void setLabel(DataType label) {
    this.label = label;
  }

  public String getHttpPort() {
    return httpPort;
  }

  public void setHttpPort(String httpPort) {
    this.httpPort = httpPort;
  }

  public String getHttpIP() {
    return httpIP;
  }

  public void setHttpIP(String httpIP) {
    this.httpIP = httpIP;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getBinaryFormat() {
    return binaryFormat;
  }

  public void setBinaryFormat(String binaryFormat) {
    this.binaryFormat = binaryFormat;
  }

  public class DataType {
    private String indexFile;
    private String[] dataPath;

    public String getIndexFile() {
      return indexFile;
    }

    public void setIndexFile(String indexFile) {
      this.indexFile = indexFile;
    }

    public String[] getDataPath() {
      return dataPath;
    }

    public void setDataPath(String[] dataPath) {
      this.dataPath = dataPath;
    }
  }
}
