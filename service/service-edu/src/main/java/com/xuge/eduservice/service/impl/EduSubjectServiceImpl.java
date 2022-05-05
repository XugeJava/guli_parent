package com.xuge.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuge.eduservice.bean.EduSubject;
import com.xuge.eduservice.bean.excel.SubjectData;
import com.xuge.eduservice.bean.subject.OneSubject;
import com.xuge.eduservice.bean.subject.TwoSubject;
import com.xuge.eduservice.listener.SubjectExcelListener;
import com.xuge.eduservice.mapper.EduSubjectMapper;
import com.xuge.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuge.serviceBase.config.handler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author xuge
 * @since 2022-04-17
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
  //添加课程分类
  @Override
  public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
    try {
      //1.文件输入流
      InputStream is = file.getInputStream();
      //2.调用方法进行读取
      EasyExcel.read(is, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();

    } catch (Exception e) {
      e.printStackTrace();
      throw new GuliException(20002, "添加课程分类失败");

    }
  }

  @Override
  public List<OneSubject> getAllOneTwoSubject() {
    //1.查询所有一级分类  parent_id=0
    QueryWrapper<EduSubject> qw = new QueryWrapper<>();
    qw.eq("parent_id", "0");
    List<EduSubject> oneEduSubjects = baseMapper.selectList(qw);

    //2.查询所有二级分类  parent_id!=0
    QueryWrapper<EduSubject> qw1 = new QueryWrapper<>();
    qw.ne("parent_id", "0");
    List<EduSubject> twoEduSubjects = baseMapper.selectList(qw1);
    //创建集合，用于最终的封装
    List<OneSubject> oneSubjects = new ArrayList<>();

    //3.封装一级分类
    //查询出来的所有一级分类遍历，取出每一个一级分类对象，获取每一个一级分类对象值
    //封装到符合要求的集合中去List<OneSubject> oneSubjects1
    for (EduSubject eduSubject : oneEduSubjects) {
      //把eduSubject的值取出来放到oneSubject中
      OneSubject oneSubject = new OneSubject();
//      oneSubject.setId(eduSubject.getId());
//      oneSubject.setTitle(eduSubject.getTitle());
      //用工具类实现 把eduSubject的值填充到oneSubject中去

      BeanUtils.copyProperties(eduSubject, oneSubject);
      //4.封装二级分类
      //创建list集合用于封装二级分类
      List<TwoSubject>twoSubjects=new ArrayList<>();
      for (EduSubject eduSubject1 : twoEduSubjects) {
        TwoSubject twoSubject= new TwoSubject();
        //判端二级分类parent_id和一级分类id相同
        if(eduSubject1.getParentId().equals(eduSubject.getId())){
          //把eduSubject1放到twoSubject中去
          BeanUtils.copyProperties(eduSubject1, twoSubject);
          //再把twoSubject放到twoSubjects1集合中去
          twoSubjects.add(twoSubject);

        }

      }
      //把一级分类下的所属二级分类加入到其中去
      oneSubject.setChildren(twoSubjects);

      //把所有一级分类放到一级分类总集合中
      oneSubjects.add(oneSubject);
    }


    return oneSubjects;

  }
}
