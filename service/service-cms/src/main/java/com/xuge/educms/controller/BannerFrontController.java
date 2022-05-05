package com.xuge.educms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuge.commonutils.R;
import com.xuge.educms.bean.CrmBanner;
import com.xuge.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * author: yjx
 * Date :2022/4/2410:53
 **/
@RestController
//@CrossOrigin
@RequestMapping("/educms/bannerFront")
public class BannerFrontController {
  @Autowired
  private CrmBannerService bannerService;

  @ApiOperation(value = "获取首页banner")
  @GetMapping("getAllBanner")

  public R index() {
    List<CrmBanner> list = bannerService.selectAllBanner();
    return R.ok().data("bannerList", list);
  }
}
