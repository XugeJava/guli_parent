package com.xuge.canal;

import com.xuge.canal.canalClient.CanalClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * author: yjx
 * Date :2022/5/314:49
 **/
@SpringBootApplication
public class CanalApplication implements CommandLineRunner {
  @Resource
  private CanalClient canalClient;

  public static void main(String[] args) {
    SpringApplication.run(CanalApplication.class, args);
  }

  @Override
  public void run(String... strings) throws Exception {
    //项目启动，执行canal客户端监听
    canalClient.run();
  }
}