package com.xuge.msmservice.service;


import com.xuge.msmservice.utils.SmsUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * author: yjx
 * Date :2022/4/2420:50
 **/
@Service
public class MsmServiceImpl implements MsmService {
  @Override
  public Boolean send(Map<String, Object> map, String phone) {
      //在这里调用你的sendMessage工具类，写入你的参数
    
//    

    return true;
  }
}
