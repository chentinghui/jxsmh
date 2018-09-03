package com.newsupplytech.jxsmh.contrller.front;


import com.newsupplytech.jxsmh.annotation.LoginRequired;
import com.newsupplytech.jxsmh.entity.news.News;
import com.newsupplytech.jxsmh.service.front.NewsDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/front")
public class NewsDetailContrller {

    @Autowired
    NewsDetailService newsDetailService;

    @PostMapping("/detail")
    public Object queryDetail(@RequestBody News news){

        return newsDetailService.queryDetail(news);
    }
}
