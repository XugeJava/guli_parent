package com.xuge.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuge.commonutils.R;
import com.xuge.eduservice.bean.EduTeacher;
import com.xuge.eduservice.bean.vo.TeacherQuery;
import com.xuge.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author xuge
 * @since 2022-04-10
 */

@RestController
//@CrossOrigin
@RequestMapping("/eduservice/teacher")
@Api(description = "讲师管理")
public class EduTeacherController {

  @Autowired
  private EduTeacherService eduTeacherService;

  //查询讲师表所有数据
  @ApiOperation(value = "所有讲师列表")
  @GetMapping("findAll")
  public R list() {
    List<EduTeacher> list = eduTeacherService.list(null);
    //链式编程
    return R.ok().data("list", list);


  }

  //逻辑删除讲师
  @ApiOperation(value = "根据ID删除讲师")
  @DeleteMapping("{id}")
  public R deleteTeacherById(@ApiParam(name = "id", value = "输入讲师id", required = true) @PathVariable String id) {

    boolean b = eduTeacherService.removeById(id);
    if (b) {
      return R.ok();
    }
    return R.error();

  }

  //分页查询讲师的方法
  //current：当前页
  //limit：每页显示记录数
  @ApiOperation("分页查询讲师的方法")
  @GetMapping("/pageTeacher/{current}/{limit}")
  public R pageListTeacher(@PathVariable("current") Integer current, @PathVariable("limit") Integer limit) {
    //1.创建page对象
    Page<EduTeacher> eduTeacherPage = new Page<EduTeacher>(current, limit);
    //2.调用方法
    //分页查询，查完后，会把分页的数据封装到eduTeacherPage
    IPage<EduTeacher> page = eduTeacherService.page(eduTeacherPage, null);
    //获取查询到的数据
    List<EduTeacher> records = eduTeacherPage.getRecords();
    //获取总记录数
    long total = eduTeacherPage.getTotal();

    Map<String, Object> map = new HashMap<>();
    map.put("total", total);
    map.put("rows", records);

    return R.ok().data(map);
//    return R.ok().data("total",total).data("rows",records);
  }

  //带条件查询的分页
  @ApiOperation("带条件查询的分页")
  @PostMapping("pageTeacherCondition/{current}/{limit}")
  public R pageTeacherCondition(@PathVariable("current") Integer current, @PathVariable("limit") Integer limit,
                                @RequestBody(required = false) TeacherQuery teacherQuery) {
    //1.创建page对象
    Page<EduTeacher> eduTeacherPage = new Page<EduTeacher>(current, limit);
    //2.调用方法
    //分页查询，查完后，会把分页的数据封装到eduTeacherPage
    //3.构建条件
    QueryWrapper<EduTeacher> eqw = new QueryWrapper<>();
    String name = teacherQuery.getName();
    String begin = teacherQuery.getBegin();
    String end = teacherQuery.getEnd();
    Integer level = teacherQuery.getLevel();
    if (!StringUtils.isEmpty(name)) {
      //添加条件
      eqw.like("name", name);
    }
    if (!StringUtils.isEmpty(begin)) {
      //添加条件  这个是表中的字段名称
      eqw.gt("gmt_create", begin);
    }
    if (!StringUtils.isEmpty(end)) {
      //添加条件
      eqw.lt("gmt_modified", end);
    }
    if (!StringUtils.isEmpty(level)) {
      //添加条件
      eqw.eq("level", level);
    }

    eqw.orderByDesc("gmt_create");


    //条件拼接完成

    IPage<EduTeacher> page = eduTeacherService.page(eduTeacherPage, eqw);
    //获取查询到的数据
    List<EduTeacher> records = eduTeacherPage.getRecords();
    //获取总记录数
    long total = eduTeacherPage.getTotal();

    return R.ok().data("total", total).data("rows", records);
  }
  //新增讲师
  //新增讲师
  @ApiOperation("新增讲师")
  @PostMapping("/save")
  public R save(@RequestBody EduTeacher eduTeacher) {
    boolean save = eduTeacherService.save(eduTeacher);
    if (save) {
      return R.ok();
    }

    return R.error();
  }
  //根据id查询
  @ApiOperation("根据id查询讲师")
  @GetMapping("/getTeacher/{id}")
  public R getTeacherById(@PathVariable("id")String  id){
    EduTeacher eduTeacher = eduTeacherService.getById(id);
    return R.ok().data("eduTeacher",eduTeacher);

  }
  //根据id修改讲师
  @ApiOperation("修改讲师")
  @PostMapping("/updateTeacher")
  public R updateTeacherById(@RequestBody EduTeacher eduTeacher){
    boolean b = eduTeacherService.updateById(eduTeacher);
    if (b) {
      return R.ok();
    }
//    try {
//      int i=10/0;
//    } catch (Exception exception) {
//      throw new GuliException(20001,"执行了自定义异常处理~!!");
//    }
    return R.error();
  }


}
