package org.zipper.demo.zdemo.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zipper.demo.zdemo.entity.CustomMenu;
import org.zipper.demo.zdemo.service.MenuService;
import org.zipper.helper.auth.dto.TreeNode;
import org.zipper.helper.web.response.ResponseEntity;

import java.util.List;

@Api
@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PutMapping("add")
    public ResponseEntity<Long> add(@RequestBody CustomMenu menu) {
        menuService.save(menu);
        return ResponseEntity.success(menu.getId());
    }

    @DeleteMapping("del")
    public ResponseEntity<Boolean> delete(@RequestParam Long id) {
        return ResponseEntity.success(menuService.delete(id));
    }

    @PostMapping("edit")
    public ResponseEntity<Boolean> edit(@RequestBody CustomMenu menu) {
        return ResponseEntity.success(menuService.updateById(menu));
    }

    @GetMapping("get")
    public ResponseEntity<List<TreeNode<CustomMenu>>> tree() {
        return ResponseEntity.success(menuService.getTree());
    }
}
