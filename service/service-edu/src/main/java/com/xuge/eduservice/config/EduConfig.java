package com.xuge.eduservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author: yjx
 * Date :2022/4/1016:35
 **/
@Configuration
@MapperScan("com.xuge.eduservice.mapper")
public class EduConfig {
  //逻辑删除插件
  @Bean
  public ISqlInjector sqlInjector() {
    return new LogicSqlInjector();
  }
  //分页插件
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    return new PaginationInterceptor();
  }

}
