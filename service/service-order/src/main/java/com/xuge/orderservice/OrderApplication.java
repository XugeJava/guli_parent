package com.xuge.orderservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * author: yjx
 * Date :2022/4/3019:35
 **/
@SpringBootApplication
//开启服务发现
@EnableDiscoveryClient
@ComponentScan("com.xuge")
@MapperScan("com.xuge.orderservice.mapper")
@EnableFeignClients//开启服务调用
public class OrderApplication {
  public static void main(String[] args) {
    SpringApplication.run(OrderApplication.class,args);
  }
}
