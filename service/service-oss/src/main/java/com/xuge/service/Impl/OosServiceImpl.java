package com.xuge.service.Impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.xuge.service.OOSService;
import com.xuge.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * author: yjx
 * Date :2022/4/1616:20
 **/
@Service
public class OosServiceImpl implements OOSService {

  @Override
  public String uploadFileAvatar(MultipartFile file) {
    //获取阿里云存储相关常量
    String endPoint = ConstantPropertiesUtil.END_POINT;
    String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
    String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
    String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
    String fileHost = ConstantPropertiesUtil.FILE_HOST;

    String uploadUrl=null ;

    try {
      //判断oss实例是否存在：如果不存在则创建，如果存在则获取
      OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
      if (!ossClient.doesBucketExist(bucketName)) {
        //创建bucket
        ossClient.createBucket(bucketName);
        //设置oss实例的访问权限：公共读
        ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
      }

      //获取上传文件流
      InputStream inputStream = file.getInputStream();

      //构建日期路径：avatar/2019/02/26/文件名

      //1.在文件里面添加唯一的值
      //文件名：uuid.扩展名
      String filename = file.getOriginalFilename();
      String uuid= UUID.randomUUID().toString().replaceAll("-","");
      filename=uuid+filename;
      String fileType = filename.substring(filename.lastIndexOf("."));
      String newName = filename + fileType;
//      String fileUrl = fileHost + "/" + filePath + "/" + newName;
      //2.把文件按造日期分类
      String filePath = new DateTime().toString("yyyy/MM/dd");
      filename=filePath+filename;

      //文件上传至阿里云
      //1.bucket名称
      //2.文件名
      //3.上传文件输入流
      ossClient.putObject(bucketName, filename, inputStream);

      // 关闭OSSClient。
      ossClient.shutdown();

      //获取url地址
      //null/2022/04/16/33232e97-b330-415a-8227-19051a39622d.jpg
      uploadUrl = "http://" + bucketName + "." + endPoint + "/" + filename;

    } catch (IOException e) {
      e.printStackTrace();
    }

    return uploadUrl;
  }
}
