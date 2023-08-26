package com.lxy.controller;

import com.lxy.domain.ResponseResult;
import com.lxy.domain.dto.LinkDto;
import com.lxy.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;


    @GetMapping("/list")
    public ResponseResult getList(Integer pageNum, Integer pageSize, LinkDto linkDto){
        return linkService.getList(pageNum, pageSize, linkDto);
    }

    @PostMapping
    public ResponseResult addLink(@RequestBody LinkDto linkDto){
        return linkService.addLink(linkDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getLink(@PathVariable Long id){
        return linkService.getLink(id);
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody LinkDto linkDto){
        return linkService.updateLink(linkDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable List<Long> id){
        return linkService.deleteLink(id);
    }
}
