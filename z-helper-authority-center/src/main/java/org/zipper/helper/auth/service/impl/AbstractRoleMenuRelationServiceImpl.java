package org.zipper.helper.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.zipper.helper.auth.entity.relation.RoleMenuRelation;
import org.zipper.helper.auth.service.IRoleMenuRelationService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @param <T> mapper
 * @author zhuxj
 * @since 2020/07/11
 */
public abstract class AbstractRoleMenuRelationServiceImpl<T extends BaseMapper<RoleMenuRelation>> extends ServiceImpl<T, RoleMenuRelation> implements IRoleMenuRelationService {
    @Override
    public List<Long> getMenuIds(Long roleId) {
        QueryWrapper<RoleMenuRelation> qw = new QueryWrapper<>();
        qw.lambda().select(RoleMenuRelation::getMenuId).eq(RoleMenuRelation::getRoleId, roleId);
        return super.list(qw).stream().map(RoleMenuRelation::getMenuId).collect(Collectors.toList());
    }

    @Override
    public List<Long> getMenuIdsIn(Collection<Long> roleIds) {
        QueryWrapper<RoleMenuRelation> qw = new QueryWrapper<>();
        qw.lambda().select(RoleMenuRelation::getMenuId).in(RoleMenuRelation::getRoleId, roleIds);
        return super.list(qw).stream().map(RoleMenuRelation::getMenuId).collect(Collectors.toList());
    }

    @Override
    public List<Long> getRoleIds(Long menuId) {
        QueryWrapper<RoleMenuRelation> qw = new QueryWrapper<>();
        qw.lambda().select(RoleMenuRelation::getRoleId).eq(RoleMenuRelation::getMenuId, menuId);
        return super.list(qw).stream().map(RoleMenuRelation::getRoleId).collect(Collectors.toList());
    }

    @Override
    public List<Long> getRoleIdsIn(Collection<Long> menuIds) {

        QueryWrapper<RoleMenuRelation> qw = new QueryWrapper<>();
        qw.lambda().select(RoleMenuRelation::getRoleId).in(RoleMenuRelation::getMenuId, menuIds);
        return super.list(qw).stream().map(RoleMenuRelation::getRoleId).collect(Collectors.toList());
    }

    @Override
    public boolean deleteByRoleId(Long roleId) {
        QueryWrapper<RoleMenuRelation> qw = new QueryWrapper<>();
        qw.lambda().eq(RoleMenuRelation::getRoleId, roleId);
        return super.remove(qw);
    }

    @Override
    public boolean deleteInRoleIds(Collection<Long> roleIds) {
        QueryWrapper<RoleMenuRelation> qw = new QueryWrapper<>();
        qw.lambda().in(RoleMenuRelation::getRoleId, roleIds);
        return super.remove(qw);
    }

    @Override
    public boolean deleteByMenuId(Long menuId) {
        QueryWrapper<RoleMenuRelation> qw = new QueryWrapper<>();
        qw.lambda().eq(RoleMenuRelation::getMenuId, menuId);
        return super.remove(qw);
    }

    @Override
    public boolean deleteInMenuIds(Collection<Long> menuIds) {
        QueryWrapper<RoleMenuRelation> qw = new QueryWrapper<>();
        qw.lambda().in(RoleMenuRelation::getMenuId, menuIds);
        return super.remove(qw);
    }

    @Override
    public boolean add(List<RoleMenuRelation> relations) {
        return super.saveBatch(relations);
    }
}
