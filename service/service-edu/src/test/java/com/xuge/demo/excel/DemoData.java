package com.xuge.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * author: yjx
 * Date :2022/4/1623:38
 **/
//设置表头和添加的数据字段
@Data
public class DemoData {
  //设置表头名称
  @ExcelProperty(value="学生编号",index=0)
  private int sno;

  //设置表头名称
  @ExcelProperty(value="学生姓名",index=1)
  private String sname;



  @Override
  public String toString() {
    return "DemoData{" +
            "sno=" + sno +
            ", sname='" + sname + '\'' +
            '}';
  }
}