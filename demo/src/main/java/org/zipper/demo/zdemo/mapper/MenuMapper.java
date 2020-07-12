package org.zipper.demo.zdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.zipper.demo.zdemo.entity.CustomMenu;

@Mapper
public interface MenuMapper extends BaseMapper<CustomMenu> {
}
