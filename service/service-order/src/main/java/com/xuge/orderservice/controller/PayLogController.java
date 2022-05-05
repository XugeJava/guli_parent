package com.xuge.orderservice.controller;


import com.xuge.commonutils.R;
import com.xuge.orderservice.service.PayLogService;
import com.xuge.serviceBase.config.handler.GuliException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author xuge
 * @since 2022-04-30
 */
@RestController
@RequestMapping("/orderservice/paylog")
//@CrossOrigin
@Api(description = "支付管理")
public class PayLogController {
  @Autowired
  private PayLogService payLogService;

  //1.生成微信支付二维码接口
  @GetMapping("createNativeCode/{orderNo}")
  public R createNativeCode(@PathVariable String orderNo) {
    //返回信息，包含二维码地址，其他信息
    Map<String, Object> map = payLogService.createNative(orderNo);
    System.out.println("二维码详情**********"+map);
    return R.ok().data(map);
  }
  //查询订单状态
  @GetMapping("queryPayStatus/{orderNo}")
  public R queryPayStatus(@PathVariable String orderNo){
    Map<String,String>map=payLogService.queryStatus(orderNo);
    System.out.println("查询订单状态集合****"+map);
    if(map==null){
      return R.error().message("支付出错");
    }
    if (("SUCCESS").equals(map.get("trade_state"))) {
      //如果成功--支付成功
      //更改订单状态
      payLogService.updateOrderStatus(map);
      return R.ok().message("支付成功");
    }

    return R.ok().code(25000).message("支付中");
  }
}

