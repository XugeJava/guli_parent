package com.xuge.eduservice.Client.Impl;

import com.xuge.commonutils.R;
import com.xuge.eduservice.Client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * author: yjx
 * Date :2022/4/2321:38
 **/
@Component
public class VodFileDegradeFeignClient  implements VodClient {
  @Override
  public R deleteVideo(String id) {
    return R.error().message("删除video error ");
  }

  @Override
  public R removeVideoList(List<String> videoIdList) {
    return R.error().message("删除多个video error ");
  }
}
