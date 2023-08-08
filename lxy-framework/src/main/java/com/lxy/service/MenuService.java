package com.lxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxy.domain.entity.Menu;

import java.util.List;

/**
 *
 */
public interface MenuService extends IService<Menu> {
    List<String> selectPermsByUserId(Long id);
    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}
