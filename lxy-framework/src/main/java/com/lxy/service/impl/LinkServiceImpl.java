package com.lxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.constants.SystemConstants;
import com.lxy.domain.ResponseResult;
import com.lxy.domain.entity.Link;
import com.lxy.domain.vo.LinkVo;
import com.lxy.mapper.LinkMapper;
import com.lxy.service.LinkService;
import com.lxy.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 爱宕
* @description 针对表【sg_link(友链)】的数据库操作Service实现
* @createDate 2023-02-21 03:01:53
*/
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        //转换成vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        //封装返回
        return ResponseResult.okResult(linkVos);
    }
}




