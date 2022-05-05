package com.xuge.eduservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuge.eduservice.bean.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xuge.eduservice.bean.vo.CourseInfoVo;
import com.xuge.eduservice.bean.vo.CoursePublishVo;
import com.xuge.eduservice.bean.vo.CourseQueryVo;
import com.xuge.eduservice.bean.vo.fornt.CourseFrontVo;
import com.xuge.eduservice.bean.vo.fornt.CourseWebVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xuge
 * @since 2022-04-17
 */
public interface EduCourseService extends IService<EduCourse> {

  String saveCourse(CourseInfoVo courseInfoVo);

  CourseInfoVo getCourseInfoById(String courseId);

  void updateCourseInfoVo(CourseInfoVo courseInfo);
  //根据课程id查询课程确认发布信息
  CoursePublishVo getCoursePublishVo(String courseId);
  //带条件分页
  IPage<EduCourse> getCourseQueryPageList(Integer current, Integer pageSize, CourseQueryVo courseQueryVo);

  Boolean removeCourse(String id);
  Map<String,Object> getCourseFrontList(Page<EduCourse> page, CourseFrontVo courseFrontVo);

  CourseWebVo getBaseCourseInfo(String id);
}
