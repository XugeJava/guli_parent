package com.xuge.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuge.eduservice.bean.EduTeacher;
import com.xuge.eduservice.mapper.EduTeacherMapper;
import com.xuge.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author xuge
 * @since 2022-04-10
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

  @Override
  public Map<String, Object> getPageFrontList(Page<EduTeacher> page) {
    //1.创建条件对象
    QueryWrapper<EduTeacher> qw=new QueryWrapper<>();
    qw.orderByDesc("id");
    //把分页数据封装到page中
     baseMapper.selectPage(page, qw);
   //2.将page数据封装到page中
    List<EduTeacher> records = page.getRecords();
    //2.1先取出page中数据
    long current = page.getCurrent();
    long pages = page.getPages();
    long size = page.getSize();
    long total = page.getTotal();
    boolean hasNext = page.hasNext();
    boolean hasPrevious = page.hasPrevious();
    //2.2构建一个map独享，封装数据
    Map<String, Object> map = new HashMap<>();
    map.put("items", records);
    map.put("current", current);
    map.put("pages", pages);
    map.put("size", size);
    map.put("total", total);
    map.put("hasNext", hasNext);
    map.put("hasPrevious", hasPrevious);





    return map;
  }
}
