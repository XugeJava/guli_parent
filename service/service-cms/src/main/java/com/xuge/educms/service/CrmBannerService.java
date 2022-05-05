package com.xuge.educms.service;

import com.xuge.educms.bean.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author xuge
 * @since 2022-04-24
 */
public interface CrmBannerService extends IService<CrmBanner> {

  List<CrmBanner> selectAllBanner();
}
