package com.xuge.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.util.StringUtils;
import com.xuge.commonutils.JwtUtils;
import com.xuge.commonutils.MD5;
import com.xuge.educenter.bean.UcenterMember;
import com.xuge.educenter.bean.Vo.RegisterVo;
import com.xuge.educenter.mapper.UcenterMemberMapper;
import com.xuge.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuge.serviceBase.config.handler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author xuge
 * @since 2022-04-25
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
  //注入redisTemplate
  @Autowired
  private RedisTemplate<String,String> redisTemplate;
 //登录方法
  @Override
  public String login(UcenterMember member) {
    //1.获取手机号和密码
    String mobile = member.getMobile();
    String password = member.getPassword();
    //2.先验证手机号和密码是否为空
    if(StringUtils.isNullOrEmpty(mobile)||StringUtils.isNullOrEmpty(password)){
      throw new GuliException(20001,"手机号或者密码为空!");
    }
    //3.手机号是否正确
    //3.1构造条件
    QueryWrapper<UcenterMember> qw=new QueryWrapper<>();
    qw.eq("mobile",mobile);
    //3.2查询对象
    UcenterMember member1 = baseMapper.selectOne(qw);
    if(member1==null){
      throw new GuliException(20001,"手机号不正确!");
    }

    //4.密码是否正确
    if(!member1.getPassword().equals(MD5.encrypt(password))){
      throw new GuliException(20001,"密码不正确!");
    }
    //5.判断用户是否禁用
    if(member1.getIsDisabled()){
      throw new GuliException(20001,"用户已被禁用");
    }

   //6.都没错的话，返回token
   return JwtUtils.getJwtToken(member1.getId(), member1.getNickname());

  }
  //注册的方法
  @Override
  public Boolean regist(RegisterVo registerVo) {
    //1.获取手机验证码...
    String code = registerVo.getCode();
    String mobile = registerVo.getMobile();
    String password = registerVo.getPassword();
    String nickname = registerVo.getNickname();

    //2.判断非空
    if(StringUtils.isNullOrEmpty(code)||StringUtils.isNullOrEmpty(mobile)||StringUtils.isNullOrEmpty(password)||StringUtils.isNullOrEmpty(nickname)){
      throw new GuliException(20001,"验证码/手机号/密码/昵称为空!");
    }
    //3.判断手机验证码是否正确
    String redisCode= redisTemplate.opsForValue().get(mobile);
    if(!code.equals(redisCode)){
      throw new GuliException(20001,"验证码不一致");
    }
    //4.判断手机号是否重复
    QueryWrapper<UcenterMember> qw=new QueryWrapper<>();
    qw.eq("mobile",mobile);
    Integer integer = baseMapper.selectCount(qw);
    if(integer>=1){
      throw new GuliException(20001,"有相同手机号");
    }


    //5.判断昵称是否重复
    qw.eq("nickname", nickname);
    Integer integer1 = baseMapper.selectCount(qw);
    if(integer1>=1){
      throw new GuliException(20001,"有相同昵称");
    }

    //6.都正确，执行添加语句
    UcenterMember member = new UcenterMember();
    member.setMobile(mobile);
    member.setPassword(MD5.encrypt(password));
    member.setNickname(nickname);
    member.setIsDisabled(false);
    member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
    member.setAge(1);
    return  baseMapper.insert(member)>0;

  }
  //根据openid查询对象
  @Override
  public UcenterMember getByOpenId(String openid) {
    QueryWrapper<UcenterMember>qw=new QueryWrapper<>();
    qw.eq("openid", openid);
    return baseMapper.selectOne(qw);
  }

  @Override
  public Integer countRegistVo(String data) {
    return baseMapper.registerDay(data);
  }

}
