package com.lxy.domain.vo;

import com.lxy.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleVo {
    private List<Long> roleIds;
    private List<Role> roles;
    private UserVo user;
}
