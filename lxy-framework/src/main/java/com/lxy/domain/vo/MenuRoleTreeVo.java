package com.lxy.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuRoleTreeVo {
    private List<MenuRoleVo> menus;
    private List<Integer> checkedKeys;
}
