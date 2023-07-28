package com.lxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxy.domain.ResponseResult;
import com.lxy.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-02-21 21:05:21
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();
    ResponseResult updateUserInfo(User user);
    ResponseResult register(User user);
}
