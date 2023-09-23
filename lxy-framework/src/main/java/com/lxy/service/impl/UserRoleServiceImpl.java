package com.lxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.domain.entity.UserRole;
import com.lxy.service.UserRoleService;
import com.lxy.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author 爱宕
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2023-08-21 18:27:49
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




