package com.xuge.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * author: yjx
 * Date :2022/4/1016:34
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.xuge"})
@EnableDiscoveryClient//服务注册
@EnableFeignClients//服务端调用
@MapperScan("com.xuge.eduservice.mapper")
public class EduApplication {
  public static void main(String[] args) {
    SpringApplication.run(EduApplication.class,args);
  }
}
