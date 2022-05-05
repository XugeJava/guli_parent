package com.xuge.controller;

import com.xuge.commonutils.R;
import com.xuge.service.OOSService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * author: yjx
 * Date :2022/4/1616:21
 **/
@RestController
@RequestMapping("/eduoss/fileoss")
//@CrossOrigin
public class OosController {
  @Autowired
  private OOSService ossService;
  //上传讲师的方法
  @PostMapping
  @ApiOperation(value="讲师头像上传")
  public R uploadsOssFile(MultipartFile file){
    //获取上传文件
    //返回上传到阿里云的路径
    String url=ossService.uploadFileAvatar(file);

    return R.ok().data("url",url);
  }
}
