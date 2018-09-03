package com.newsupplytech.jxsmh.contrller.news;

import com.alibaba.fastjson.JSONObject;
import com.newsupplytech.jxsmh.annotation.LoginRequired;
import com.newsupplytech.jxsmh.entity.news.News;
import com.newsupplytech.jxsmh.service.news.NewsService;
import com.newsupplytech.jxsmh.until.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsContrller {

    @Resource
    private NewsService newsService = null;
    @Value("${jxsmh.filePath}")
    public String staticUrl;

    //@RequestParam(name="id",required=false,defaultValue="0")在参数不存在的情况下，可能希望变量有一个默认值
    //动态列表初始页面
    @PostMapping("/dynamicList")
    @LoginRequired
    public Object dynamicList(){
        List<News> newsList = newsService.queryNewsAll();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","");
        jsonObject.put("code",0);
        jsonObject.put("count",newsList.size());
        jsonObject.put("data",newsList);

        return jsonObject;
    }
//    public String queryList (HttpServletRequest request) {
//        String total = "";
//        String pId = request.getParameter("pId");
//        int currentNumber = Integer.parseInt(request.getParameter("currentNumber"));
//        String currentPage = request.getParameter("currentPage") == null ? "1" : request.getParameter("currentPage");
//        //分页处理，显示第一页的30条数据(默认值)
//        PageHelper.startPage(Integer.parseInt(currentPage), currentNumber);
//        List<PExl> list = exportDao.queryList (pId);
//        if(list.size() > 0){
//            total = list.get(0).getTotal();
//        }
//
//        Page page = PageHelper.localPage.get();
//        if(page!=null){
//            page.setCurrentPage(Integer.parseInt(currentPage));
//        }
//        PageHelper.endPage();
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("code", 0);
//        jsonObject.put("msg", "");
//        jsonObject.put("count", total);
//        jsonObject.put("data", list);
//        //System.out.println("json:----" + jsonObject.toString());
//        return jsonObject.toString();
//    }

    @PostMapping("/addNews")
    public boolean insertNews(@RequestParam(name="title",required=false) String title,@RequestParam(name="file",required=false) MultipartFile file,
                              @RequestParam(name="state",required=false) String state,@RequestParam(name="details",required=false) String details){

        //title, picture, state, details

        boolean insertState = newsService.insertNews(title, file, state, details);

        return insertState;
    }

    @GetMapping("/queryNews")
    public List<News> queryNews(@RequestParam(name="title",required=false) String title,
                                @RequestParam(name="state",required=false) String state){
        //"测试标题","正常" title,state
        return newsService.queryNews("测试标题","正常");
    }



    @PostMapping("/updateOne")//修改用户数据
    public Boolean updateOne(@RequestParam(name="id")Integer id,@RequestParam(name="title",required=false) String title,@RequestParam(name="file",required=false) MultipartFile file,@RequestParam(name="fileUrl",required=false) String fileUrl,
                         @RequestParam(name="state",required=false) String state,@RequestParam(name="details",required=false) String details){
        //System.out.println("---");
        return newsService.updateOne(id,title,file,state,details,fileUrl);
    }

    @GetMapping("/deleteNews/{ids}")
    public int deleteNews(@PathVariable(name="ids") List ids){
        return newsService.deleteNews(ids);
    }
}
