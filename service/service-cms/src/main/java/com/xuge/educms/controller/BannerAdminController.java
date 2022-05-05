package com.xuge.educms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuge.commonutils.R;
import com.xuge.educms.bean.CrmBanner;
import com.xuge.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *后台系统接口
 * @author xuge
 * @since 2022-04-24
 */
@RestController
//@CrossOrigin
@RequestMapping("/educms/bannerAdmin")
public class BannerAdminController {
  @Autowired
  private CrmBannerService bannerService;
  //1.分页查询banner
  @GetMapping("pageBanner/{page}/{limit}")
  @ApiOperation("分页查询banner")
  public R pageBanner(@PathVariable("page")Integer page,@PathVariable("limit")Integer limit){
    //1.创建page对象
    Page<CrmBanner> pageBanner=new Page<>(page,limit);
    bannerService.page(pageBanner, null);


    return R.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
  }
  //2.添加banner
  @PostMapping("addBanner")
  @ApiOperation("添加banner")
  public R addBanner(@RequestBody CrmBanner banner){
    bannerService.save(banner);
    return R.ok();
  }
  @ApiOperation(value = "获取Banner")
  @GetMapping("get/{id}")
  public R get(@PathVariable String id) {
    CrmBanner banner = bannerService.getById(id);
    return R.ok().data("item", banner);
  }

  @ApiOperation(value = "修改Banner")
  @PutMapping("update")
  public R updateById(@RequestBody CrmBanner banner) {
    bannerService.updateById(banner);
    return R.ok();
  }

  @ApiOperation(value = "删除Banner")
  @DeleteMapping("remove/{id}")
  public R remove(@PathVariable String id) {
    bannerService.removeById(id);
    return R.ok();
  }



}

