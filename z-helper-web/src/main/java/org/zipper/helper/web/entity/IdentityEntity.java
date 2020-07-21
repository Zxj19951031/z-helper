package org.zipper.helper.web.entity;

/**
 * 带有Id字段的实体记录
 *
 * @author zhuxj
 * @since 2020/07/06
 */
public abstract class IdentityEntity {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
