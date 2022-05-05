package com.xuge.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * author: yjx
 * Date :2022/4/2514:27
 **/
@ComponentScan("com.xuge")
@SpringBootApplication//取消数据源自动配置
@MapperScan("com.xuge.educenter.mapper")
@EnableDiscoveryClient
public class UcenterApplication {
  public static void main(String[] args) {
    SpringApplication.run(UcenterApplication.class, args);
  }
}
