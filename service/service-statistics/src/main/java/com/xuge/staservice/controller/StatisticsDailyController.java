package com.xuge.staservice.controller;


import com.xuge.commonutils.R;
import com.xuge.staservice.Client.UcenterClient;
import com.xuge.staservice.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author xuge
 * @since 2022-05-01
 */
@RestController
//@CrossOrigin
@Api(description = "统计管理")
@RequestMapping("/staservice/stadaily")
//开启定时任务注解
@EnableScheduling
public class StatisticsDailyController {
  @Autowired
  private StatisticsDailyService dailyService;

  @PostMapping("createStatis/{day}")
  public R createStatisticsByDate(@PathVariable String day) {
    dailyService.createStatisticsByDay(day);
    return R.ok();
  }

  //图表显示，返回两部分数据 日期，数量json数组
  @GetMapping("showData/{type}/{start}/{end}")
  @ApiOperation("图表显示，返回两部分数据 日期，数量json数组")
  public R showData(@PathVariable String type, @PathVariable String start, @PathVariable String end) {
    Map<String,Object> map=dailyService.getShowData(type,start,end);


    return R.ok().data(map);



  }


}

