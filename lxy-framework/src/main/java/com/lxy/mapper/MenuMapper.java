package com.lxy.mapper;

import com.lxy.domain.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Entity com.lxy.domain.entity.Menu
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long userid);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}




