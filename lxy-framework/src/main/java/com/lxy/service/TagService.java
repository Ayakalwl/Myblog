package com.lxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxy.domain.ResponseResult;
import com.lxy.domain.dto.TagListDto;
import com.lxy.domain.entity.Tag;

import java.util.List;

/** 标签(Tag)表服务接口
 *
 * @author 爱宕
 * @since 2023-07-19 16:52:38
 */
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNumber, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagListDto tagListDto);

    ResponseResult deleteTag(List<Long> id);

    ResponseResult updateTag(Tag tag);

    ResponseResult getTag(Integer id);

    ResponseResult listAllTag();

}