package com.xuge.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * author: yjx
 * Date :2022/4/1616:19
 **/
public interface OOSService {
  /**
   * 文件上传至阿里云
   * @param file
   * @return
   */
  String uploadFileAvatar(MultipartFile file);
}
