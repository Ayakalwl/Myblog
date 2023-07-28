package com.lxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.domain.entity.Role;
import com.lxy.service.RoleService;
import com.lxy.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if (id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        return getBaseMapper().selectRoleKeyByUserId(id);
    }
}




