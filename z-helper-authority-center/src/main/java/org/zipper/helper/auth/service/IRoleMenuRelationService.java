package org.zipper.helper.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zipper.helper.auth.entity.relation.RoleMenuRelation;

import java.util.Collection;
import java.util.List;

/**
 * 角色菜单功能关联关系表
 *
 * @author zhuxj
 * @since 2020/07/11
 */
public interface IRoleMenuRelationService extends IService<RoleMenuRelation> {
    /**
     * 获取某个角色所关联的菜单编号
     *
     * @param roleId 角色编号
     * @return 菜单编号列表
     */
    public List<Long> getMenuIds(Long roleId);

    /**
     * 获取某一批角色所关联的菜单编号
     *
     * @param roleIds 角色编号集合
     * @return 菜单编号
     */
    public List<Long> getMenuIdsIn(Collection<Long> roleIds);

    /**
     * 获取某个菜单所关联的角色编号
     *
     * @param menuId 菜单编号
     * @return 角色编号
     */
    public List<Long> getRoleIds(Long menuId);

    /**
     * 获取某些菜单所关联的角色编号
     *
     * @param menuIds 菜单编号集合
     * @return 角色编号
     */
    public List<Long> getRoleIdsIn(Collection<Long> menuIds);

    /**
     * 删除某个角色的关联关系
     *
     * @param roleId 角色编号
     * @return 成功？true：false
     */
    public boolean deleteByRoleId(Long roleId);

    /**
     * 删除某些角色的关联关系
     *
     * @param roleIds 角色编号
     * @return 成功？true：false
     */
    public boolean deleteInRoleIds(Collection<Long> roleIds);

    /**
     * 删除某个菜单的关联关系
     *
     * @param menuId 菜单编号
     * @return 成功？true：false
     */
    public boolean deleteByMenuId(Long menuId);

    /**
     * 删除某些菜单的关联关系
     *
     * @param menuIds 菜单编号
     * @return 成功？true：false
     */
    public boolean deleteInMenuIds(Collection<Long> menuIds);

    /**
     * 新增一组角色和菜单的关联关系
     *
     * @param relations 一组关联关系
     * @return
     */
    public boolean add(List<RoleMenuRelation> relations);


}
