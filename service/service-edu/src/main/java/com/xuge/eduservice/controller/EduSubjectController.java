package com.xuge.eduservice.controller;


import com.xuge.commonutils.R;
import com.xuge.eduservice.bean.subject.OneSubject;
import com.xuge.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author xuge
 * @since 2022-04-17
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
@Api(description="课程分类管理")
public class EduSubjectController {
  @Autowired
  private EduSubjectService subjectService;
  //添加课程分类
  //获取上传的文件，把文件内容读取出来
  @PostMapping("addSubject")
  @ApiOperation("保存表格数据")
  public R addSubject(MultipartFile file){
    //上传过来的excel文件
    subjectService.saveSubject(file,subjectService);
    return R.ok();
  }

  //课程分类列表树形
  @ApiOperation("课程列表树形")
  @GetMapping("getAllSubject")
  public R  getAllSubject(){
     List<OneSubject> list=subjectService.getAllOneTwoSubject();
    return R.ok().data("list",list);
  }



}

