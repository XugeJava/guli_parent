package com.xuge.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuge.eduservice.Client.VodClient;
import com.xuge.eduservice.bean.EduChapter;
import com.xuge.eduservice.bean.EduVideo;
import com.xuge.eduservice.bean.chapter.ChapterVo;
import com.xuge.eduservice.bean.chapter.VideoVo;
import com.xuge.eduservice.mapper.EduChapterMapper;
import com.xuge.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuge.eduservice.service.EduVideoService;
import com.xuge.serviceBase.config.handler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author xuge
 * @since 2022-04-17
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
 //注入eduVideoService
  @Autowired
 EduVideoService videoService;
  //注入vod
  @Autowired
  private VodClient vodClient;
  @Override
  public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
    //1.根据课程id查询课程里面所有的章节
    QueryWrapper<EduChapter> qw1=new QueryWrapper<>();
    qw1.eq("course_id", courseId);
    List<EduChapter> eduChapterList= baseMapper.selectList(qw1);



    //2.根据课程id查询课程里面所有的小结
    QueryWrapper<EduVideo> qw2=new QueryWrapper<>();
    qw2.eq("course_id", courseId);
    List<EduVideo> eduVideoList = videoService.list(qw2);
    //3.创建list集合，用于最终封装
    List<ChapterVo> chapterVos=new ArrayList<>();

    //3.遍历查询章节list集合进行封装
    for (EduChapter eduChapter : eduChapterList) {
      //1.创建一个chapterVo对象用于接收章节对象
      ChapterVo chapterVo = new ChapterVo();
      //2.通过工具类进行拷贝
      BeanUtils.copyProperties(eduChapter, chapterVo);



      //4.遍历查询小结list集合进行封装
      //创建一个集合用于videoVo封装
      List<VideoVo> videoVos=new ArrayList<>();
      for (EduVideo eduVideo : eduVideoList) {
        if(eduVideo.getChapterId().equals(eduChapter.getId())){//如果小结的chapter_id和chapter的id相等
          //1.构建video列表对象
          VideoVo videoVo = new VideoVo();
          BeanUtils.copyProperties(eduVideo,videoVo);
          //2.加入videoVos中
          videoVos.add(videoVo);
        }
      }
      //5.将每个chapter下的中小结结合加入到其中
      chapterVo.setChildren(videoVos);
      //6.添加集成了小结的每个章节对象
      chapterVos.add(chapterVo);


    }

    return chapterVos;
  }

  /**
   *
   * @param id 章节id
   * @return
   */
  @Override
  public boolean deleteChapter(String id) {

    //1.先删除视频
    QueryWrapper<EduVideo> qw=new QueryWrapper<>();
    //如果在小结表中存在章节id
    qw.eq("chapter_id",id);
    qw.select("video_source_id");
    List<EduVideo> list = videoService.list(qw);
//    List<String >StringIds=new ArrayList<>();
//    for (EduVideo eduVideo : list) {
//      if(!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
//        StringIds.add(eduVideo.getVideoSourceId());
//      }
//    }
//    if(StringIds.size()!=0){
//      //批量删除视频
//      vodClient.removeVideoList(StringIds);
//    }
//
//    //2.再删除小节
//    QueryWrapper<EduVideo> qw1=new QueryWrapper<>();
//    qw1.eq("chapter_id", id);
//    qw.select("id");
//    List<EduVideo> list1 = videoService.list(qw);
//    boolean b = videoService.removeByIds(list1);
//    if(!b){
//      throw new GuliException(20001,"删除小节失败!!!");
//    }
    //3.删除章节
    //根据章节id查询小结是否存在
    //删除章节



//    //查询章节id下的数据的条数
    int count = videoService.count(qw);
    if(count<=0){//查询不出小结,可以进行删除
      int res = baseMapper.deleteById(id);
      return res>0;
    }else{//不能删除
      throw new GuliException(20001,"不能进行删除");
    }



  }

  @Override
  public Boolean removeChapterById(String id) {
    QueryWrapper<EduChapter> qw=new QueryWrapper<>();
    qw.eq("course_id", id);
    int delete = baseMapper.delete(qw);
    return delete>=0;
  }
}
