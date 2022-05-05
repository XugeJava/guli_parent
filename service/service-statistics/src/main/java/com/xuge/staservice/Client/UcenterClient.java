package com.xuge.staservice.Client;

import com.xuge.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * author: yjx
 * Date :2022/5/116:50
 **/
@FeignClient("service-ucenter")
@Component
public interface UcenterClient {
  @ApiOperation("查询某一天注册人数")
  @GetMapping("/educenter/member/getRegistCount/{data}")
  public R getRegistCount(@PathVariable("data") String data);
}
