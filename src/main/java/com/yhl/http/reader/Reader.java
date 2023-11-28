package com.yhl.http.reader;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.yhl.http.reader.types.FileHeader;
import com.yhl.http.reader.types.Int2;
import com.yhl.http.reader.types.Int3;
import com.yhl.http.reader.types.LevInf;
import com.yhl.http.model.DataConfig;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Paths;

/**
 *  文件阅读器
 */
public class Reader {
  public static final String INDEX_EXT = ".idx";
  public static final String BINARY_EXE = ".fepk";
  public static final String TYPE_IMAGE = "image";
  public static final String TYPE_LABEL = "label";
  public static final String TYPE_DEM = "dem";
  private DataConfig dataConfig;

  public Reader(JsonObject dataConfig) {
    setDataConfig(dataConfig);
  }


  public DataConfig getDataConfig() {
    return dataConfig;
  }

  public void setDataConfig(JsonObject dataConfig) {
    this.dataConfig = dataConfig.mapTo(DataConfig.class);
  }

  public String calcBlockId(String path, Int3 key) {
    int num1 = key.getX() >> key.getZ() - 8;
    int num2 = key.getY() >> key.getZ() - 8;
    return Paths.get(path).resolve(StrUtil.format("{}-{}-{}" + ObjUtil.defaultIfNull(getDataConfig().getBinaryFormat(),BINARY_EXE), 8, num1, num2)).toString();
  }


  public byte[] getPackTileImageV1(Int3 key) {
    return getPackTileImageV1(key, TYPE_IMAGE);
  }

  public byte[] getPackTileLabelV1(Int3 key) {
    return getPackTileImageV1(key, TYPE_LABEL);
  }

  private byte[] getPackTileImageV1(Int3 key, String type) {
    String path1 = "";
    String path2 = "";
    DataConfig.DataType dataType;
    if (TYPE_DEM.equals(type)) {
      dataType = getDataConfig().getDem();
    } else if (TYPE_LABEL.equals(type)) {
      dataType = getDataConfig().getLabel();
    } else {
      dataType = getDataConfig().getImage();
    }
    if (
      ((TYPE_DEM.equals(type) || TYPE_IMAGE.equals(type)) && key.getZ() < 9)
        ||
        (TYPE_LABEL.equals(type) && key.getZ() < 13)
    ) {
      path1 = dataType.getIndexFile() + INDEX_EXT;
      path2 = dataType.getIndexFile();
    } else {
      boolean flag = false;
      for (String dataPath : dataType.getDataPath()) {
        String path3 = calcBlockId(dataPath, key);
        File file = new File(path3);
        if (file.exists()) {
          flag = true;
          path2 = path3;
          path1 = path2 + ".idx";
          break;
        }
      }
      if (!flag) {
        return null;
      }
    }
    try {
      byte[] numArray1 = new byte[512];
      RandomAccessFile fileStream1 = new RandomAccessFile(path1, "r");
      fileStream1.read(numArray1, 0, numArray1.length);

      FileHeader fileHeader = new FileHeader();
      fileHeader.fillFromBuffer(Buffer.buffer(numArray1));
      RandomAccessFile fileStream2 = new RandomAccessFile(path2, "r");
      for (byte index = 0; fileHeader.getLevOff()[index].getLevel() != 255; ++index) {
        int level = (int) fileHeader.getLevOff()[index].getLevel();
        if (key.getZ() == level) {
          LevInf levInf = new LevInf();
          byte[] numArray2 = new byte[64];
          fileStream1.seek(fileHeader.getLevOff()[index].getOffset() - 256L);
          fileStream1.read(numArray2, 0, 64);
          levInf.fillFromBuffer(Buffer.buffer(numArray2));

          Int2 start = levInf.getStart();
          Int2 int2_1 = new Int2(levInf.getEnd().getX() - levInf.getStart().getX() + 1, levInf.getEnd().getY() - levInf.getStart().getY() + 1);
          Int2 int2_2 = new Int2(key.getX() - start.getX(), key.getY() - start.getY());

          if (int2_2.getX() < 0 || int2_2.getY() < 0 || int2_2.getY() >= int2_1.getY() || int2_2.getX() >= int2_1.getX()) {
            fileStream1.close();
            fileStream2.close();
            return null;
          }
          long offset1 = fileHeader.getLevOff()[index].getOffset() + (((long) int2_2.getY() * int2_1.getX() + int2_2.getX()) * 10);
          fileStream1.seek(offset1);
          byte[] buffer1 = new byte[10];
          byte[] buffer2 = new byte[16];
          fileStream1.read(buffer1, 0, buffer1.length);
          long offset2 = Buffer.buffer(buffer1).getLongLE(0) >> 10;

          fileStream2.seek(offset2);
          fileStream2.read(buffer2, 0, buffer2.length);
          int dataSize = Buffer.buffer(buffer2).getIntLE(12);
          byte[] buffer3 = new byte[dataSize];
          fileStream2.read(buffer3, 0, buffer3.length);

          return buffer3;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public byte[] getPackTileDemV1(Int3 key) {
    byte[] packData = getPackTileImageV1(key, TYPE_DEM);

    if (ObjUtil.isNotNull(packData)) {// 高程数据
      // 移除 头部16字节
      byte[] dataPack = new byte[packData.length - 16];
      System.arraycopy(packData, 16, dataPack, 0, dataPack.length);
      Buffer newPack = Buffer.buffer();
      Buffer buffer = Buffer.buffer(dataPack);
      int width = 65, height = 65;
      for (int y = 0; y < width; ++y) {
        for (int x = 0; x < height; ++x) {
          int byteOffset = (y * width + x) * 2;
          short demHeight = buffer.getShort(byteOffset);
          newPack.appendShort(demHeight);
        }
      }
      // 垂直翻转图像
      for (int j = 0, k = 65; j < 65 / 2; j++) {
        for (int i = 0; i < 65; i++) {
          short temp = newPack.getShort(j * k + i);
          newPack.setShort(j * k + i, newPack.getShort((65 - j - 1) * k + i));
          newPack.setShort((65 - j - 1) * k + i, temp);
        }
      }
      return newPack.getBytes();
    }
    return null;
  }
}
