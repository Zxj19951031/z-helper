package org.zipper.helper.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zipper.helper.auth.dto.TreeNode;
import org.zipper.helper.auth.entity.Organization;

import java.util.Collection;
import java.util.List;

/**
 * @param <T>
 * @author zhuxj
 * @since 2020/07/10
 */
public interface IOrganizationService<T extends Organization> extends IService<T> {

    /**
     * 逻辑删除某一部门
     *
     * @param id 部门ID
     * @return 删除的部门数量
     */
    public boolean delete(Long id);

    /**
     * 逻辑删除一些部门
     *
     * @param ids 部门IDs
     * @return 删除的部门数量
     */
    public boolean delete(Collection<Long> ids);

    /**
     * 获取某个部门下的所有子孙部门ID
     *
     * @param id 部门ID
     * @return 子孙部门ID
     */
    public List<T> getAllChildren(Long id);

    /**
     * 查询父节点详情
     *
     * @param id 部门ID
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
     * 查询某个角色的部门功能列表
     *
     * @param roleId 角色ID
     * @return 带有子节点信息的部门功能树
     */
    List<T> getList(Long roleId);


    /**
     * 查询部门树结构
     *
     * @return 树结构部门
     */
    List<TreeNode<T>> getTree();
}
