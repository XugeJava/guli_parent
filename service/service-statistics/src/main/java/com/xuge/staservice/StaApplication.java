package com.xuge.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * author: yjx
 * Date :2022/5/116:36
 **/
@SpringBootApplication
@MapperScan("com.xuge.staservice.mapper")
@ComponentScan("com.xuge")
//开启服务发现
@EnableDiscoveryClient
//开启远程调用
@EnableFeignClients
public class StaApplication {
  public static void main(String[] args) {
    SpringApplication.run(StaApplication.class, args);
  }
}