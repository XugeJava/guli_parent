package com.xuge.demo.excel;

import com.alibaba.excel.EasyExcel;

/**
 * author: yjx
 * Date :2022/4/1715:05
 **/
public class TestRead {
  public static void main(String[] args) {
    // 写法1：
    String fileName = "D:\\01.xlsx";
    // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
    EasyExcel.read(fileName, DemoData.class, new ExcelListener()).sheet().doRead();
  }
}
