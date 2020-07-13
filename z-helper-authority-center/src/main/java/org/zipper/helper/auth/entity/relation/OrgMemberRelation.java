package org.zipper.helper.auth.entity.relation;


import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 组织用户关联表
 * <p>
 * 考虑常见组织出现一个人员存在多个组织节点下的问题
 * <p>
 * 所以不将关联关系维护在用户表里
 *
 * @author zhuxj
 * @since 2020/07/07
 */
@TableName("tb_auth_relation_org_member")
public class OrgMemberRelation {

    /**
     * 组织ID
     */
    private Long orgId;
    /**
     * 用户ID
     */
    private Long memberId;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
