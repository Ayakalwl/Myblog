package com.lxy.service;

import com.lxy.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lxy.domain.ResponseResult;

/**
* @author 爱宕
* @description 针对表【sg_link(友链)】的数据库操作Service
* @createDate 2023-02-21 03:01:53
*/
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
