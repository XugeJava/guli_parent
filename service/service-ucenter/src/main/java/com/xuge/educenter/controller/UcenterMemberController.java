package com.xuge.educenter.controller;


import com.xuge.commonutils.JwtUtils;
import com.xuge.commonutils.R;

import com.xuge.commonutils.UcenterMemberVo;
import com.xuge.educenter.bean.UcenterMember;
import com.xuge.educenter.bean.Vo.RegisterVo;
import com.xuge.educenter.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author xuge
 * @since 2022-04-25
 */
@RestController
//@CrossOrigin
@Api(description = "用户登录与注册管理")
@RequestMapping("/educenter/member")
public class UcenterMemberController {
  @Autowired
  private UcenterMemberService memberService;

  //登录方法
  @ApiOperation("登录方法")
  @PostMapping("login")
  public R login(@RequestBody UcenterMember member) {
    //1.获取token
    String token = memberService.login(member);
    //返回token
    return R.ok().data("token", token);

  }

  //注册方法
  @ApiOperation("注册方法")
  @PostMapping("register")
  public R regist(@RequestBody RegisterVo registerVo) {
    //1.获取token
    Boolean regist = memberService.regist(registerVo);
    //返回token
    if (!regist) {
      return R.error().message("注册失败!");
    }
    return R.ok();

  }

  //根据token获取用户信息接口
  @GetMapping("getMemberInfo")
  @ApiOperation("根据token获取用户信息接口")
  public R getMemberInfo(HttpServletRequest request) {
    //1.调用工具类获取和用户id
    String id = JwtUtils.getMemberIdByJwtToken(request);
    //2.根据id查询用户信息
    UcenterMember member = memberService.getById(id);
    //3.返回
    return R.ok().data("userInfo",member);

  }

  //根据用户id获取用户信息接口
  @ApiOperation("根据用户id获取用户信息接口")
  @GetMapping("getUcenterMemberById/{id}")
  public UcenterMemberVo getUcenterMemberVById(@PathVariable String id){
    UcenterMember uc = memberService.getById(id);
    UcenterMemberVo ucenterMemberVo = new UcenterMemberVo();
    BeanUtils.copyProperties(uc, ucenterMemberVo);
    return ucenterMemberVo;
  }

  //查询某一天注册人数
  @ApiOperation("查询某一天注册人数")
  @GetMapping("getRegistCount/{data}")
  public R getRegistCount(@PathVariable String data){
   Integer count=memberService.countRegistVo(data);
    return R.ok().data("countRegister",count);

  }




}

