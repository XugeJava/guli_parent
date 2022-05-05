package com.xuge.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuge.commonutils.R;
import com.xuge.eduservice.bean.EduCourse;
import com.xuge.eduservice.bean.EduTeacher;
import com.xuge.eduservice.service.EduCourseService;
import com.xuge.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * author: yjx
 * Date :2022/4/2610:50
 **/
@RestController
@RequestMapping("/eduservice/teacherFront")
//@CrossOrigin
@Api(description = "前端讲师管理")
public class TeacherFrontController {
  @Autowired
  private EduTeacherService teacherService;
  @Autowired
  private EduCourseService courseService;

  //1.分页查询教师
  @ApiOperation(("分页查询教师"))
  @GetMapping("getPageTeacherFront/{current}/{limit}")
  public R getPageTeacherFront(@PathVariable("current") Integer current, @PathVariable("limit") Integer limit) {
    //1.创建page对象
    Page<EduTeacher> page = new Page<>(current, limit);
    //2.获取教师分页列表
    Map<String, Object> map = teacherService.getPageFrontList(page);
    return R.ok().data(map);
  }

  //3.讲师详情接口
  @ApiOperation("讲师详情接口")
  @GetMapping("getFrontTeacherDetail/{id}")
  public R getFrontTeacherDetail(@PathVariable("id") String id) {
    //1.根据讲师id查询讲师基本信息
    EduTeacher teacher= teacherService.getById(id);
    //2.根据讲师id查询课程信息
    QueryWrapper<EduCourse> qw=new QueryWrapper<>();
    qw.eq("teacher_id",id);
    List<EduCourse> list = courseService.list(qw);

    return R.ok().data("teacher",teacher).data("courseList",list);
  }


}
