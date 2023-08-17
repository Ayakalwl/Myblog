package com.lxy.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuRoleVo {
    private Long id;
    private Long parentId;
    private String label;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MenuRoleVo> children;
}
