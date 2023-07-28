package com.lxy.controller;

import com.lxy.domain.ResponseResult;
import com.lxy.domain.entity.User;
import com.lxy.enums.AppHttpCodeEnum;
import com.lxy.exception.SystemException;
import com.lxy.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }       return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}