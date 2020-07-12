package org.zipper.helper.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.zipper.helper.auth.entity.Member;
import org.zipper.helper.auth.service.IMemberService;
import org.zipper.helper.auth.service.IOrgMemberRelationService;
import org.zipper.helper.auth.service.IOrgMemberRoleRelationService;
import org.zipper.helper.exception.ErrorCode;
import org.zipper.helper.exception.HelperException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @param <T> 用户实体类型
 * @param <M> baseMapper
 * @author zhuxj
 * @since 2020/07/12
 */
public abstract class AbstractMemberServiceImpl<T extends Member, M extends BaseMapper<T>> extends ServiceImpl<M, T> implements IMemberService<T> {


    @Autowired
    IOrgMemberRoleRelationService orgMemberRoleRelationService;

    @Autowired
    IOrgMemberRelationService orgMemberRelationService;

    @Override
    public boolean save(T member) {
        if (member == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "不可新增空的用户");
        }
        member.setCreateTime(LocalDateTime.now());
        member.setUpdateTime(LocalDateTime.now());
        member.setStatus(0);
        return super.save(member);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        UpdateWrapper<T> uw = new UpdateWrapper<>();
        uw.eq("id", id).set("status", 1).set("update_time", LocalDateTime.now());
        orgMemberRoleRelationService.deleteByMemberId(id);
        orgMemberRelationService.deleteByMemberId(id);
        return super.update(uw);
    }

    @Override
    @Transactional
    public boolean delete(Collection<Long> ids) {
        UpdateWrapper<T> uw = new UpdateWrapper<>();
        uw.in("id", ids).set("status", 1).set("update_time", LocalDateTime.now());
        orgMemberRoleRelationService.deleteInMemberIds(ids);
        orgMemberRelationService.deleteInMemberIds(ids);
        return super.update(uw);
    }

    @Override
    public boolean updateById(T entity) {
        if (entity == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "不可以更新空的用户");
        }
        if (entity.getId() == null) {
            throw HelperException.newException(ErrorCode.PARAMETER_ERROR, "非法的用户编号");
        }
        entity.setUpdateTime(LocalDateTime.now());
        return super.updateById(entity);
    }

    @Override
    public List<T> list() {
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.eq("status", 0);
        return super.list(qw);
    }

}
