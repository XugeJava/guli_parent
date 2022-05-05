package com.xuge.eduservice.service;

import com.xuge.eduservice.bean.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xuge.eduservice.bean.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xuge
 * @since 2022-04-17
 */
public interface EduChapterService extends IService<EduChapter> {

  List<ChapterVo> getChapterVideoByCourseId(String courseId);
 //删除章节
  boolean deleteChapter(String id);

  Boolean removeChapterById(String id);
}
