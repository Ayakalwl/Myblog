package com.lxy.controller;

import com.lxy.domain.ResponseResult;
import com.lxy.domain.entity.LoginUser;
import com.lxy.domain.entity.User;
import com.lxy.domain.vo.AdminUserInfoVo;
import com.lxy.domain.vo.RoutersVo;
import com.lxy.domain.vo.UserInfoVo;
import com.lxy.enums.AppHttpCodeEnum;
import com.lxy.exception.SystemException;
import com.lxy.service.AdminLoginService;
import com.lxy.service.MenuService;
import com.lxy.service.RoleService;
import com.lxy.utils.BeanCopyUtils;
import com.lxy.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminLoginController {
    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }       return adminLoginService.login(user);
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //查当前登录用户信息和权限
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //封装返回
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        return null;
    }

}