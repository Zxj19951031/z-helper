package org.zipper.helper.web.entity;


public abstract class NodeEntity extends BaseEntity {
    private Long pid;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}
