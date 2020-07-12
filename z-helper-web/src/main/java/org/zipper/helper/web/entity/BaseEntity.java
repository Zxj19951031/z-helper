package org.zipper.helper.web.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础类型
 *
 * @author zhuxj
 * @since 2020/07/06
 */
public abstract class BaseEntity extends IdentityEntity {

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer status;


    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
