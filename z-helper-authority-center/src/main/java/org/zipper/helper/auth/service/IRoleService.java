package org.zipper.helper.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zipper.helper.auth.entity.Role;

import java.util.Collection;
import java.util.List;

/**
 * @param <T> 角色类
 * @author zhuxj
 * @since 2020/07/11
 */
public interface IRoleService<T extends Role> extends IService<T> {

    /**
     * 逻辑删除某一条角色
     *
     * @param id 角色ID
     * @return 删除结果
     */
    public boolean delete(Long id);

    /**
     * 逻辑删除一些角色
     *
     * @param ids 角色IDs
     * @return 删除结果
     */
    public boolean delete(Collection<Long> ids);

    /**
     * 条件查询角色列表,框架里没有实现该方法
     * 原先考虑设置参数为继承至T的类型，但是查询也只能做到对Role的字段查询
     * 所以就开放给外部去实现了
     *
     * @param parameter 查询参数
     * @param <M>       参数类型Dto，拥有表内所有字段的查询条件，可以补充其他参数
     * @return 角色列表
     */
    <M> List<T> getList(M parameter);
}
