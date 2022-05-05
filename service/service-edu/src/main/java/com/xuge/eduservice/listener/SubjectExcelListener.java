package com.xuge.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuge.eduservice.bean.EduSubject;
import com.xuge.eduservice.bean.excel.SubjectData;
import com.xuge.eduservice.service.EduSubjectService;
import com.xuge.serviceBase.config.handler.GuliException;

/**
 * author: yjx
 * Date :2022/4/1715:22
 **/
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
  //因为SubjectExcelListener不能交给spring进行管理,需要自己new,不能注入其他对象
  public EduSubjectService subjectService;

  public SubjectExcelListener() {
  }
  //创建有参数构造，传递subjectService用于操作数据库
  public SubjectExcelListener(EduSubjectService subjectService) {
    this.subjectService = subjectService;
  }

  @Override
  public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
    if (subjectData == null) {
      throw new GuliException(20001, "文件数据为空");
    }
     //一行一行读取，每次读取两个值，第一个值一级分类，第二个值二级分类
     //1.先判断一级分类
    EduSubject eduOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
    if(eduOneSubject==null){//没有相同分类，进行添加
      eduOneSubject=new EduSubject();
       //1.设置
      eduOneSubject.setParentId("0");
      eduOneSubject.setTitle(subjectData.getOneSubjectName());
       //然后执行service方法中的save
      subjectService.save(eduOneSubject);
    }
    //获取一级分类的id值
    String pid=eduOneSubject.getId();
    EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
    if(existTwoSubject==null){//没有相同分类，进行添加
      existTwoSubject=new EduSubject();
      //1.设置
      existTwoSubject.setParentId(pid);
      existTwoSubject.setTitle(subjectData.getTwoSubjectName());
      //然后执行service方法中的save
      subjectService.save(existTwoSubject);
    }

  }
  //判断一级分类不能重复添加
  public EduSubject   existOneSubject(EduSubjectService subjectService,String name){
    QueryWrapper<EduSubject> qw=new QueryWrapper<>();
    qw.eq("title",name);
    qw.eq("parent_id","0");
   return subjectService.getOne(qw);
  }

  //判断二级分类不能重复添加
  public EduSubject   existTwoSubject(EduSubjectService subjectService,String name,String pid){
    QueryWrapper<EduSubject> qw=new QueryWrapper<>();
    qw.eq("title", name);
    qw.eq("parent_id",pid);
    return subjectService.getOne(qw);
  }



  @Override
  public void doAfterAllAnalysed(AnalysisContext analysisContext) {

  }
}
