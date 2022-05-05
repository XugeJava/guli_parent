package com.xuge.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * author: yjx
 * Date :2022/4/1714:50
 **/
public class TestExcel {
  //实现excel的写操作
  public static void main(String[] args) {
    //1.设置写入文件地址和写入文件名称
    String  fileName="D:\\01.xlsx";

    //2.调用ExcelCel里面的方法实现写操作
    EasyExcel.write(fileName,DemoData.class).sheet("学生列表")
            .doWrite(demoDataList());
  }
  //创建返回list集合
  public  static List<DemoData> demoDataList(){
    List<DemoData> list = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      DemoData data = new DemoData();
      data.setSno(i);
      data.setSname("张三"+i);
      list.add(data);
    }
    return list;
  }

}
