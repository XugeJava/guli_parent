package com.xuge.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuge.commonutils.JwtUtils;
import com.xuge.commonutils.R;
import com.xuge.commonutils.UcenterMemberVo;
import com.xuge.eduservice.Client.UcentClient;
import com.xuge.eduservice.bean.EduComment;
import com.xuge.eduservice.service.EduCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author xuge
 * @since 2022-04-30
 */
@RestController
@RequestMapping("/eduservice/comment")
//@CrossOrigin
@Api(description = "评论管理")
public class EduCommentController {
  @Autowired
  private EduCommentService commentService;
  //注入调用用户端服务接口
  @Autowired
  private UcentClient ucentClient;

  //1.评论分页
  @ApiOperation("评论分页")
  @GetMapping("getCommentPage/{current}/{limit}/{courseId}")
  public R getCommentPage(@PathVariable("current")Integer current, @PathVariable("limit")Integer limit,@PathVariable String courseId){
    //1.创建评论对象
    Page<EduComment> page=new Page<>(current,limit);
    //2.调用page
    QueryWrapper<EduComment> qw=new QueryWrapper<>();
    qw.eq("course_id",courseId);
    qw.orderByDesc("gmt_create");
    commentService.page(page,qw);
    List<EduComment> records = page.getRecords();

    Map<String, Object> map = new HashMap<>();
    map.put("items", records);
    map.put("current", page.getCurrent());
    map.put("pages", page.getPages());
    map.put("size", page.getSize());
    map.put("total", page.getTotal());
    map.put("hasNext", page.hasNext());
    map.put("hasPrevious", page.hasPrevious());
    return R.ok().data(map);
  }
  //2.添加评论
  @PostMapping("addComment")
  public R addComment(@RequestBody EduComment comment, HttpServletRequest request){
    //1.获取用户id
    String memberId = JwtUtils.getMemberIdByJwtToken(request);

    //2.判断id是否为空来判断用户是否登录
    if(StringUtils.isEmpty(memberId)){
      return R.error().code(28004).message("请先登录，再评论!!!");
    }
    //3.给comment设置用户id值
    comment.setMemberId(memberId);
    //4.远程调用查询用户信息
    UcenterMemberVo ucenterMember = ucentClient.getUcenterMemberVById(memberId);
    comment.setNickname(ucenterMember.getNickname());
    comment.setAvatar(ucenterMember.getAvatar());

    commentService.save(comment);


    return R.ok();
  }

}

