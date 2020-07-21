package org.zipper.helper.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.zipper.helper.auth.dto.TreeNode;
import org.zipper.helper.auth.entity.Organization;
import org.zipper.helper.auth.service.IOrgMemberRelationService;
import org.zipper.helper.auth.service.IOrgMemberRoleRelationService;
import org.zipper.helper.auth.service.IOrganizationService;
import org.zipper.helper.exception.ErrorCode;
import org.zipper.helper.exception.HelperException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractOrganizationServiceImpl<T extends Organization, M extends BaseMapper<T>> extends ServiceImpl<M, T> implements IOrganizationService<T> {

    @Autowired
    IOrgMemberRelationService orgMemberRelationService;
    @Autowired
    IOrgMemberRoleRelationService orgMemberRoleRelationService;

    @Override
    @Transactional
    public boolean save(T org) {
        if (org == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "不允许添加空记录");
        }
        org.setCreateTime(LocalDateTime.now());
        org.setUpdateTime(LocalDateTime.now());
        org.setStatus(0);
        super.save(org);

        if (org.getPid().equals(0L)) {
            org.setCode("0/" + org.getId());
        } else {
            T parent = getById(org.getPid());
            org.setCode(parent.getCode() + "/" + org.getId());
        }
        return super.updateById(org);
    }

    @Override
    public boolean delete(Long id) {
        if (orgMemberRelationService.existMember(id)) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "当前组织下存在用户，不允许删除");
        }
        UpdateWrapper<T> uw = new UpdateWrapper<>();
        uw.eq("id", id)
                .set("status", 1)
                .set("update_time", LocalDateTime.now());
        return super.update(uw);
    }

    @Override
    public boolean delete(Collection<Long> ids) {
        if (orgMemberRelationService.existMembers(ids)) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "某些组织下存在用户，不允许删除");
        }
        UpdateWrapper<T> uw = new UpdateWrapper<>();
        uw.in("id", ids)
                .set("status", 1)
                .set("update_time", LocalDateTime.now());
        return super.update(uw);
    }

    @Override
    public boolean updateById(T org) {
        if (org == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "部门不可更新为空");
        }
        if (org.getId() == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "部门编号不合法");
        }
        org.setUpdateTime(LocalDateTime.now());
        return super.updateById(org);
    }

    @Override
    public List<T> getAllChildren(Long id) {
        if (id == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "部门编号不合法");
        }
        T menu = getById(id);
        String code = menu.getCode();
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.eq("status", 0).like("code", code + "/%");
        return super.list(qw);
    }

    @Override
    public T getById(Serializable id) {
        if (id == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "部门编号不合法");
        }
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.eq("status", 0).eq("id", id);

        T menu = super.getOne(qw);
        if (menu == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "目标部门不存在");
        }
        return menu;
    }

    @Override
    public T getParent(Long id) {
        if (id == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "部门编号不合法");
        }
        T parent = this.getById(this.getById(id).getPid());
        if (parent == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "目标部门不存在");
        }
        return parent;
    }

    @Override
    public List<T> getChildren(Long pid) {
        if (pid == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "菜单或功能编号不合法");
        }
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.eq("pid", pid).eq("status", 0);
        return super.list(qw);
    }

    @Override
    public List<T> list() {
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.eq("status", 0);
        return super.list(qw);
    }

    @Override
    public List<T> getList(Long roleId) {
        if (roleId == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "角色编号不合法");
        }
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.eq("status", 0);
        return super.list(qw);
    }

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

}
