package com.lxy.service;

import com.lxy.domain.ResponseResult;
import com.lxy.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 爱宕
* @description 针对表【sg_comment(评论表)】的数据库操作Service
* @createDate 2023-02-21 19:35:51
*/
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
