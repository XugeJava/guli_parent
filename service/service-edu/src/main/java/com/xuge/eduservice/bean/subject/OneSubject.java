package com.xuge.eduservice.bean.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * author: yjx
 * Date :2022/4/1721:09
 **/
@Data
public class OneSubject {
  private String id;
  private String title;

  private List<TwoSubject> children=new ArrayList<>();
}
