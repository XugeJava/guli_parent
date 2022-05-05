package com.xuge.eduservice.service;

import com.xuge.eduservice.bean.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author xuge
 * @since 2022-04-17
 */
public interface EduVideoService extends IService<EduVideo> {

  Boolean removeVideoById(String id);
}
