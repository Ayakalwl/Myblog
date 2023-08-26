package com.lxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxy.domain.ResponseResult;
import com.lxy.domain.dto.LinkDto;
import com.lxy.domain.entity.Link;

import java.util.List;

/**
* @author 爱宕
* @description 针对表【sg_link(友链)】的数据库操作Service
* @createDate 2023-02-21 03:01:53
*/
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult getList(Integer pageNum, Integer pageSize, LinkDto linkDto);

    ResponseResult addLink(LinkDto linkDto);

    ResponseResult getLink(Long id);

    ResponseResult updateLink(LinkDto linkDto);

    ResponseResult deleteLink(List<Long> id);

}
