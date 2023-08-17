package com.lxy.controller;

import com.lxy.domain.ResponseResult;
import com.lxy.domain.dto.RoleDto;
import com.lxy.domain.entity.Role;
import com.lxy.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult getRoleList(Integer pageNum, Integer pageSize, RoleDto roleDto){
        return roleService.getRoleList(pageNum, pageSize, roleDto);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody Role role){
        return roleService.changeStatus(role);
    }

    @PostMapping
    public ResponseResult addRole(@RequestBody RoleDto roleDto){
        return roleService.addRole(roleDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getRole(@PathVariable("id") Long id){
        return roleService.getRole(id);
    }
}
