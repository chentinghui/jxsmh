package com.newsupplytech.jxsmh.service.front;

import com.newsupplytech.jxsmh.entity.news.News;
import com.newsupplytech.jxsmh.mapper.front.NewsDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewsDetailService {

    @Resource
    NewsDetailMapper newsDetailMapper;

    public News queryDetail(News news){
        return newsDetailMapper.queryDetail(news);
    }
}
