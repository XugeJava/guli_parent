package com.xuge.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * author: yjx
 * Date :2022/4/2023:46
 **/
public interface VodService {
  String uploadAliyun(MultipartFile file);

  void removeVideoById(String id);

  void removeVideoList(List videoIdList);
}
