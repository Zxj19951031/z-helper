package org.zipper.helper.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.zipper.helper.auth.entity.Role;
import org.zipper.helper.auth.service.IOrgMemberRoleRelationService;
import org.zipper.helper.auth.service.IRoleMenuRelationService;
import org.zipper.helper.auth.service.IRoleService;
import org.zipper.helper.exception.ErrorCode;
import org.zipper.helper.exception.HelperException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * 角色管理服务
 *
 * @param <T> 角色子类
 * @param <M> mapper
 */
public abstract class AbstractRoleServiceImpl<T extends Role, M extends BaseMapper<T>> extends ServiceImpl<M, T> implements IRoleService<T> {

    @Autowired
    IRoleMenuRelationService roleMenuRelationService;

    @Autowired
    IOrgMemberRoleRelationService orgMemberRoleRelationService;

    /**
     * 保存新的角色，不接收空对象
     *
     * @param role 角色实体
     * @return 成功？true：false
     */
    @Override
    public boolean save(T role) {
        if (role == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "不可新增空的角色");
        }
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        role.setStatus(0);
        return super.save(role);
    }


    /**
     * 删除某一个角色，删除角色和菜单的关联关系
     * 删除前判断该角色是否存在于部门角色用户关联表,
     * 若存在说明已经有人被赋予了这个角色，那么是不允许删除这个角色的
     *
     * @param id 角色ID
     * @return 成功？true：false
     */
    @Override
    @Transactional
    public boolean delete(Long id) {
        if (orgMemberRoleRelationService.existRole(id)) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "该角色已被赋予用户，不允许执行删除操作");
        }
        UpdateWrapper<T> uw = new UpdateWrapper<>();
        uw.eq("id", id).set("status", 1).set("update_time", LocalDateTime.now());
        roleMenuRelationService.deleteByRoleId(id);
        return super.update(uw);
    }

    /**
     * 删除某一些角色，删除角色和菜单的关联关系
     *
     * @param ids 角色ID
     * @return 成功？true：false
     */
    @Override
    @Transactional
    public boolean delete(Collection<Long> ids) {
        if (orgMemberRoleRelationService.existRoles(ids)) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "一些角色已被赋予用户，不允许执行删除操作");
        }
        UpdateWrapper<T> uw = new UpdateWrapper<>();
        uw.in("id", ids).set("status", 1).set("update_time", LocalDateTime.now());
        roleMenuRelationService.deleteInRoleIds(ids);
        return super.update(uw);
    }

    /**
     * 更新角色类型，不接收空对象，不接收空编号
     *
     * @param menu 角色
     * @return 成功？true：false
     */
    @Override
    public boolean updateById(T menu) {
        if (menu == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "不可更新空的角色");
        }
        if (menu.getId() == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "非法的角色编号");
        }
        menu.setUpdateTime(LocalDateTime.now());
        return super.updateById(menu);
    }

    @Override
    public T getById(Serializable id) {
        if (id == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "角色编号不合法");
        }
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.eq("id", id).eq("status", 0);
        T role = super.getOne(qw);
        if (role == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "目标角色不存在");
        }
        return role;
    }

    @Override
    public List<T> list() {
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.eq("status", 0);
        return super.list(qw);
    }
}
