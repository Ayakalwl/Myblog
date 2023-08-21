package com.lxy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Long id;
    private String roleName;
    private String status;
    private String roleKey;
    private Integer roleSort;
    private List<Long> menuIds;
    private String remark;
}
