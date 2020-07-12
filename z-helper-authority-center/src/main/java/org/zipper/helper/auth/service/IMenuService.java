package org.zipper.helper.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zipper.helper.auth.dto.TreeNode;
import org.zipper.helper.auth.entity.Menu;

import java.util.Collection;
import java.util.List;

/**
 * 菜单管理
 *
 * @param <T> 菜单实体
 * @author zhuxj
 * @since 2020/07/07
 */
public interface IMenuService<T extends Menu> extends IService<T> {

    /**
     * 逻辑删除某一条菜单
     *
     * @param id 菜单ID
     * @return 删除的菜单数量
     */
    public boolean delete(Long id);

    /**
     * 逻辑删除一些菜单
     *
     * @param ids 菜单IDs
     * @return 删除的菜单数量
     */
    public boolean delete(Collection<Long> ids);

    /**
     * 获取某个菜单下的所有子孙菜单或功能ID
     *
     * @param id 菜单或功能ID
     * @return 子孙菜单或功能ID
     */
    public List<T> getAllChildren(Long id);

    /**
     * 查询父节点详情
     *
     * @param id 菜单或功能ID
     * @return 父级详情
     */
    public T getParent(Long id);

    /**
     * 查询子节点
     *
     * @param pid 父亲节点
     * @return 子节点
     */
    List<T> getChildren(Long pid);

    /**
     * 查询某个角色的菜单功能列表
     *
     * @param roleId 角色ID
     * @return 带有子节点信息的菜单功能树
     */
    List<T> getList(Long roleId);


    /**
     * 查询菜单树结构
     *
     * @return 树结构菜单或功能
     */
    List<TreeNode<T>> getTree();

    /**
     * 查询某个角色的菜单树结构
     *
     * @param roleId 角色ID
     * @return 树结构菜单或功能
     */
    List<TreeNode<T>> getTree(Long roleId);
}
