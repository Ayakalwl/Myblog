package com.lxy.controller;

import com.lxy.domain.ResponseResult;
import com.lxy.domain.dto.UserDto;
import com.lxy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult getList(Integer pageNum, Integer pageSize, UserDto userDto){
        return userService.getList(pageNum, pageSize, userDto);
    }

    @PostMapping
    public ResponseResult addUser(@RequestBody UserDto userDto){
        return userService.addUser(userDto);
    }

}
