package com.xuge.serviceBase.config.handler;

import com.xuge.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author: yjx
 * Date :2022/4/1020:34
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  //指定出现什么异常会执行这个方法
  @ExceptionHandler(Exception.class)
  //因为他不在Controller中。没有@RestController，所以数据不会返回，需要加@ResponeseBody返回数据
  @ResponseBody
  public R error(Exception e){
    e.printStackTrace();
    return R.error().message("执行了全局异常处理。。。");
  }

  @ExceptionHandler(ArithmeticException.class)
  @ResponseBody
  public R error(ArithmeticException e){
    log.error(e.getMessage());
    e.printStackTrace();
    return R.error().message("执行了ArithmeticException异常");
  }

  @ExceptionHandler(GuliException.class)
  @ResponseBody
  public R error(GuliException e){
    log.error(e.getMessage());
    e.printStackTrace();
    return R.error().message(e.getMsg()).code(e.getCode());
  }



}
