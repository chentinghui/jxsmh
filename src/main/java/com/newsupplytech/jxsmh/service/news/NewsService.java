package com.newsupplytech.jxsmh.service.news;

import com.newsupplytech.jxsmh.entity.news.News;
import com.newsupplytech.jxsmh.mapper.news.NewsMapper;
import com.newsupplytech.jxsmh.until.FileUtil;
import com.newsupplytech.jxsmh.until.LoggerUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewsService {
    LoggerUtils log = new LoggerUtils();

    @Resource
    public NewsMapper newsMapper = null;

    @Value("${jxsmh.filePath}")
    public String staticUrl;

    public boolean insertNews(String title,MultipartFile file,String state,String details){
        boolean inserTstate = false;

        String newFileName = new FileUtil().createDir(file,staticUrl);

        if("1".equals(newFileName)){
            newFileName = null;//没有图片
        }else if("0".equals(newFileName)){
            return false;//图片上传失败
        }
        inserTstate = newsMapper.insertNews(title, newFileName, state, details);
        return inserTstate;
    }

    public Boolean updateOne(Integer id, String title,MultipartFile file,String state, String details,String fileUrl){
        boolean upstate = false;
        String flilPath = new FileUtil().createDir(file,staticUrl);

        if("1".equals(flilPath)){
            flilPath = null;//没有图片
        }else if("0".equals(flilPath)){
            return false;//图片上传失败
        }

        upstate = newsMapper.updateOne(id,title,flilPath,state,details);
        if(upstate && null != file){
            upstate = new FileUtil().deleteFile(fileUrl,staticUrl);
        }

        return upstate;
    }

    public List<News> queryNews(String title, String state){
        return newsMapper.queryNews(title,state);
    }

    public List<News> queryNewsAll(){

        return newsMapper.queryNewsAll();
    }

    public int deleteNews(List list){
        List<News> news = newsMapper.queryImageUrl(list);
        for(int i=0;i<news.size();i++){//删除图片
            FileUtil.deleteFile(news.get(0).getPicture(),staticUrl);
        }
        return newsMapper.deleteNews(list);
    }


}
