package com.xuge.educms.utils;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * author: yjx
 * Date :2022/4/2416:03
 **/
public class testRedis {
  @Test
  public void test1(){
    Jedis jedis = new Jedis("www.xuge.site",6380);
    String pong = jedis.ping();
    System.out.println("连接成功："+pong);
    jedis.close();

  }
}
