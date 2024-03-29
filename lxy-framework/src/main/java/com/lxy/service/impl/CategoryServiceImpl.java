package com.lxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.constants.SystemConstants;
import com.lxy.domain.ResponseResult;
import com.lxy.domain.dto.CategoryDto;
import com.lxy.domain.entity.Article;
import com.lxy.domain.entity.Category;
import com.lxy.domain.vo.CategoryVo;
import com.lxy.domain.vo.PageVo;
import com.lxy.enums.AppHttpCodeEnum;
import com.lxy.exception.SystemException;
import com.lxy.mapper.CategoryMapper;
import com.lxy.service.ArticleService;
import com.lxy.service.CategoryService;
import com.lxy.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 爱宕
* @description 针对表【sg_category(分类表)】的数据库操作Service实现
* @createDate 2023-02-19 23:42:19
*/
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表  状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());


        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream().
                filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public List<CategoryVo> listAllCategor() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus,SystemConstants.NORMAL);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list(wrapper), CategoryVo.class);
        return categoryVos;
    }

    @Override
    public ResponseResult getList(Integer pageNum, Integer pageSize, CategoryDto categoryDto) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(categoryDto.getName()),Category::getName,categoryDto.getName())
                .eq(StringUtils.hasText(categoryDto.getStatus()),Category::getStatus,categoryDto.getStatus());
        Page<Category> page = new Page(pageNum,pageSize);
        page(page, wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(page.getRecords(), CategoryVo.class);
        PageVo pageVo = new PageVo(categoryVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addCategory(CategoryDto categoryDto) {
        if (!StringUtils.hasText(categoryDto.getName())){
            throw new SystemException(AppHttpCodeEnum.CATEGORYNAME_NOT_NULL);
        }
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategory(Long id) {
        Category category = getById(id);
        CategoryVo categoryVo = BeanCopyUtils.copyBean(category, CategoryVo.class);
        return ResponseResult.okResult(categoryVo);
    }

    @Override
    public ResponseResult updateCategory(CategoryDto categoryDto) {
        if (!StringUtils.hasText(categoryDto.getName())){
            throw new SystemException(AppHttpCodeEnum.CATEGORYNAME_NOT_NULL);
        }
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategory(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

}



