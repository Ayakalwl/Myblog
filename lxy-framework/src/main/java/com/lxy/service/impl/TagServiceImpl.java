package com.lxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.domain.entity.Tag;
import com.lxy.mapper.TagMapper;
import com.lxy.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author 爱宕
 * @since 2023-07-19 16:54:38
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}