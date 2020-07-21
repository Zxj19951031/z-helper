package org.zipper.helper.web.entity;

/**
 * 逻辑实体，维护一个逻辑状态变量，用于逻辑删除记录
 *
 * @author zhuxj
 * @since 2020/07/06
 */
public abstract class LogicalEntity {
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
