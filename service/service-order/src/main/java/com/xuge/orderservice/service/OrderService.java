package com.xuge.orderservice.service;

import com.xuge.orderservice.bean.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author xuge
 * @since 2022-04-30
 */
public interface OrderService extends IService<Order> {

  String createOrder(String courseId, String memberIdByJwtToken);
}
