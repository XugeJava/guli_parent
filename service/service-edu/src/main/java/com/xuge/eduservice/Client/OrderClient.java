package com.xuge.eduservice.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * author: yjx
 * Date :2022/5/115:26
 **/
@FeignClient("service-order")
@Component
public interface OrderClient {
  //根据课程id和用户id查询订单的状态
  @GetMapping("/orderservice/order/isBuyCourse/{courseId}/{memberId}")
  public Boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId) ;
}
