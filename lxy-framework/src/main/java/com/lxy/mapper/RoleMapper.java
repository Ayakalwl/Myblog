package com.lxy.mapper;

import com.lxy.domain.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Entity com.lxy.domain.entity.Role
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}




