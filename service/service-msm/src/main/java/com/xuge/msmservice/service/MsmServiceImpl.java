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
    SmsUtils.sendMessage("424f9991f7154b65aaa6ce87f0a11113",phone,(String)map.get("code"),"5","2e65b1bb3d054466b82f0c9d125465e2","908e94ccf08b4476ba6c876d13f084ad");


    return true;
  }
}
