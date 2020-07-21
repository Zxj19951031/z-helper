package org.zipper.helper.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.zipper.helper.web.entity.NodeEntity;

/**
 * 菜单类型实体
 *
 * @author zhuxj
 * @since 2020/07/06
 */
public class Menu extends NodeEntity {
    /**
     * 菜单编号
     * 因为用了MybatisPlus的sql维护
     * 需要显式对编号进行自增描述
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 菜单或功能名称
     */
    private String name;
    /**
     * 菜单或功能对应的uri
     */
    private String regexUri;
    /**
     * 菜单层级，功能没有层次
     */
    private Integer level;
    /**
     * 排序字段
     */
    private Integer sort;
    /**
     * 前端元数据，一般用于设定前端的约定
     */
    private String metadata;
    /**
     * 是否为功能路径，是意味着是后台接口功能，否是前台菜单目录
     */
    private boolean isFunc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegexUri() {
        return regexUri;
    }

    public void setRegexUri(String regexUri) {
        this.regexUri = regexUri;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public boolean isFunc() {
        return isFunc;
    }

    public void setFunc(boolean func) {
        isFunc = func;
    }
}
