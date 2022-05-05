package com.xuge.staservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuge.commonutils.R;
import com.xuge.staservice.Client.UcenterClient;
import com.xuge.staservice.bean.StatisticsDaily;
import com.xuge.staservice.mapper.StatisticsDailyMapper;
import com.xuge.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author xuge
 * @since 2022-05-01
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
  @Autowired
  //注入查询某一天注册人数接口
  private UcenterClient ucenterClient;
  @Override
  public void createStatisticsByDay(String day) {
    //0.删除已存在的统计对象
    QueryWrapper<StatisticsDaily> dayQueryWrapper = new QueryWrapper<>();
    dayQueryWrapper.eq("date_calculated", day);
    baseMapper.delete(dayQueryWrapper);

    R registCount = ucenterClient.getRegistCount(day);
    //1.获取某一天注册人数
    Integer count = (Integer )registCount.getData().get("countRegister");
    //2.把获取的数据添加到数据库
    StatisticsDaily sta = new StatisticsDaily();
    //设置注册人数
    sta.setRegisterNum(count);
    //设置时间
    sta.setDateCalculated(day);

    //添加一些随机模拟数据
    sta.setLoginNum(RandomUtils.nextInt(100, 200));
    sta.setCourseNum(RandomUtils.nextInt(100, 200));
    sta.setVideoViewNum(RandomUtils.nextInt(100, 200));

    //4.执行添加数据
    baseMapper.insert(sta);

  }
  //实现数据返回
  @Override
  public Map<String, Object> getShowData(String type, String start, String end) {
    QueryWrapper<StatisticsDaily> qw=new QueryWrapper<>();
    qw.between("date_calculated", start, end);
    qw.select("date_calculated",type);
    List<StatisticsDaily> list = baseMapper.selectList(qw);

   //因为返回有俩部分数据，日期和日期对应类型数量
    //返回json数组==对饮java中list集合
    List<String> dateList=new ArrayList<>();
    List<Integer> numDataList= new ArrayList<>();
    for (StatisticsDaily statisticsDaily : list) {
      dateList.add(statisticsDaily.getDateCalculated());
      //封装对应数量
      switch(type){
        case"login_num":
          numDataList.add(statisticsDaily.getLoginNum());
          break;

        case"register_num":
          numDataList.add(statisticsDaily.getRegisterNum());
          break;
        case"video_view_num":
          numDataList.add(statisticsDaily.getVideoViewNum());
          break;
        case"course_num":
          numDataList.add(statisticsDaily.getCourseNum());
          break;
      }



    }

    Map<String,Object> map=new HashMap<>();
    map.put("date_calculated",dateList);
    map.put("numDataList",numDataList);

    return map;

  }
}
