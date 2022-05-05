package com.xuge.eduservice.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * author: yjx
 * Date :2022/4/1715:15
 **/
@Data
public class SubjectData {

  @ExcelProperty(value="一级分类",index = 0)
  private String oneSubjectName;

  @ExcelProperty(value="二级分类",index = 1)
  private String twoSubjectName;


}
