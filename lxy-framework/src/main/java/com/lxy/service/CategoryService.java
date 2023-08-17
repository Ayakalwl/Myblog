package com.lxy.service;

import com.lxy.domain.ResponseResult;
import com.lxy.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lxy.domain.vo.CategoryVo;

import java.util.List;

/**
* @author 爱宕
* @description 针对表【sg_category(分类表)】的数据库操作Service
* @createDate 2023-02-19 23:42:19
*/
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategor();
}
