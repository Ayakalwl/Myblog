package com.lxy.service;

import com.lxy.domain.ResponseResult;
import com.lxy.domain.entity.User;

/**
 * @author 爱宕
 * @description 用户登录登出
 */
public interface AdminLoginService {
    ResponseResult login(User user);
}