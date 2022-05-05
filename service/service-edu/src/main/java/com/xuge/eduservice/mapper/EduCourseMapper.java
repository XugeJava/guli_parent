package com.xuge.eduservice.mapper;

import com.xuge.eduservice.bean.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuge.eduservice.bean.vo.CoursePublishVo;
import com.xuge.eduservice.bean.vo.fornt.CourseWebVo;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author xuge
 * @since 2022-04-17
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
   //根据课程id获取最终发布信息
  CoursePublishVo getCoursePublishVo(@Param("id")String id);

  CourseWebVo getBaseCourseDetail(@Param("id")String id);
}
