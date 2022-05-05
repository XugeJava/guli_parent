package com.xuge.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * author: yjx
 * Date :2022/4/2410:43
 **/
@SpringBootApplication
@ComponentScan("com.xuge")
@EnableDiscoveryClient
@MapperScan("com.xuge.educms.mapper")
public class CmsApplication {
  public static void main(String[] args) {
    SpringApplication.run(CmsApplication.class,args);
  }
}
