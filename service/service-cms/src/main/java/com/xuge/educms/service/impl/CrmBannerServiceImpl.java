package com.xuge.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuge.educms.bean.CrmBanner;
import com.xuge.educms.mapper.CrmBannerMapper;
import com.xuge.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author xuge
 * @since 2022-04-24
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

  @Override
  @Cacheable(value = "banner", key = "'selectIndexList'")
  public List<CrmBanner> selectAllBanner() {
    //根据id进行排列，只显示两条记录
    QueryWrapper<CrmBanner> qw=new QueryWrapper<>();
    //1.降序id
    qw.orderByDesc("id");
    //2.显示前两条 last方法拼接语句
    qw.last("limit 2");
    return baseMapper.selectList(null);
  }
}
