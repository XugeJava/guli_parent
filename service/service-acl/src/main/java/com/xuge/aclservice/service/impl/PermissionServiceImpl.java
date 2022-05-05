package com.xuge.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xuge.aclservice.entity.Permission;
import com.xuge.aclservice.entity.RolePermission;
import com.xuge.aclservice.entity.User;
import com.xuge.aclservice.helper.MemuHelper;
import com.xuge.aclservice.helper.PermissionHelper;
import com.xuge.aclservice.mapper.PermissionMapper;
import com.xuge.aclservice.service.PermissionService;
import com.xuge.aclservice.service.RolePermissionService;
import com.xuge.aclservice.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.util.internal.SuppressJava6Requirement;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
@SuppressWarnings("all")
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

  @Autowired
  private RolePermissionService rolePermissionService;

  @Autowired
  private UserService userService;

  //获取全部菜单
  @Override
  public List<Permission> queryAllMenu() {

    QueryWrapper<Permission> wrapper = new QueryWrapper<>();
    wrapper.orderByDesc("id");
    List<Permission> permissionList = baseMapper.selectList(wrapper);

    List<Permission> result = bulid(permissionList);

    return result;
  }

  //根据角色获取菜单
  @Override
  public List<Permission> selectAllMenu(String roleId) {
    List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

    //根据角色id获取角色权限
    List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id", roleId));
    //转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
    for (int i = 0; i < allPermissionList.size(); i++) {
      Permission permission = allPermissionList.get(i);
      for (int m = 0; m < rolePermissionList.size(); m++) {
        RolePermission rolePermission = rolePermissionList.get(m);
        if (rolePermission.getPermissionId().equals(permission.getId())) {
          permission.setSelect(true);
        }
      }
    }


    List<Permission> permissionList = bulid(allPermissionList);
    return permissionList;
  }

  //给角色分配权限
  @Override
  public void saveRolePermissionRealtionShip(String roleId, String[] permissionIds) {

    rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id", roleId));


    List<RolePermission> rolePermissionList = new ArrayList<>();
    for (String permissionId : permissionIds) {
      if (StringUtils.isEmpty(permissionId)) continue;

      RolePermission rolePermission = new RolePermission();
      rolePermission.setRoleId(roleId);
      rolePermission.setPermissionId(permissionId);
      rolePermissionList.add(rolePermission);
    }
    rolePermissionService.saveBatch(rolePermissionList);
  }

  //递归删除菜单
  @Override
  public void removeChildById(String id) {
    List<String> idList = new ArrayList<>();
    this.selectChildListById(id, idList);

    idList.add(id);
    baseMapper.deleteBatchIds(idList);
  }

  //根据用户id获取用户菜单
  @Override
  public List<String> selectPermissionValueByUserId(String id) {

    List<String> selectPermissionValueList = null;
    if (this.isSysAdmin(id)) {
      //如果是系统管理员，获取所有权限
      selectPermissionValueList = baseMapper.selectAllPermissionValue();
    } else {
      selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
    }
    return selectPermissionValueList;
  }

  @Override
  public List<JSONObject> selectPermissionByUserId(String userId) {
    List<Permission> selectPermissionList = null;
    if (this.isSysAdmin(userId)) {
      //如果是超级管理员，获取所有菜单
      selectPermissionList = baseMapper.selectList(null);
    } else {
      selectPermissionList = baseMapper.selectPermissionByUserId(userId);
    }

    List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
    List<JSONObject> result = MemuHelper.bulid(permissionList);
    return result;
  }

  /**
   * 判断用户是否系统管理员
   *
   * @param userId
   * @return
   */
  private boolean isSysAdmin(String userId) {
    User user = userService.getById(userId);

    if (null != user && "admin".equals(user.getUsername())) {
      return true;
    }
    return false;
  }

  /**
   * 递归获取子节点
   *
   * @param id
   * @param idList
   */
  private void selectChildListById(String id, List<String> idList) {
    List<Permission> childList = baseMapper.selectList(new QueryWrapper<Permission>().eq("pid", id).select("id"));
    childList.stream().forEach(item -> {
      idList.add(item.getId());
      this.selectChildListById(item.getId(), idList);
    });
  }

  /**
   * 使用递归方法建菜单
   *
   * @param treeNodes
   * @return
   */
  private static List<Permission> bulid(List<Permission> treeNodes) {
    List<Permission> trees = new ArrayList<>();
    for (Permission treeNode : treeNodes) {
      if ("0".equals(treeNode.getPid())) {
        treeNode.setLevel(1);
        trees.add(findChildren(treeNode, treeNodes));
      }
    }
    return trees;
  }

  /**
   * 递归查找子节点
   *
   * @param treeNodes
   * @return
   */
  private static Permission findChildren(Permission treeNode, List<Permission> treeNodes) {
    treeNode.setChildren(new ArrayList<Permission>());

    for (Permission it : treeNodes) {
      if (treeNode.getId().equals(it.getPid())) {
        int level = treeNode.getLevel() + 1;
        it.setLevel(level);
        if (treeNode.getChildren() == null) {
          treeNode.setChildren(new ArrayList<>());
        }
        treeNode.getChildren().add(findChildren(it, treeNodes));
      }
    }
    return treeNode;
  }


  //========================递归查询所有菜单================================================
  //获取全部菜单
  @Override
  public List<Permission> queryAllMenuGuli() {
    //1 查询菜单表所有数据
    QueryWrapper<Permission> wrapper = new QueryWrapper<>();
    wrapper.orderByDesc("id");
    List<Permission> permissionList = baseMapper.selectList(wrapper);
    //2 把查询所有菜单list集合按照要求进行封装
    List<Permission> resultList = bulidPermission(permissionList);
    return resultList;
  }

  //把返回所有菜单list集合进行封装的方法
  public static List<Permission> bulidPermission(List<Permission> permissionList) {

    //创建list集合，用于数据最终封装
    List<Permission> finalNode = new ArrayList<>();
    //把所有菜单list集合遍历，得到顶层菜单 pid=0菜单，设置level是1
    for (Permission permissionNode : permissionList) {
      //得到顶层菜单 pid=0菜单
      if ("0".equals(permissionNode.getPid())) {
        //设置顶层菜单的level是1
        permissionNode.setLevel(1);
        //根据顶层菜单，向里面进行查询子菜单，封装到finalNode里面
        finalNode.add(selectChildren(permissionNode, permissionList));
      }
    }
    return finalNode;
  }

  private static Permission selectChildren(Permission permissionNode, List<Permission> permissionList) {
    //1 因为向一层菜单里面放二层菜单，二层里面还要放三层，把对象初始化
    permissionNode.setChildren(new ArrayList<Permission>());

    //2 遍历所有菜单list集合，进行判断比较，比较id和pid值是否相同
    for (Permission it : permissionList) {
      //判断 id和pid值是否相同
      if (permissionNode.getId().equals(it.getPid())) {
        //把父菜单的level值+1
        int level = permissionNode.getLevel() + 1;
        it.setLevel(level);
        //如果children为空，进行初始化操作
        if (permissionNode.getChildren() == null) {
          permissionNode.setChildren(new ArrayList<Permission>());
        }
        //把查询出来的子菜单放到父菜单里面
        permissionNode.getChildren().add(selectChildren(it, permissionList));
      }
    }
    return permissionNode;
  }

  //============递归删除菜单==================================
  @Override
  public void removeChildByIdGuli(String id) {
    //1 创建list集合，用于封装所有删除菜单id值
    List<String> idList = new ArrayList<>();
    //2 向idList集合设置删除菜单id
    this.selectPermissionChildById(id, idList);
    //把当前id封装到list里面
    idList.add(id);
    baseMapper.deleteBatchIds(idList);
  }

  //2 根据当前菜单id，查询菜单里面子菜单id，封装到list集合
  private void selectPermissionChildById(String id, List<String> idList) {
    //查询菜单里面子菜单id
    QueryWrapper<Permission> wrapper = new QueryWrapper<>();
    wrapper.eq("pid", id);
    wrapper.select("id");
    List<Permission> childIdList = baseMapper.selectList(wrapper);
    //把childIdList里面菜单id值获取出来，封装idList里面，做递归查询
    childIdList.stream().forEach(item -> {
      //封装idList里面
      idList.add(item.getId());
      //递归查询
      this.selectPermissionChildById(item.getId(), idList);
    });
  }

  //=========================给角色分配菜单=======================
  @Override
  public void saveRolePermissionRealtionShipGuli(String roleId, String[] permissionIds) {
    //roleId角色id
    //permissionId菜单id 数组形式
    //1 创建list集合，用于封装添加数据
    List<RolePermission> rolePermissionList = new ArrayList<>();
    //遍历所有菜单数组
    for (String perId : permissionIds) {
      //RolePermission对象
      RolePermission rolePermission = new RolePermission();
      rolePermission.setRoleId(roleId);
      rolePermission.setPermissionId(perId);
      //封装到list集合
      rolePermissionList.add(rolePermission);
    }
    //添加到角色菜单关系表
    rolePermissionService.saveBatch(rolePermissionList);
  }
  //====================================================


  //=============================xuge写的
  @Override
  public List<Permission> queryAllMenuXuge() {
    //1.先查询菜单表中的所有数据
    QueryWrapper<Permission> qw = new QueryWrapper<>();
    qw.orderByDesc("id");
    List<Permission> list = baseMapper.selectList(qw);

    //2.把所有查询出来的数据进行封装
    return buildPermissionXuge(list);

  }

  //============递归删除菜单=============
  @Override
  public void removeChildByIdXuge(String id) {
    //1.创建一个list集合
    List<String> ids = new ArrayList<>();
    //2.设置要删除的id,执行封装
    this.selectChildListByIdXuge(id, ids);
    //3.封装传进来的id
    ids.add(id);
    baseMapper.deleteBatchIds(ids);
  }

  //具体封装==根据当前id，查询/选择要删除子菜单id
  private void selectChildListByIdXuge(String id, List<String> ids) {
    //1.查询菜单里面子菜单id
    QueryWrapper<Permission> qw = new QueryWrapper<>();
    qw.eq("pid", id);
    qw.select("id");

    List<Permission> list = baseMapper.selectList(qw);

    //2.把list中里面的菜单id值取出来，封装到ids中
    list.stream().forEach(item -> {
      //1.封装到ids中
      ids.add(item.getId());
      //2.递归查询
      this.selectChildListByIdXuge(item.getId(), ids);
    });


  }
  //------------------------------------------


  //给角色分配菜单=============================

  @Override
  public void saveRolePermissionRealtionShipXueg(String roleId, String[] permissionId) {
    //roleId角色id
    //permissionId 菜单id

    //1.创建list集合，用于封装添加数据
    List<RolePermission> list=new ArrayList<>();

    //2.遍历所以菜单数组
    for (String pid : permissionId) {
      //封装RolePermission对象
      RolePermission rp = new RolePermission();
      rp.setRoleId(roleId);
      rp.setPermissionId(pid);

      //放入list集合中
      list.add(rp);


    }
    //执行角色分配菜单
    rolePermissionService.saveBatch(list);


  }


  //======================================


  //执行递归封装==============查询所有菜单===================
  private List<Permission> buildPermissionXuge(List<Permission> list) {
    //1.创建list集合，用于数据最终封装
    List<Permission> finalList = new ArrayList<>();
    //2.把所有菜单数据进行遍历,得到顶层菜单,pid=0,设置level=1
    for (Permission permission : list) {
      if ("0".equals(permission.getPid())) {
        //1.设置顶层level=1
        permission.setLevel(1);
        //2.根据顶层菜单，查询子菜单，封装到finalList中去
        finalList.add(selectChildrenXuge(permission, list));

      }
    }
    return finalList;


  }

  private Permission selectChildrenXuge(Permission permission, List<Permission> list) {
    //1.因为一级菜单要放二级菜单，二级同理
    permission.setChildren(new ArrayList<Permission>());
    //2.遍历所有菜单list集合
    for (Permission it : list) {
      //1.判断一级节点id和二级节点pid是否相同
      if (permission.getId().equals(it.getPid())) {
        //2.把父菜单的level加1
        int level = permission.getLevel() + 1;
        it.setLevel(level);
        //如果permission为空
        if (permission.getChildren() == null) {
          permission.setChildren(new ArrayList<Permission>());
        }
        //3.把查询的子菜单放到父菜单里面去
        permission.getChildren().add(selectChildrenXuge(it, list));
      }
    }


    return permission;


  }
  //================================================


}
