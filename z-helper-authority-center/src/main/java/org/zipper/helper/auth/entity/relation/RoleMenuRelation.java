package org.zipper.helper.auth.entity.relation;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 角色-菜单关联表
 *
 * @author zhuxj
 * @since 2020/07/06
 */
@TableName("tb_auth_relation_role_menu")
public class RoleMenuRelation {
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单资源ID
     */
    private Long menuId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
