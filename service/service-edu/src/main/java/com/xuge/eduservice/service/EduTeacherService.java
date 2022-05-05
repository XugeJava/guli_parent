package com.xuge.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuge.eduservice.bean.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author xuge
 * @since 2022-04-10
 */
public interface EduTeacherService extends IService<EduTeacher> {
  Map<String,Object> getPageFrontList(Page<EduTeacher> page);
}
