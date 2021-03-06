package com.xuge.eduservice.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * author: yjx
 * Date :2022/4/2016:18
 **/
@Data
@ApiModel(value = "Course查询对象", description = "课程查询对象封装")
public class CourseQueryVo implements Serializable {
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "课程名称")
  private String title;

  @ApiModelProperty(value = "讲师id")
  private String teacherId;

  @ApiModelProperty(value = "一级类别id")
  private String subjectParentId;

  @ApiModelProperty(value = "二级类别id")
  private String subjectId;
}
