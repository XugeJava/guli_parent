package com.xuge.eduservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xuge.commonutils.R;
import com.xuge.eduservice.bean.EduCourse;
import com.xuge.eduservice.bean.vo.CourseInfoVo;
import com.xuge.eduservice.bean.vo.CoursePublishVo;
import com.xuge.eduservice.bean.vo.CourseQueryVo;
import com.xuge.eduservice.service.EduCourseService;
import com.xuge.serviceBase.config.handler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author xuge
 * @since 2022-04-17
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/course")
@Api(description = "课程管理")
public class EduCourseController {

  @Autowired
  private EduCourseService courseService;
  //添加课程基本信息功能
  @ApiOperation("添加课程基本信息功能")
  @PostMapping("addCourse")
  public R addCourse(@RequestBody CourseInfoVo courseInfo){
    String id=courseService.saveCourse(courseInfo);
    return R.ok().data("courseId",id);
  }
  //根据课程id查出课程基本信息
  @ApiOperation("根据课程id查出课程基本信息")
  @GetMapping("getCourseInfo/{courseId}")
  public R  getCourseInfo(@PathVariable("courseId")String courseId){
    //1.查询出courseInfo对象
    CourseInfoVo courseInfoVo = courseService.getCourseInfoById(courseId);

    return R.ok().data("courseInfo",courseInfoVo);
  }
  @ApiOperation("修改课程信息")
  @PostMapping("updateCourseInfo")
  public R updateCourseInfo(@RequestBody CourseInfoVo  courseInfo){
    courseService.updateCourseInfoVo(courseInfo);

    return R.ok();
  }
  //根据课程id获取对应的课程发布信息
  @ApiOperation("根据课程id获取对应的课程发布信息")
  @GetMapping("/getCoursePublish/{courseId}")
  public R  getCoursePublish(@PathVariable("courseId")String courseId){
    CoursePublishVo coursePublishVo = courseService.getCoursePublishVo(courseId);
    if(coursePublishVo==null){
      throw new GuliException(20001,"找不到对应的课程发布信息!");
    }

    return R.ok().data("coursePublish",coursePublishVo);
  }
  //课程最终发布接口
  //修改课程状态
  @ApiOperation("课程最终发布接口")
  @PostMapping("publishCourse/{courseId}")
  public R publishCourse(@PathVariable("courseId")String courseId){
    EduCourse eduCourse = new EduCourse();
    //1.设置id
    eduCourse.setId(courseId);
    //2.设置发布状态
    eduCourse.setStatus("Normal");
    boolean b = courseService.updateById(eduCourse);
    if(!b) {
      throw new GuliException(20001, "修改发布状态失败!");
    }
    return R.ok();
  }


  //课程列表 基本实现
  @ApiOperation("基本课程列表")
  @GetMapping("getAllCourseInfo")
  public R  getAllCourseInfo(){
    List<EduCourse> list = courseService.list(null);
    return R.ok().data("list",list);
  }


  //todo 课程列表带条件分页

  /**
   *
   * @param current 当前页号
   * @param limit 每页记录数
   * @param courseQuery 封装的课程列表查询条件
   * @return   返回处理后的分页列表
   */
  @ApiOperation("课程列表带分页")
  @PostMapping("getCoursePageVo/{current}/{limit}")
  public R getCoursePageVo(@PathVariable("current")Integer current, @PathVariable("limit")Integer limit,@RequestBody(required = false)  CourseQueryVo courseQuery){
    IPage<EduCourse> pageCourse = courseService.getCourseQueryPageList(current, limit, courseQuery);
    //1.获取记录集合
    List<EduCourse> list = pageCourse.getRecords();
    //2.获取总记录数
    long total = pageCourse.getTotal();
    return R.ok().data("list",list).data("total",total);
  }
  //删除课程信息
  @ApiOperation("删除课程信息")
  @DeleteMapping("deleteCourse/{id}")
  public R deleteCourse(@PathVariable String id){
    Boolean b=courseService.removeCourse(id);
   if(!b){
     return R.error();
   }
    return R.ok();
  }







}

