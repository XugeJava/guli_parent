package com.xuge.eduservice.controller;

import com.xuge.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * author: yjx
 * Date :2022/4/1515:16
 **/
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/user")
public class EduLoginController {
  //login
  @PostMapping("/login")
  public R login() {
    return R.ok().data("token","admin");
  }

  //info
  @GetMapping("/info")
  public R info() {

    return R.ok().data("roles","[admin]").data("name","adminXuge").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
  }
}
