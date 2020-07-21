package org.zipper.helper.web.entity;

import java.time.LocalDate;

/**
 * 带有日期相关的实体
 *
 * @author zhuxj
 * @since 2020/07/06
 */
public abstract class DatetimeEntity {

    private LocalDate createTime;
    private LocalDate updateTime;

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public LocalDate getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
    }
}
