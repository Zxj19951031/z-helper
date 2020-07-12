package org.zipper.helper.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zipper.helper.auth.entity.relation.OrgMemberRoleRelation;

import java.util.Collection;

/**
 * 部门——用户——角色关联表
 *
 * @author zhuxj
 * @since 2020/07/12
 */
public interface IOrgMemberRoleRelationService extends IService<OrgMemberRoleRelation> {
    /**
     * 判断某个角色是否存在和用户的关联关系
     *
     * @param roleId 角色编号
     * @return 存在？true: false
     */
    boolean existRole(Long roleId);

    /**
     * 判断某些角色是否存在和用户的关联关系
     *
     * @param roleIds 角色编号
     * @return 存在？true: false
     */
    boolean existRoles(Collection<Long> roleIds);

    /**
     * 通过用户ID删除他的部门角色关联
     *
     * @param memberId 用户ID
     * @return 成功？true: false
     */
    boolean deleteByMemberId(Long memberId);


    /**
     * 通过一组用户编号删除他们的部门角色关联关系
     *
     * @param memberIds 用户编号
     * @return 成功？true: false
     */
    boolean deleteInMemberIds(Collection<Long> memberIds);
}
