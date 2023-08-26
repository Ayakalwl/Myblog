package com.lxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxy.domain.ResponseResult;
import com.lxy.domain.dto.RoleDto;
import com.lxy.domain.entity.Role;

import java.util.List;

/**
 *
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult getRoleList(Integer pageNum, Integer pageSize, RoleDto roleDto);

    ResponseResult changeStatus(Role role);

    ResponseResult addRole(RoleDto roleDto);

    ResponseResult getRole(Long id);

    ResponseResult updateRole(RoleDto roleDto);

    ResponseResult deleteRole(List<Long> id);

    ResponseResult getListAllRole();
}
