package com.xuge.orderservice.service;

import com.xuge.orderservice.bean.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author xuge
 * @since 2022-04-30
 */
public interface PayLogService extends IService<PayLog> {

  Map<String, Object> createNative(String orderNo);

  Map<String, String> queryStatus(String orderNo);

  void updateOrderStatus(Map<String, String> map);
}
