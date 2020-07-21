package org.zipper.helper.auth.entity;

import org.zipper.helper.web.entity.BaseEntity;

/**
 * 角色实体
 *
 * @author zhuxj
 * @since 2020/07/06
 */
public  class Role extends BaseEntity {
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色描述
     */
    private String desc;
    /**
     * 角色等级，通常和角色类型作为公共字段，在类型和层级存在
     */
    private Integer level;
    /**
     * 排序字段
     */
    private Integer sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
