package com.lxy.service;

import com.lxy.domain.dto.AddArticleDto;
import com.lxy.domain.dto.ArticleDto;
import com.lxy.domain.dto.UpdateArticleDto;
import com.lxy.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lxy.domain.ResponseResult;

/**
* @author 爱宕
* @description 针对表【sg_article(文章表)】的数据库操作Service
* @createDate 2023-02-17 23:21:27
*/
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto articleDto);

    ResponseResult articleList(Integer pageNum, Integer pageSize, ArticleDto articleDto);

    ResponseResult getArticle(Integer id);

    ResponseResult updateArticle(UpdateArticleDto updateArticleDto);

    ResponseResult deleteArticle(Integer id);
}
