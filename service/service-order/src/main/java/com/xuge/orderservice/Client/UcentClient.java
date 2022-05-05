package com.xuge.orderservice.Client;


import com.xuge.commonutils.UcenterMemberVo;
import com.xuge.orderservice.Client.Impl.UcenterClientImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * author: yjx
 * Date :2022/4/3013:46
 **/
@Component
@FeignClient(name = "service-ucenter", fallback = UcenterClientImpl.class)
public interface UcentClient {
  @ApiOperation("根据用户id获取用户信息接口")
  @GetMapping("/educenter/member/getUcenterMemberById/{id}")
  UcenterMemberVo getUcenterMemberVById(@PathVariable("id") String id);
}
