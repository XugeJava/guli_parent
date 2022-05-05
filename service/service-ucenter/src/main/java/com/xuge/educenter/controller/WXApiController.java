package com.xuge.educenter.controller;

import com.google.gson.Gson;
import com.xuge.commonutils.JwtUtils;
import com.xuge.educenter.bean.UcenterMember;
import com.xuge.educenter.service.UcenterMemberService;
import com.xuge.educenter.utils.ConstantPropertiesUtil;
import com.xuge.educenter.utils.HttpClientUtils;
import com.xuge.serviceBase.config.handler.GuliException;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * author: yjx
 * Date :2022/4/2521:42
 **/
@Controller
//@CrossOrigin
@RequestMapping("/api/ucenter/wx")
@Slf4j
public class WXApiController {
  @Autowired
  private UcenterMemberService memberService;
  //1.生成微信二维码
  @GetMapping("/login")
  @ApiOperation("生成微信二维码")
  public String getWxCode(){
    //重定向到固定地址
    //%s相当于占位符
    String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
            "?appid=%s" +
            "&redirect_uri=%s" +
            "&response_type=code" +
            "&scope=snsapi_login" +
            "&state=%s" +
            "#wechat_redirect";

    //2.对url进行编码
    // 回调地址
    String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
    try {
      redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
    } catch (UnsupportedEncodingException e) {
      throw new GuliException(20001, e.getMessage());
    }

    //生成qrcodeUrl
    String qrcodeUrl = String.format(
            baseUrl,
            ConstantPropertiesUtil.WX_OPEN_APP_ID,
            redirectUrl,
            "xuge");
    return "redirect:"+qrcodeUrl;

  }


  //2.回调,获取用户信息，添加数据
  @GetMapping("callback")
  public String callback(String code,String state){
    log.info("code="+code);
    log.info("state="+state);
    //1.获取code值

     //从redis中将state获取出来，和当前传入的state作比较
     //如果一致则放行，如果不一致则抛出异常：非法访问

     //2.拿着code请求微信固定的地址，换取access_token和opendid
    String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
            "?appid=%s" +
            "&secret=%s" +
            "&code=%s" +
            "&grant_type=authorization_code";
   //拼接三个参数
    String accessTokenUrl = String.format(baseAccessTokenUrl,
            ConstantPropertiesUtil.WX_OPEN_APP_ID,
            ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
            code);
    //3.使用httpClient发送请求
    String result = null;
    try {
      result = HttpClientUtils.get(accessTokenUrl);
      System.out.println("accessToken=============" + result);
    } catch (Exception e) {
      throw new GuliException(20001, "获取access_token失败");
    }

    //4.accessTokenInfo获取access_token和openid
    //4.1使用Gson转换工具
    // 解析json
    Gson gson = new Gson();
    HashMap<String, Object> map = gson.fromJson(result, HashMap.class);
    //4.2获取access_token 和openid
    String accessToken = (String)map.get("access_token");
    String  openid =(String) map.get("openid");


    //4.3根据openid获取用户信息
    UcenterMember member= memberService.getByOpenId(openid);
    //4.4如果不存在数据
    if(member==null){
      log.info("===============新用户注册==============");
      //5.拿着access_token 和openid再去请求一个地址
      //访问微信的资源服务器，获取用户信息
      String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
              "?access_token=%s" +
              "&openid=%s";
      String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
      String userInfo = null;
      try {
        //发送请求
        userInfo = HttpClientUtils.get(userInfoUrl);
        log.info("userInfo"+userInfo);
      } catch (Exception exception) {
        exception.printStackTrace();
      }
      //6.获取返回userinfo扫描人信息

      HashMap<String, Object> userMap = gson.fromJson(userInfo, HashMap.class);
      //获取昵称和头像
      String nickname = (String)userMap.get("nickname");
      String headimgurl = (String)userMap.get("headimgurl");

      //7.设置数据
      member=new UcenterMember();
      member.setNickname(nickname);
      member.setOpenid(openid);
      member.setAvatar(headimgurl);
      memberService.save(member);

    }
    //8.使用jwt根据member生产token
    String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

    //todo 登录  ==首页面
    return "redirect:http://localhost:3000?token="+jwtToken;
  }



}
