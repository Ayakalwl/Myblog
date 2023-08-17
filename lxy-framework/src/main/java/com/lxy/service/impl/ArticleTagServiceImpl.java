package com.lxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.domain.entity.ArticleTag;
import com.lxy.service.ArticleTagService;
import com.lxy.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
* @author 爱宕
* @description 针对表【sg_article_tag(文章标签关联表)】的数据库操作Service实现
* @createDate 2023-08-10 16:09:39
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{

}




