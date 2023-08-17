package com.lxy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateArticleDto {
    //所属分类id
    private Long categoryId;

    //文章内容
    private String content;

    //创建类型
    private Long createBy;

    //时间
    private Date createTime;

    private Integer delFlag;

    private Long id;

    //是否能评论
    private String isComment;

    //是否置顶
    private String isTop;

    //状态
    private String status;

    //文章摘要
    private String summary;

    private List<Long> tags;

    //缩略图
    private String thumbnail;
    //标题
    private String title;

    private Long updateBy;

    private Date updateTime;

    //访问量
    private Long viewCount;
}
