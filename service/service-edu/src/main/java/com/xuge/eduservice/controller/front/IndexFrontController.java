package com.xuge.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuge.commonutils.R;
import com.xuge.eduservice.bean.EduCourse;
import com.xuge.eduservice.bean.EduTeacher;
import com.xuge.eduservice.service.EduCourseService;
import com.xuge.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author: yjx
 * Date :2022/4/2411:24
 **/
@RestController
@RequestMapping("/eduservice/indexFront")
//@CrossOrigin
public class IndexFrontController {
  @Autowired
  private EduTeacherService teacherService;
  @Autowired
  private EduCourseService courseService;
  //查询前八条课程,查询前四个名师
  @GetMapping("getIndex")
  public R getIndex (){
    //1.查询前八个热门课程
    QueryWrapper<EduCourse> qw1=new QueryWrapper<>();
    qw1.orderByDesc("id");
    qw1.last("limit 8");
    List<EduCourse> list = courseService.list(qw1);




    //2.查询前四个名师
    QueryWrapper<EduTeacher> qw2=new QueryWrapper<>();
    qw2.orderByDesc("id");
    qw2.last("limit 4");
    List<EduTeacher> list1 = teacherService.list(qw2);



    return R.ok().data("courseList",list).data("teacherList",list1);
  }




}
