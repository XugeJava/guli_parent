package com.xuge.orderservice.Client;

import com.xuge.commonutils.CourseWebVoOrder;
import com.xuge.orderservice.Client.Impl.CourseClientImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * author: yjx
 * Date :2022/4/3020:08
 **/
@FeignClient(name="service-edu",fallback = CourseClientImpl.class)
@Component
public interface CourseClient {
  @GetMapping("/eduservice/coursefront/getCourseInfo/{courseId}")
  @ApiOperation("根据课程id查询课程信息")
  public CourseWebVoOrder getCourseInfo(@PathVariable("courseId") String courseId) ;
}
