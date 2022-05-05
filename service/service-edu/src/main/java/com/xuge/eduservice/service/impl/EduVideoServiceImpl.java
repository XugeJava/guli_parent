package com.xuge.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.util.StringUtils;
import com.xuge.eduservice.Client.VodClient;
import com.xuge.eduservice.bean.EduVideo;
import com.xuge.eduservice.mapper.EduVideoMapper;
import com.xuge.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author xuge
 * @since 2022-04-17
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
  //注入vodClient
  @Autowired
  private VodClient vodClient;
  //1.根据课程id删除小节==>并且删除对饮video
  @Override
  public Boolean removeVideoById(String id) {
    //1.根据课程id查询出其对应的视频id
    QueryWrapper<EduVideo> qw1=new QueryWrapper<>();
    qw1.eq("course_id",id);
    //指定要查询的列
    qw1.select("video_source_id");
    List<EduVideo> eduVideos = baseMapper.selectList(qw1);
    //转换泛型list<EduVideo>==>list<String>
    List<String >list=new ArrayList<>();
    for (EduVideo eduVideo : eduVideos) {
      if(!StringUtils.isNullOrEmpty(eduVideo.getVideoSourceId())){
        list.add(eduVideo.getVideoSourceId());
      }
    }
    //根据多个视频id删除视频
    //判断list是否为空
    if(list.size()!=0){
      vodClient.removeVideoList(list);
    }

    QueryWrapper<EduVideo> qw=new QueryWrapper<>();
    qw.eq("course_id",id);
    int delete = baseMapper.delete(qw);
    return delete>=0;
  }
}
