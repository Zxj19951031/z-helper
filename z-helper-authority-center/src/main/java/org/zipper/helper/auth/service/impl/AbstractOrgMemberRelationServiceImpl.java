package org.zipper.helper.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.zipper.helper.auth.entity.relation.OrgMemberRelation;
import org.zipper.helper.auth.service.IOrgMemberRelationService;

import java.util.Collection;

/**
 * @param <M>
 * @author zhuxj
 * @since 2020/07/12
 */
public abstract class AbstractOrgMemberRelationServiceImpl<M extends BaseMapper<OrgMemberRelation>> extends ServiceImpl<M, OrgMemberRelation> implements IOrgMemberRelationService {

    @Override
    public boolean deleteByMemberId(Long memberId) {
        QueryWrapper<OrgMemberRelation> qw = new QueryWrapper<>();
        qw.lambda().eq(OrgMemberRelation::getMemberId, memberId);
        return super.remove(qw);
    }

    @Override
    public boolean deleteInMemberIds(Collection<Long> memberIds) {
        QueryWrapper<OrgMemberRelation> qw = new QueryWrapper<>();
        qw.lambda().in(OrgMemberRelation::getMemberId, memberIds);
        return super.remove(qw);
    }

    @Override
    public boolean existMember(Long id) {
        QueryWrapper<OrgMemberRelation> qw = new QueryWrapper<>();
        qw.lambda().eq(OrgMemberRelation::getOrgId, id);
        return super.getOne(qw) != null;
    }

    @Override
    public boolean existMembers(Collection<Long> ids) {
        QueryWrapper<OrgMemberRelation> qw = new QueryWrapper<>();
        qw.lambda().in(OrgMemberRelation::getOrgId, ids);
        return super.getOne(qw) != null;
    }
}
