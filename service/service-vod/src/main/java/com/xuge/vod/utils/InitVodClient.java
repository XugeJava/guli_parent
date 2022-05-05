package com.xuge.vod.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

/**
 * author: yjx
 * Date :2022/4/2022:44
 **/
public class InitVodClient {
  //填入AccessKey信息
  public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
    String regionId = "cn-shanghai";  // 点播服务接入地域
    DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
    return  new DefaultAcsClient(profile);
  }
}
