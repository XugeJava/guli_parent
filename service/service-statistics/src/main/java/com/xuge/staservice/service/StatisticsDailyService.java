package com.xuge.staservice.service;

import com.xuge.staservice.bean.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author xuge
 * @since 2022-05-01
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

  void createStatisticsByDay(String day);

  Map<String,Object> getShowData(String type, String start, String end);
}
