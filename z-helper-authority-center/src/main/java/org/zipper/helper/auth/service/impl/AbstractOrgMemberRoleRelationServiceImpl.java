package org.zipper.helper.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.zipper.helper.auth.entity.relation.OrgMemberRoleRelation;
import org.zipper.helper.auth.service.IOrgMemberRoleRelationService;

import java.util.Collection;

/**
 * @param <M>
 * @author zhuxj
 * @since 2020/07/12
 */
public abstract class AbstractOrgMemberRoleRelationServiceImpl<M extends BaseMapper<OrgMemberRoleRelation>> extends ServiceImpl<M, OrgMemberRoleRelation> implements IOrgMemberRoleRelationService {

    @Override
    public boolean existRole(Long roleId) {
        QueryWrapper<OrgMemberRoleRelation> qw = new QueryWrapper<>();
        qw.lambda().eq(OrgMemberRoleRelation::getRoleId, roleId);
        return super.getOne(qw) != null;
    }

    @Override
    public boolean existRoles(Collection<Long> roleIds) {
        QueryWrapper<OrgMemberRoleRelation> qw = new QueryWrapper<>();
        qw.lambda().in(OrgMemberRoleRelation::getRoleId, roleIds);
        return super.getOne(qw) != null;
    }

    @Override
    public boolean deleteByMemberId(Long memberId) {
        QueryWrapper<OrgMemberRoleRelation> qw = new QueryWrapper<>();
        qw.lambda().eq(OrgMemberRoleRelation::getMemberId, memberId);
        return super.remove(qw);
    }

    @Override
    public boolean deleteInMemberIds(Collection<Long> memberIds) {
        QueryWrapper<OrgMemberRoleRelation> qw = new QueryWrapper<>();
        qw.lambda().in(OrgMemberRoleRelation::getMemberId, memberIds);
        return super.remove(qw);
    }
}
