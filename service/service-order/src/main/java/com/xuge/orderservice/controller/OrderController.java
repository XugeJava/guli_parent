package com.xuge.orderservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.util.StringUtils;
import com.xuge.commonutils.JwtUtils;
import com.xuge.commonutils.R;
import com.xuge.orderservice.bean.Order;
import com.xuge.orderservice.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author xuge
 * @since 2022-04-30
 */
@RestController
@RequestMapping("orderservice/order")
//@CrossOrigin
@Api(description = "订单管理")
public class OrderController {
  @Autowired
  private OrderService orderService;

  //1.生成订单方法
  @PostMapping("createOrder/{courseId}")
  @ApiOperation("生成订单方法")
  public R createOrder(@PathVariable String courseId, HttpServletRequest request) {
    String orderNo = orderService.createOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
    if (StringUtils.isNullOrEmpty(orderNo)) {
      return R.error().message("生成订单失败");
    }
    return R.ok().data("orderNo", orderNo);
  }

  //根据订单id获取订单信息
  @GetMapping("getOrder/{orderId}")
  public R get(@PathVariable String orderId) {
    QueryWrapper<Order> wrapper = new QueryWrapper<>();
    wrapper.eq("order_no", orderId);
    Order order = orderService.getOne(wrapper);
    return R.ok().data("item", order);
  }

  //根据课程id和用户id查询订单的状态
  @GetMapping("isBuyCourse/{courseId}/{memberId}")
  public Boolean isBuyCourse(@PathVariable String courseId, @PathVariable String memberId) {
    //1.构建条件对象
    QueryWrapper<Order> qw=new QueryWrapper<>();
    qw.eq("course_id",courseId);
    qw.eq("member_id",memberId);
    qw.eq("status", 1);//1代表已经支付
    return orderService.count(qw)>0;

  }


}

