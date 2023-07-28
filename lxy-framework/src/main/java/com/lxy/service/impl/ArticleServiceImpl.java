package com.lxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.constants.SystemConstants;
import com.lxy.domain.ResponseResult;
import com.lxy.domain.entity.Article;
import com.lxy.domain.entity.Category;
import com.lxy.domain.vo.ArticleDetailVo;
import com.lxy.domain.vo.ArticleListVo;
import com.lxy.domain.vo.HotArticleVo;
import com.lxy.domain.vo.PageVo;
import com.lxy.mapper.ArticleMapper;
import com.lxy.service.ArticleService;
import com.lxy.service.CategoryService;
import com.lxy.utils.BeanCopyUtils;
import com.lxy.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author 爱宕
* @description 针对表【sg_article(文章表)】的数据库操作Service实现
* @createDate 2023-02-17 23:21:27
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page(1,10);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();

        //从redis中获取viewCount
        for(Article article:articles){
            Integer viewCount = redisCache.getCacheMapValue("article:viewCount", article.getId().toString());
            article.setViewCount(viewCount.longValue());
        }

        //bean拷贝
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles,HotArticleVo.class);
        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0 ,Article::getCategoryId,categoryId);
        // 状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        List<Article> articles = page.getRecords();

        //从redis中获取viewCount
        for (Article article:articles) {
            Integer viewCount = redisCache.getCacheMapValue("article:viewCount", article.getId().toString());
            article.setViewCount(viewCount.longValue());
        }

        //查询categoryName
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }
}




