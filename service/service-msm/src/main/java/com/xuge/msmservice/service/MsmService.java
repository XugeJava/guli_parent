package com.xuge.msmservice.service;

import java.util.Map;

/**
 * author: yjx
 * Date :2022/4/2420:50
 **/
public interface MsmService {
  Boolean send(Map<String, Object> map, String phone);
}
