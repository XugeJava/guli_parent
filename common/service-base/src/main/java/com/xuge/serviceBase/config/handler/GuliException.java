package com.xuge.serviceBase.config.handler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author: yjx
 * Date :2022/4/1020:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException{
  @ApiModelProperty(value = "状态码")
  private Integer code;
  private String msg;
}
