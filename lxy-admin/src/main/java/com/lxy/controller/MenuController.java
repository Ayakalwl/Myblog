package com.lxy.controller;

import com.lxy.domain.ResponseResult;
import com.lxy.domain.dto.MenuDto;
import com.lxy.domain.entity.Menu;
import com.lxy.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult getList(MenuDto menuDto){
        return menuService.getList(menuDto);
    }

    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }

    @GetMapping("/{id}")
    public ResponseResult getMenu(@PathVariable Integer id){
        return menuService.getMenu(id);
    }

    @PutMapping
    public ResponseResult upDateMenu(@RequestBody Menu menu){
        return menuService.upDateMenu(menu);
    }

    @DeleteMapping("/{menuId}")
    public ResponseResult removeMenu(@PathVariable Integer menuId){
        return menuService.removeMenu(menuId);
    }

    @GetMapping("/treeselect")
    public ResponseResult treeSelect(){
        return menuService.treeSelect();
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTreeselect(@PathVariable Long id){
        return menuService.roleMenuTreeselect(id);
    }


}
