package com.lxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxy.domain.ResponseResult;
import com.lxy.domain.dto.MenuDto;
import com.lxy.domain.entity.Menu;

import java.util.List;

/**
 *
 */
public interface MenuService extends IService<Menu> {
    List<String> selectPermsByUserId(Long id);
    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult getList(MenuDto menuDto);

    ResponseResult addMenu(Menu menu);

    ResponseResult getMenu(Integer id);

    ResponseResult upDateMenu(Menu menu);

    ResponseResult removeMenu(Integer menuId);

    ResponseResult treeSelect();

    ResponseResult roleMenuTreeselect(Long id);
}
