package com.xuge.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * author: yjx
 * Date :2022/4/2023:40
 **/
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.xuge"})
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class VodApplication {
  public static void main(String[] args) {
    SpringApplication.run(VodApplication.class,args);
  }
}
