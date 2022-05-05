package com.xuge.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.xuge.commonutils.R;
import com.xuge.serviceBase.config.handler.GuliException;
import com.xuge.vod.service.VodService;
import com.xuge.vod.utils.ConstantPropertiesUtil;
import com.xuge.vod.utils.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * author: yjx
 * Date :2022/4/2023:44
 **/
@RestController
//@CrossOrigin
@Api(description = "上传视屏管理")
@RequestMapping("/eduvod/video")
public class VodController {
  //注入service组件
  @Autowired
  private VodService vodService;

  //上传视屏到阿里云的方法
  @ApiOperation("上传视屏到阿里云的方法")
  @PostMapping("uploadAly")
  public R uploadAly(MultipartFile file) {
    String vodId = vodService.uploadAliyun(file);
    return R.ok().data("vodId", vodId);
  }

  @ApiOperation("根据视屏id删除video")
  @DeleteMapping("deleteVideo/{id}")
  public R deleteVideo(@PathVariable("id") String id) {
    vodService.removeVideoById(id);
    return R.ok();
  }

  @DeleteMapping("deleteBatch")
  public R removeVideoList(
          @ApiParam(name = "videoIdList", value = "云端视频id", required = true)
          @RequestParam("videoIdList") List<String> videoIdList) {

    vodService.removeVideoList(videoIdList);
    return R.ok().message("视频删除成功");
  }

  @ApiOperation("根据视频id获取视屏凭证")
  @GetMapping("getPlayAuth/{VideoId}")
  public R getPlaAuth(@PathVariable("VideoId") String videoId) {
    try {
      //1.创建初始化对象
      DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
      //2.创建获取凭证的request和response对象
      GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
      //3.向request设置视屏Id
      request.setVideoId(videoId);
      //响应
      GetVideoPlayAuthResponse response = client.getAcsResponse(request);

      //4.调用方法获取凭证
      String playAuth = response.getPlayAuth();
      return R.ok().data("playAuth", playAuth);


    } catch (Exception e) {
      throw new GuliException(20001, "视屏凭证获取失败");
    }


  }


}
