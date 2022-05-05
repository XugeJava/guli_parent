package com.xuge.msmservice.controller;

import com.xuge.commonutils.R;
import com.xuge.msmservice.service.MsmService;
import com.xuge.msmservice.utils.RandomUtil;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * author: yjx
 * Date :2022/4/2420:48
 **/
@RestController
@RequestMapping("edumsm/msm")
//@CrossOrigin
public class MsmController {
  @Autowired
  private MsmService msmService;
  @Autowired
  private RedisTemplate<String,String> redisTemplate;
  @GetMapping("send/{phone}")
  public R sendMsm(@PathVariable String phone){
    //从redis中获取验证码，如果获取到直接返回
    String code=redisTemplate.opsForValue().get(phone);
    if(!StringUtils.isEmpty(code)){
      return R.ok();
    }
    //1.生成随机值，发送给阿里云
     code = RandomUtil.getFourBitRandom();
    //2.创建map
    Map<String,Object> map=new HashedMap<>();
    map.put("code",code);

    //3.调用service发送短信的方法
    Boolean b=msmService.send(map,phone);
    if(b){
      //设置验证码到redis中去
      //设置有效时间
      redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);

      return R.ok();
    }else{
      return R.error().message("发送验证码失败!");
    }





  }

}
