package com.yhl.http;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.io.file.FileSystemUtil;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

public class Rename {

  private static String[] paths = new String[]{
    "E:\\mercator1","E:\\mercator3","E:\\mercator-oz","F:\\china","F:\\mercator",
    "F:\\mercator2","F:\\mercator3","G:\\mercator\\fepk","G:\\mercator1"};
  private static String[] path1 = new String[]{
    "E:\\mercator1"};
  public static void main(String[] args) {

//    Arrays.stream(paths).forEach( t -> {
//      System.out.println(t);
//      for (File loopFile : FileUtil.loopFiles(t)) {
//        if (loopFile.getName().contains("FEPK")){
//          String newName = loopFile.getName().replace("FEPK", "dat");
//          FileUtil.rename(loopFile,newName,false,true);
//          System.out.println(loopFile.getName());
//        }else if (loopFile.getName().contains("fepk")){
//          String newName = loopFile.getName().replace("fepk", "dat");
//          FileUtil.rename(loopFile,newName,false,true);
//          System.out.println(loopFile.getName());
//        }
//      }
//    });
//
  }
}
