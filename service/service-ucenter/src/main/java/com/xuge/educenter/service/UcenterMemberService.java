package com.xuge.educenter.service;

import com.xuge.educenter.bean.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xuge.educenter.bean.Vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author xuge
 * @since 2022-04-25
 */
public interface UcenterMemberService extends IService<UcenterMember> {


  String login(UcenterMember member);

  Boolean regist(RegisterVo registerVo);

  UcenterMember getByOpenId(String openid);

  Integer countRegistVo(String data);
}
