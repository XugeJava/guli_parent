package com.xuge.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * author: yjx
 * Date :2022/4/2420:47
 **/
@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
@ComponentScan("com.xuge")
public class MsmApplication {
  public static void main(String[] args) {
    SpringApplication.run(MsmApplication.class,args);
  }
}
