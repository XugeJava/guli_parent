package com.xuge.orderservice.service.impl;

import com.xuge.commonutils.CourseWebVoOrder;
import com.xuge.commonutils.UcenterMemberVo;
import com.xuge.orderservice.Client.CourseClient;
import com.xuge.orderservice.Client.UcentClient;
import com.xuge.orderservice.bean.Order;
import com.xuge.orderservice.mapper.OrderMapper;
import com.xuge.orderservice.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuge.orderservice.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author xuge
 * @since 2022-04-30
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
 //注入远程调用课程端
  @Autowired
  private CourseClient courseClient;
  //注入远程调用用户端
  @Autowired
  private UcentClient ucentClient;

  public String createOrder(String courseId, String memberId) {
    //1.通过远程调用根据用户id获取用户信息
    UcenterMemberVo memberVo = ucentClient.getUcenterMemberVById(memberId);

    //2.通过远程根据课程id获取课程信息
    CourseWebVoOrder courseInfo = courseClient.getCourseInfo(courseId);
    //3.取出要的数据，设置进去
    Order order = new Order();
    //设置封面
    order.setCourseCover(courseInfo.getCover());
    //设置订单号
    order.setOrderNo(OrderNoUtil.getOrderNo());
    //设置课程id
    order.setCourseId(courseInfo.getId());
    //设置课程名称
    order.setCourseTitle(courseInfo.getTitle());
    //设置讲师名称
    order.setTeacherName(courseInfo.getTeacherName());
    //设置会员id
    order.setMemberId(memberVo.getId());
    //设置会员昵称
    order.setNickname(memberVo.getNickname());
    //设置会员手机
    order.setMobile(memberVo.getMobile());
    //设置订单金额
//    BigDecimal bigDecimal = new BigDecimal("0.00000001");
    order.setTotalFee(courseInfo.getPrice());
    //设置支付类型-未支付
    order.setPayType(1);
    order.setStatus(0);
    baseMapper.insert(order);


    return order.getOrderNo();
  }
}
