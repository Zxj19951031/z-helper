package org.zipper.helper.web.entity;

import java.time.LocalDateTime;

/**
 * 带有日期相关的实体
 *
 * @author zhuxj
 * @since 2020/07/06
 */
public abstract class DatetimeEntity {

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

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
}
