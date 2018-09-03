package com.newsupplytech.jxsmh.mapper.app;

import com.newsupplytech.jxsmh.entity.data.DataClass;
import com.newsupplytech.jxsmh.entity.data.DataInfo;
import com.newsupplytech.jxsmh.entity.news.News;
import com.newsupplytech.jxsmh.entity.user.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppMapper {

   public List<DataClass> queryOne(DataClass dataClass);

   public List<DataInfo> queryDataInfo(DataInfo dataInfo);

   public List<News> queryNews(News news);

   public int updatePwd(User user);

   public Boolean saveLoaction(User user);

   public int queryPassword(User user);
}
