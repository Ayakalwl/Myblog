package com.lxy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkDto {
    private Long id;
    private String name;
    private String status;
    private String description;
    private String address;
    private String logo;
}
