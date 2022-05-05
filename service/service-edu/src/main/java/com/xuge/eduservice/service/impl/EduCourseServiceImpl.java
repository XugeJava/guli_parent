package com.xuge.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuge.eduservice.bean.EduCourse;
import com.xuge.eduservice.bean.EduCourseDescription;
import com.xuge.eduservice.bean.vo.CourseInfoVo;
import com.xuge.eduservice.bean.vo.CoursePublishVo;
import com.xuge.eduservice.bean.vo.CourseQueryVo;
import com.xuge.eduservice.bean.vo.fornt.CourseFrontVo;
import com.xuge.eduservice.bean.vo.fornt.CourseWebVo;
import com.xuge.eduservice.mapper.EduCourseMapper;
import com.xuge.eduservice.service.EduChapterService;
import com.xuge.eduservice.service.EduCourseDescriptionService;
import com.xuge.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuge.eduservice.service.EduVideoService;
import com.xuge.serviceBase.config.handler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author xuge
 * @since 2022-04-17
 */
@Service
@Transactional
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
  //注入操作课程简介的服务对象
  @Autowired
  private EduCourseDescriptionService eduCourseDescriptionService;
  //注入小节
  @Autowired
  private EduVideoService eduVideoService;
  @Autowired
  private EduChapterService eduChapterService;

  //添加课程信息的方法
  @Override
  public String saveCourse(CourseInfoVo courseInfoVo) {
    //1.向课程表添加信息
    //1.1先把courseInfo转为EduCourse对象
    EduCourse eduCourse = new EduCourse();
    BeanUtils.copyProperties(courseInfoVo, eduCourse);
    int insert = baseMapper.insert(eduCourse);
    if (insert <= 0) {
      throw new GuliException(20001, "添加课程基本信息失败");
    }
    //获取课程id
    String id = eduCourse.getId();

    //2.向课程描述表添加基本信息
    //2.1创建对象
    EduCourseDescription eduCourseDescription = new EduCourseDescription();
    eduCourseDescription.setDescription(courseInfoVo.getDescription());
    //2.2设置描述id就是课id
    eduCourseDescription.setId(id);
    eduCourseDescriptionService.save(eduCourseDescription);
    //3.返回课程id
    return id;

  }

  //根据课程id查询课程基本信息
  @Override
  public CourseInfoVo getCourseInfoById(String courseId) {
    //1.查询课程表信息
    EduCourse eduCourse = baseMapper.selectById(courseId);
    //2.查询描述表
    EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
    //3.创建一个courseInfoVo对象
    CourseInfoVo courseInfoVo = new CourseInfoVo();
    //4.封装eduCourse对象
    BeanUtils.copyProperties(eduCourse, courseInfoVo);
    //5.封装eduCourseDescription对象
    courseInfoVo.setDescription(eduCourseDescription.getDescription());
    return courseInfoVo;
  }

  @Override
  public void updateCourseInfoVo(CourseInfoVo courseInfo) {
    //1.修改课程表
    //2.创建一个eduCourse对象用于接收courseInfoVo
    EduCourse eduCourse = new EduCourse();
    BeanUtils.copyProperties(courseInfo, eduCourse);
    int i = baseMapper.updateById(eduCourse);
    if (i == 0) {
      throw new GuliException(20001, "修改课程表失败!");
    }


    //2.修改课程描述表
    EduCourseDescription eduCourseDescription = new EduCourseDescription();
    eduCourseDescription.setId(courseInfo.getId());
    eduCourseDescription.setDescription(courseInfo.getDescription());
    boolean b = eduCourseDescriptionService.updateById(eduCourseDescription);
    if (!b) {
      throw new GuliException(20001, "修改课程描述表失败!");
    }

  }

  //实现获取课程发布信息的方法
  @Override
  public CoursePublishVo getCoursePublishVo(String courseId) {
    return baseMapper.getCoursePublishVo(courseId);
  }

  @Override
  public IPage<EduCourse> getCourseQueryPageList(Integer current, Integer pageSize, CourseQueryVo courseQueryVo) {
    //1.创建page对象
    Page<EduCourse> eduCoursePage = new Page<EduCourse>(current, pageSize);
    //2.调用方法
    //分页查询，查完后，会把分页的数据封装到eduTeacherPage
    //3.构建条件
    QueryWrapper<EduCourse> eqw = new QueryWrapper<>();
    //先按造创建时间降序
    eqw.orderByDesc("gmt_create");

    if (courseQueryVo == null) {//此时为没有任何查询条件，自然不会执行后面的语句
      //返回记录
      return baseMapper.selectPage(eduCoursePage, eqw);
    }
    //3.1获取课程标题
    String title = courseQueryVo.getTitle();
    //3.2获取一级分类id
    String subjectParentId = courseQueryVo.getSubjectParentId();
    //3.3获取二级分类id
    String subjectId = courseQueryVo.getSubjectId();
    //3.4获取讲师id
    String teacherId = courseQueryVo.getTeacherId();
    if (!StringUtils.isEmpty(title)) {
      //添加条件
      eqw.like("title", title);
    }
    if (!StringUtils.isEmpty(subjectParentId)) {
      //添加条件  这个是表中的字段名称
      eqw.eq("subject_parent_id", subjectParentId);
    }
    if (!StringUtils.isEmpty(subjectId)) {
      //添加条件
      eqw.eq("subject_id", subjectId);
    }
    if (!StringUtils.isEmpty(teacherId)) {
      //添加条件
      eqw.eq("teacher_id", teacherId);
    }
    //获取查询到的数据
    return baseMapper.selectPage(eduCoursePage, eqw);

  }

  @Override
  public Boolean removeCourse(String id) {
    //1.根据课程id删除小节
    Boolean b = eduVideoService.removeVideoById(id);

    //2.根据课程id删除章节
    Boolean b2 = eduChapterService.removeChapterById(id);

    //3.删除课程描述
    boolean b1 = eduCourseDescriptionService.removeById(id);

    //4.根据课程id删除课程
    int i = baseMapper.deleteById(id);


    return true;
  }

  //实现课程分页
  @Override
  public Map<String, Object> getCourseFrontList(Page<EduCourse> page, CourseFrontVo courseFrontVo) {
    //1.创建条件对象
    QueryWrapper<EduCourse> qw = new QueryWrapper<>();
    //2.判断条件值中的属性空情况
    String title = courseFrontVo.getTitle();
    String buyCountSort = courseFrontVo.getBuyCountSort();
    String priceSort = courseFrontVo.getPriceSort();
    String gmtCreateSort = courseFrontVo.getGmtCreateSort();
    String subjectId = courseFrontVo.getSubjectId();
    String parentId = courseFrontVo.getSubjectParentId();
    String teacherId = courseFrontVo.getTeacherId();

   //3.判断一级分类是否存在
    if(!StringUtils.isEmpty(parentId)){
      qw.eq("subject_parent_id",parentId);
    }
   //4.判断二级分类
    if(!StringUtils.isEmpty(subjectId)){
      qw.eq("subject_id",subjectId);
    }

    //5.销量排序
    if (!StringUtils.isEmpty(buyCountSort)) {
      qw.orderByDesc("buy_count");
    }
   //6排序
    if (!StringUtils.isEmpty(gmtCreateSort)) {
      qw.orderByDesc("gmt_create");
    }
    //7.价格排序
    if (!StringUtils.isEmpty(priceSort)) {
      qw.orderByDesc("price");
    }
    //8.调用查询
    baseMapper.selectPage(page, qw);

   //9.数据放到map返回
    List<EduCourse> records = page.getRecords();
    long current = page.getCurrent();
    long pages = page.getPages();
    long size = page.getSize();
    long total = page.getTotal();
    boolean hasNext = page.hasNext();
    boolean hasPrevious = page.hasPrevious();

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("items", records);
    map.put("current", current);
    map.put("pages", pages);
    map.put("size", size);
    map.put("total", total);
    map.put("hasNext", hasNext);
    map.put("hasPrevious", hasPrevious);




    return map;
  }
  //查询课程·基本信息
  @Override
  public CourseWebVo getBaseCourseInfo(String id) {
   //1.根据id，查询课程基本信息
    return baseMapper.getBaseCourseDetail(id);
  }
}
