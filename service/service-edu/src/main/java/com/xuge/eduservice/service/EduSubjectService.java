package com.xuge.eduservice.service;

import com.xuge.eduservice.bean.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xuge.eduservice.bean.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author xuge
 * @since 2022-04-17
 */
public interface EduSubjectService extends IService<EduSubject> {

  void saveSubject(MultipartFile file,EduSubjectService subjectService);

  List<OneSubject> getAllOneTwoSubject();
}
