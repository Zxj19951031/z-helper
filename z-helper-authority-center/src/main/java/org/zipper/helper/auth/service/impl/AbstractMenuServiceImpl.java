package org.zipper.helper.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.zipper.helper.auth.dto.TreeNode;
import org.zipper.helper.auth.entity.Menu;
import org.zipper.helper.auth.service.IMenuService;
import org.zipper.helper.auth.service.IRoleMenuRelationService;
import org.zipper.helper.auth.service.IRoleService;
import org.zipper.helper.exception.ErrorCode;
import org.zipper.helper.exception.HelperException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 菜单功能服务层的抽象实现
 *
 * @param <T> 菜单实体
 * @param <M> 菜单功能Mapper接口
 */
public abstract class AbstractMenuServiceImpl<T extends Menu, M extends BaseMapper<T>> extends ServiceImpl<M, T> implements IMenuService<T> {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRoleMenuRelationService roleMenuRelationService;

    /**
     * 保存一个功能菜单，不可新增空对象，会对code字段进行更新方便后期查询
     *
     * @param menu 菜单功能
     * @return 成功 true，失败false
     */
    @Override
    @Transactional
    public boolean save(T menu) {
        if (menu == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "不允许添加空记录");
        }
        menu.setCreateTime(LocalDateTime.now());
        menu.setUpdateTime(LocalDateTime.now());
        menu.setStatus(0);
        super.save(menu);

        if (menu.getPid().equals(0L))
            menu.setCode("0/" + menu.getId());
        else {
            T parent = getById(menu.getPid());
            menu.setCode(parent.getCode() + "/" + menu.getId());
        }
        return this.updateById(menu);
    }

    /**
     * 删除一个菜单或功能，同时删除角色和菜单功能的关联关系
     *
     * @param id 菜单ID
     * @return 成功 true，失败false
     */
    @Override
    @Transactional
    public boolean delete(Long id) {
        UpdateWrapper<T> uw = new UpdateWrapper<>();
        uw.eq("id", id)
                .set("status", 1)
                .set("update_time", LocalDateTime.now());
        roleMenuRelationService.deleteByMenuId(id);
        return super.update(uw);
    }

    /**
     * 删除一组菜单或功能，同时删除角色和菜单功能的关联关系
     *
     * @param ids 菜单IDs
     * @return 成功 true，失败false
     */
    @Override
    @Transactional
    public boolean delete(Collection<Long> ids) {
        UpdateWrapper<T> uw = new UpdateWrapper<>();
        uw.in("id", ids)
                .set("status", 1)
                .set("update_time", LocalDateTime.now());
        roleMenuRelationService.deleteInMenuIds(ids);
        return super.update(uw);
    }

    /**
     * 更新一个菜单功能，不接收空对象，不接收id为空的对象
     *
     * @param menu 菜单
     * @return 成功 true，失败false
     */
    @Override
    public boolean updateById(T menu) {
        if (menu == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "菜单或功能不可更新为空");
        }
        if (menu.getId() == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "菜单或功能编号不合法");
        }
        menu.setUpdateTime(LocalDateTime.now());
        return super.updateById(menu);
    }

    /**
     * 获取所有子孙节点信息，采用code字段进行快速查询，替换递归方法
     * code规则：code其实就是该节点在树中的路径
     *
     * @param id 菜单或功能ID
     * @return 所有子孙节点详情
     */
    @Override
    public List<T> getAllChildren(Long id) {
        if (id == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "菜单或功能编号不合法");
        }
        T menu = getById(id);
        String code = menu.getCode();
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.eq("status", 0).like("code", code + "/%");
        return super.list(qw);
    }

    /**
     * 获取单个菜单或权限记录，ID不允许为空，对结果会进行空值判断
     *
     * @param id 记录ID
     * @return 一个菜单或功能实体
     */
    @Override
    public T getById(Serializable id) {
        if (id == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "菜单或功能编号不合法");
        }
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.eq("status", 0).eq("id", id);

        T menu = super.getOne(qw);
        if (menu == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "目标菜单或功能不存在");
        }
        return menu;
    }

    /**
     * 获取父亲菜单
     *
     * @param id 菜单或功能ID
     * @return 父亲节点
     */
    @Override
    public T getParent(Long id) {
        if (id == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "菜单或功能编号不合法");
        }
        T parent = this.getById(this.getById(id).getPid());
        if (parent == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "目标菜单或功能不存在");
        }
        return parent;
    }

    /**
     * 获取子菜单或功能列表
     *
     * @param pid 父亲节点ID
     * @return 所有直属子节点
     */
    @Override
    public List<T> getChildren(Long pid) {
        if (pid == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "菜单或功能编号不合法");
        }
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.eq("pid", pid).eq("status", 0);
        return super.list(qw);
    }

    /**
     * 获取所有菜单，返回带有树结构
     *
     * @return 整颗菜单或功能树
     */
    @Override
    public List<T> list() {
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.eq("status", 0);
        return super.list(qw);
    }

    /**
     * 获取某个角色授权的所有菜单或功能，返回带有列表结构
     *
     * @param roleId 角色ID
     * @return 某个角色的菜单或功能列表
     */
    @Override
    @Transactional
    public List<T> getList(Long roleId) {
        if (roleId == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "角色编号不合法");
        }
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.eq("status", 0).in("id", roleMenuRelationService.getMenuIds(roleId));
        return super.list(qw);
    }

    /**
     * 获取树结构菜单
     *
     * @return 整颗树结构菜单
     */
    @Override
    public List<TreeNode<T>> getTree() {
        List<TreeNode<T>> treeList = new ArrayList<>();
        this.list().forEach(node -> {
            TreeNode<T> treeNode = new TreeNode<>();
            treeNode.setNode(node);
            treeList.add(treeNode);
        });
        return TreeNode.parseTree(treeList);
    }

    /**
     * 获取某个角色的树结构菜单
     *
     * @param roleId 角色ID
     * @return 属于该角色一部分树结构菜单
     */
    @Override
    public List<TreeNode<T>> getTree(Long roleId) {
        List<TreeNode<T>> treeList = new ArrayList<>();
        this.getList(roleId).forEach(node -> {
            TreeNode<T> treeNode = new TreeNode<>();
            treeNode.setNode(node);
            treeList.add(treeNode);
        });
        return TreeNode.parseTree(treeList);
    }


}
