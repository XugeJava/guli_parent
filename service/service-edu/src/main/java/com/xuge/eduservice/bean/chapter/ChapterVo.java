package com.xuge.eduservice.bean.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * author: yjx
 * Date :2022/4/1822:23
 **/
@Data
public class ChapterVo {
  private String id;
  private String title;
  //一个章节对应对个小结
  private List<VideoVo> children=new ArrayList<>();
}
