package com.xuge.educenter.mapper;

import com.xuge.educenter.bean.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author xuge
 * @since 2022-04-25
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

  Integer registerDay(@Param("data") String data);
}
