package com.xuge.vod.service.Impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.sun.org.apache.xml.internal.security.Init;
import com.xuge.serviceBase.config.handler.GuliException;
import com.xuge.vod.service.VodService;
import com.xuge.vod.utils.ConstantPropertiesUtil;
import com.xuge.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.record.ArrayRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * author: yjx
 * Date :2022/4/2023:46
 **/
@Service
public class VodServiceImpl implements VodService {
  //实现上传
  @Override
  public String uploadAliyun(MultipartFile file) {
    //title上传之后文件名称
    //fileName 要上传的原文件名称
    //inputStream 文件上传输入流
    String fileName=file.getOriginalFilename();
    String title = fileName.substring(0, fileName.lastIndexOf("."));

    InputStream is=null;
    try {
       is=file.getInputStream();
    } catch (IOException e) {
      e.printStackTrace();
    }
    UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET, title, fileName, is);
    UploadVideoImpl uploader = new UploadVideoImpl();
    UploadStreamResponse response = uploader.uploadStream(request);
    System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
    return response.getVideoId();
  }

  @Override
  public void  removeVideoById(String id) {
     try{
       //1.初始化对象
       DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
       //2.创建request,response对象
       DeleteVideoRequest request = new DeleteVideoRequest();
       //3.设置id
       request.setVideoIds(id);
       //4.调用初始化对象删除
       client.getAcsResponse(request);

     }catch(Exception e){
       e.printStackTrace();
        throw new GuliException(20001,"删除失败");
    }
  }
  //删除多个视频
  @Override
  public void removeVideoList(List videoIdList) {
    try{
      //1.初始化对象
      DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
      //2.创建request,response对象
      DeleteVideoRequest request = new DeleteVideoRequest();
      //3.设置id 将list转为 1,2,3
      String join = StringUtils.join(videoIdList.toArray(), ",");
      request.setVideoIds(join);
      //4.调用初始化对象删除
      client.getAcsResponse(request);

    }catch(Exception e){
      e.printStackTrace();
      throw new GuliException(20001,"删除失败");
    }
  }



}
