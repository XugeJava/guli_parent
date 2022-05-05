package com.xuge.educenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * author: yjx
 * Date :2022/4/2521:39
 **/
@Component
//@PropertySource("classpath:application.properties")
public class ConstantPropertiesUtil implements InitializingBean {

//  @Value("${wx.open.app.id}")
  private String appId="wxed9954c01bb89b47";

//  @Value("${wx.open.app.secret}")
  private String appSecret="a7482517235173ddb4083788de60b90e";

//  @Value("${wx.open.redirect.url}")
  private String redirectUrl="http://localhost:8160/api/ucenter/wx/callback";

  public static String WX_OPEN_APP_ID;
  public static String WX_OPEN_APP_SECRET;
  public static String WX_OPEN_REDIRECT_URL;

  @Override
  public void afterPropertiesSet() throws Exception {
    WX_OPEN_APP_ID = appId;
    WX_OPEN_APP_SECRET = appSecret;
    WX_OPEN_REDIRECT_URL = redirectUrl;
  }
}