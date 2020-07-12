package org.zipper.helper.auth.entity;

import org.zipper.helper.web.entity.BaseEntity;

/**
 * 用户实体
 *
 * @author zhuxj
 * @since 2020/07/007
 */
public  class Member extends BaseEntity {
    /**
     * 用户名称
     */
    private String name;
    /**
     * 用户性别
     */
    private Integer sex;
    /**
     * 用户手机号
     */
    private String mobile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
