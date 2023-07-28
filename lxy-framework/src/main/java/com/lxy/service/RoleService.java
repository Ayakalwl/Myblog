package com.lxy.service;

import com.lxy.domain.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}
