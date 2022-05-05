package com.xuge.eduservice.Client;

import com.xuge.commonutils.R;
import com.xuge.eduservice.Client.Impl.VodFileDegradeFeignClient;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * author: yjx
 * Date :2022/4/2319:33
 **/
@Component
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
  //定义调用方法路径
  @ApiOperation("根据视屏id删除video")
  @DeleteMapping("/eduvod/video/deleteVideo/{id}")
   R deleteVideo(@PathVariable("id")String id);
  //批处理删除
  @DeleteMapping("/eduvod/video/deleteBatch")
   R removeVideoList(
          @ApiParam(name = "videoIdList", value = "云端视频id", required = true)
          @RequestParam("videoIdList") List<String> videoIdList);
}
