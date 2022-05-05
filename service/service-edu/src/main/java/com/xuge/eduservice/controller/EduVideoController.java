package com.xuge.eduservice.controller;


import com.mysql.cj.util.StringUtils;
import com.xuge.commonutils.R;
import com.xuge.eduservice.Client.VodClient;
import com.xuge.eduservice.bean.EduVideo;
import com.xuge.eduservice.service.EduVideoService;
import com.xuge.serviceBase.config.handler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author xuge
 * @since 2022-04-17
 */
@Api(description = "小节管理")
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/video")
public class EduVideoController {
  //注入vodClient
  @Autowired
  private VodClient vodClient;
  @Autowired
  private EduVideoService videoService;

  //1.添加小节

  @ApiOperation("添加小节")
  @PostMapping("addVideo")
  public R addVideo(@RequestBody EduVideo video) {
    boolean save = videoService.save(video);
    if (!save) {
      throw new GuliException(20001, "添加小节失败");
    }
    return R.ok();
  }


  //2.删除小节
  //todo 删除小节的时候，后面的视屏也要删除
  @ApiOperation("删除小节")
  @DeleteMapping("deleteVideo/{id}")
  public R deleteVideo(@PathVariable("id") String id) {
    //1.根据小节id，获取视屏id
    EduVideo eduVideo = videoService.getById(id);
    String video = eduVideo.getVideoSourceId();
    //2.根绝视频id远程调用删除
    if(!StringUtils.isEmptyOrWhitespaceOnly(video)){
      //根据视屏id，删除视频
      R r = vodClient.deleteVideo(video);
      if(r.getCode()==20001){
        throw new GuliException(20001,"删除video失败!!");
      }
    }


    //3.删除小节
    boolean b = videoService.removeById(id);
    if (!b) {
      throw new GuliException(20001, "删除小节失败");
    }
    return R.ok();
  }


  //3.根据小节id获取小节
  @ApiOperation("根据id获取小节")
  @GetMapping("getVideo/{id}")
  public R getVideo(@PathVariable("id") String id) {
    EduVideo eduVideo = videoService.getById(id);
    if (eduVideo == null) {
      throw new GuliException(20001, "查询不到数据");
    }
    return R.ok().data("eduVideo", eduVideo);
  }


  //4.修改小节
  @ApiOperation("根据id修改小节")
  @PostMapping("updateVideo")
  public R updateVideo(@RequestBody EduVideo video) {
    boolean b = videoService.updateById(video);
    if (!b) {
      throw new GuliException(20001, "修改失败");
    }
    return R.ok();
  }
}

