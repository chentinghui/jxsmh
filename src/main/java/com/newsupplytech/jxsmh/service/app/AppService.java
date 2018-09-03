package com.newsupplytech.jxsmh.service.app;

import com.newsupplytech.jxsmh.entity.data.DataClass;
import com.newsupplytech.jxsmh.entity.data.DataInfo;
import com.newsupplytech.jxsmh.entity.news.News;
import com.newsupplytech.jxsmh.entity.user.User;
import com.newsupplytech.jxsmh.mapper.app.AppMapper;
import com.newsupplytech.jxsmh.mapper.data.DataClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppService {

    @Resource
    AppMapper appMapper;

    public List<DataClass> queryOne(DataClass dataClass){

        return  appMapper.queryOne(dataClass);
    }

    public List<DataInfo> queryDataInfo(DataInfo dataInfo){

        return  appMapper.queryDataInfo(dataInfo);
    }

    public List<News> queryNews(News news){
        return  appMapper.queryNews(news);
    }

    public int updatePwd(User user){
        int num = appMapper.queryPassword(user);
        if(1 == num){
            return appMapper.updatePwd(user);
        }
        return  0;
    }

    public Boolean saveLoaction(User user){
        return  appMapper.saveLoaction(user);
    }

}
