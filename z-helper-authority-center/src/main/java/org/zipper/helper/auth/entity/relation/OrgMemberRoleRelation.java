package org.zipper.helper.auth.entity.relation;


/**
 * 组织-用户-角色关联表
 */
public class OrgMemberRoleRelation {

    /**
     * 组织ID
     */
    private Long orgId;
    /**
     * 用户ID
     */
    private Long memberId;
    /**
     * 角色ID
     */
    private Long roleId;

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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
