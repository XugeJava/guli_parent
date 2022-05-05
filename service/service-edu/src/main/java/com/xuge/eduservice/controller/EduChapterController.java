package com.xuge.eduservice.controller;


import com.xuge.commonutils.R;
import com.xuge.eduservice.bean.EduChapter;
import com.xuge.eduservice.bean.chapter.ChapterVo;
import com.xuge.eduservice.service.EduChapterService;
import com.xuge.serviceBase.config.handler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author xuge
 * @since 2022-04-17
 */
@RestController
//@CrossOrigin
@Api(description = "课程大纲管理")
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
  @Autowired
  EduChapterService chapterService;
  //课程大纲列表-根据课程id进行查询章节
  @GetMapping("getChapterVo/{courseId}")
  @ApiOperation("获取大纲列表")
  public R getChapterVo(@PathVariable("courseId")String courseId){
    //1.获取列表
    List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
    //2.传入data数据
    return R.ok().data("allChapterVo",list);
  }
  @ApiOperation("添加章节")
  @PostMapping("addChapter")
  public R  addChapter(@RequestBody EduChapter eduChapter){
    chapterService.save(eduChapter);
    return R.ok();
  }

  //根据id查询章节
  @ApiOperation("根据id查询章节")
  @GetMapping("getChapter/{id}")
  public R getChapter(@PathVariable("id") String id ){
    EduChapter eduChapter = chapterService.getById(id);

    return R.ok().data("eduChapter",eduChapter);
  }
  //修改章节
  //根据id查询章节
  @ApiOperation("修改章节")
  @PostMapping("updateChapter")
  public R getChapter(@RequestBody EduChapter eduChapter){
    boolean b = chapterService.updateById(eduChapter);
    if (!b) {
      throw new GuliException(20001,"修改失败!");
    }
    return R.ok();
  }

  //修改章节
  //根据id查询章节
  @ApiOperation("删除章节")
  @DeleteMapping ("deleteChapter/{id}")
  public R deleteChapter(@PathVariable String id){
    boolean b = chapterService.deleteChapter(id);
    if (!b) {
     return R.error();
    }else{
      return R.ok();
    }
  }









}

