package com.lxy.service;

import com.lxy.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 爱宕
 * @description 上传图片
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}