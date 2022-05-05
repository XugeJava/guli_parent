package com.xuge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * author: yjx
 * Date :2022/4/1615:51
 **/
//默认不去加载数据库配置
  @EnableDiscoveryClient
@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
public class OosApplication {
  public static void main(String[] args) {
     SpringApplication.run(OosApplication.class,args);
  }
}
