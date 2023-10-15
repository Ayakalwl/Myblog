package com.lxy.controller;

import com.lxy.domain.ResponseResult;
import com.lxy.domain.dto.AddArticleDto;
import com.lxy.domain.dto.ArticleDto;
import com.lxy.domain.dto.UpdateArticleDto;
import com.lxy.enums.AppHttpCodeEnum;
import com.lxy.exception.SystemException;
import com.lxy.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto articleDto){
        if (articleDto.getCategoryId() == null){
            throw new SystemException(AppHttpCodeEnum.CATEGORYNAME_NOT_NULL);
        }
        return articleService.add(articleDto);
    }

    @PutMapping
    public ResponseResult update(@RequestBody UpdateArticleDto updateArticleDto){
        return articleService.updateArticle(updateArticleDto);
    }

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, ArticleDto articleDto){
        return articleService.articleList(pageNum,pageSize,articleDto);
    }

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    @GetMapping("/{id}")
    public ResponseResult getArticle(@PathVariable Integer id){
        return articleService.getArticle(id);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable List<Long> id){
        return articleService.deleteArticle(id);
    }
}
