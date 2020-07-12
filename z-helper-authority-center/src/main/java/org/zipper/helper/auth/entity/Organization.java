package org.zipper.helper.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.zipper.helper.web.entity.NodeEntity;

/**
 * 组织实体
 *
 * @author zhuxj
 * @since 2020/07/06
 */
public class Organization extends NodeEntity {
    /**
     * 部门编号
     * 因为用了MybatisPlus的sql维护
     * 需要显式对编号进行自增描述
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 组织节点名称
     */
    private String name;
    /**
     * 组织排序字段
     */
    private Integer sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
