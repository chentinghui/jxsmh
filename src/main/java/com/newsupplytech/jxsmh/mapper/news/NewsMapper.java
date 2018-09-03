package com.newsupplytech.jxsmh.mapper.news;

import com.newsupplytech.jxsmh.entity.news.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NewsMapper {
    @Insert("insert into news (title,picture,state,details) values (#{title},#{picture},#{state},#{details})")
    public boolean insertNews(@Param("title") String title,@Param("picture") String picture,
                              @Param("state") String state,@Param("details") String details);

    @Select("select id,title,picture,state,details,createtime from news where title like '%${title}%' " +
            "and state = #{state}")
    public List<News> queryNews(@Param("title") String title,
                                @Param("state") String state);

    @Select("select id,title,picture,state,details, createtime  from news")
    public List<News> queryNewsAll();

    public int deleteNews(List list);

    public List<News> queryImageUrl(List list);

    public Boolean updateOne(@Param("id")Integer id,@Param("title")String title,@Param("fileUrl") String fileUrl,
                @Param("state")String state, @Param("details")String details);

}
