package org.zipper.demo.zdemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.zipper.helper.auth.entity.Menu;

@TableName("tb_auth_menu")
public class CustomMenu extends Menu {

    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
