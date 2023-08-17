package com.lxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.domain.entity.RoleMenu;
import com.lxy.service.RoleMenuService;
import com.lxy.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author 爱宕
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service实现
* @createDate 2023-08-17 22:25:42
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{

}




