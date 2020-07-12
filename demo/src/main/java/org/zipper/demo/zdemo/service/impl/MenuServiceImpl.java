package org.zipper.demo.zdemo.service.impl;

import org.springframework.stereotype.Service;
import org.zipper.demo.zdemo.entity.CustomMenu;
import org.zipper.demo.zdemo.mapper.MenuMapper;
import org.zipper.demo.zdemo.service.MenuService;
import org.zipper.helper.auth.service.impl.AbstractMenuServiceImpl;

@Service
public class MenuServiceImpl extends AbstractMenuServiceImpl<CustomMenu, MenuMapper> implements MenuService {
}
