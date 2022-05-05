package com.xuge.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import com.xuge.commonutils.CourseWebVoOrder;
import com.xuge.commonutils.JwtUtils;
import com.xuge.commonutils.R;
import com.xuge.eduservice.Client.OrderClient;
import com.xuge.eduservice.bean.EduCourse;
import com.xuge.eduservice.bean.chapter.ChapterVo;
import com.xuge.eduservice.bean.vo.fornt.CourseFrontVo;
import com.xuge.eduservice.bean.vo.fornt.CourseWebVo;
import com.xuge.eduservice.service.EduChapterService;
import com.xuge.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * author: yjx
 * Date :2022/4/2720:59
 **/
@RestController
//@CrossOrigin
@Api(description = "前端课程管理")
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {
  @Autowired
  private EduCourseService courseService;
  @Autowired
  //注入videoService
  private EduChapterService chapterService;
  @Autowired
  private OrderClient orderClient;

  //1.条件查询带分页课程
  @PostMapping("getFrontPageCourseList/{current}/{limit}")
  @ApiOperation("条件查询带分页查询课程")
  public R getFrontPageCourseList(@PathVariable Integer current, @PathVariable Integer limit, @RequestBody(required = false) CourseFrontVo courseFrontVo) {
    //1.创建page对象
    Page<EduCourse> page = new Page<>(current, limit);
    Map<String, Object> map = courseService.getCourseFrontList(page, courseFrontVo);

    return R.ok().data(map);
  }

  @ApiOperation("课程详情")
  @GetMapping("getCourseDetail/{id}")
  public R getCourseDetail(@PathVariable("id") String id, HttpServletRequest request) {
    //1.根据课程id查询基本信息
    CourseWebVo courseWebVo = courseService.getBaseCourseInfo(id);

    //2.根据课程id获取章节小节
    List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(id);
    //3.获取是否当前用户已经支付当前课程
    String memberId = JwtUtils.getMemberIdByJwtToken(request);
    Boolean buyCourse = false;
    if(StringUtils.isNullOrEmpty(memberId)){
      return R.error().code(28004).message("请先登录，再购买或者观看课程!!");
    }
    buyCourse=orderClient.isBuyCourse(id,memberId);




    return R.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoList).data("buyCourse", buyCourse);
  }


  //根据课程id查询课程信息

  @GetMapping("getCourseInfo/{courseId}")
  @ApiOperation("根据课程id查询课程信息")
  public CourseWebVoOrder getCourseInfo(@PathVariable String courseId) {
    CourseWebVo course = courseService.getBaseCourseInfo(courseId);
    CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
    BeanUtils.copyProperties(course, courseWebVoOrder);
    return courseWebVoOrder;
  }


}
