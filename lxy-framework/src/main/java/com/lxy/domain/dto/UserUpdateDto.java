package com.lxy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    private Long id;
    private String nickName;
    private String phonenumber;
    private String email;
    private String sex;
    private String status;
    private List<Long> roleIds;
}
