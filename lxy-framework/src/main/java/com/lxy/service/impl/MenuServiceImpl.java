package com.lxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.constants.SystemConstants;
import com.lxy.domain.ResponseResult;
import com.lxy.domain.dto.MenuDto;
import com.lxy.domain.entity.Menu;
import com.lxy.domain.vo.MenuRoleVo;
import com.lxy.domain.vo.MenuVo;
import com.lxy.enums.AppHttpCodeEnum;
import com.lxy.exception.SystemException;
import com.lxy.mapper.MenuMapper;
import com.lxy.service.MenuService;
import com.lxy.utils.BeanCopyUtils;
import com.lxy.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是初始管理员返回所有权限
        if (id == 1L){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON)
                    .eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            List<String> perms =menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            //如果是 获取所有符合要求的Menu
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.DIRECTORY)
                    .eq(Menu::getStatus,SystemConstants.STATUS_NORMAL)
                    .orderBy(true,true,Menu::getParentId,Menu::getOrderNum);
            menus = list(wrapper);
        }else{
            //否则  获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        //构建tree
        //先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    @Override
    public ResponseResult getList(MenuDto menuDto) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(menuDto.getMenuName()),Menu::getMenuName,menuDto.getMenuName())
                .like(StringUtils.hasText(menuDto.getStatus()),Menu::getStatus,menuDto.getStatus())
                .orderBy(true,true,Menu::getParentId,Menu::getOrderNum);
        List<MenuVo> menuVoList = BeanCopyUtils.copyBeanList(list(wrapper), MenuVo.class);
        return ResponseResult.okResult(menuVoList);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenu(Integer id) {
        Menu menu = getById(id);
        MenuVo menuVo = BeanCopyUtils.copyBean(menu, MenuVo.class);
        return ResponseResult.okResult(menuVo);
    }

    @Override
    public ResponseResult upDateMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeMenu(Integer menuId) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId, menuId);
        List<Menu> list = list(wrapper);
        if (!list.isEmpty()){
            throw new SystemException(AppHttpCodeEnum.MENU_PID_NOT_NULL);
        }
        removeById(menuId);
        return ResponseResult.okResult();
    }

    @Override
    public List<MenuRoleVo> treeSelect() {
        List<MenuRoleVo> menuRoleVos = getBaseMapper().selectMenuRoleVoTree();
        List<MenuRoleVo> menuRoleVoTree = builderMenuRoleVoTree(menuRoleVos, 0L);
        return menuRoleVoTree;
    }

    @Override
    public List<Integer> getCheckedKeys(Long id) {
        List<Integer> checkedKeys = getBaseMapper().getCheckedKeys(id);
        return checkedKeys;
    }


    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }


    private List<MenuRoleVo> builderMenuRoleVoTree(List<MenuRoleVo> menus, Long parentId) {
        List<MenuRoleVo> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .peek(menu -> menu.setChildren(builderMenuRoleVoTree(menus, menu.getId())))
                .collect(Collectors.toList());
        return menuTree;
    }


    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
}




