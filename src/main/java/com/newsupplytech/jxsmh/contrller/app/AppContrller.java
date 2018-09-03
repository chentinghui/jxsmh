package com.newsupplytech.jxsmh.contrller.app;

import com.alibaba.fastjson.JSONObject;
import com.newsupplytech.jxsmh.annotation.LoginRequired;
import com.newsupplytech.jxsmh.entity.data.DataClass;
import com.newsupplytech.jxsmh.entity.data.DataInfo;
import com.newsupplytech.jxsmh.entity.news.News;
import com.newsupplytech.jxsmh.entity.user.User;
import com.newsupplytech.jxsmh.service.LoginService;
import com.newsupplytech.jxsmh.service.app.AppService;
import com.newsupplytech.jxsmh.service.user.UserService;
import com.newsupplytech.jxsmh.until.HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/app")

public class AppContrller {
    private LoginService loginService;
    private UserService userService;

    @Autowired
    public AppContrller(LoginService loginService, UserService userService) {
        this.loginService = loginService;
        this.userService = userService;
    }

    @Autowired
    AppService appService;


    @PostMapping("/login")
    public Object login(@RequestBody User user) {
        User userInDataBase = userService.findByName(user.getUsername(),2);
        JSONObject jsonObject = new JSONObject();
        if (userInDataBase == null) {
            jsonObject.put("status", "2");//失败
            jsonObject.put("message", "用户不存在");
            jsonObject.put("emessage", "user does not exist");
        } else if (!userService.comparePassword(user, userInDataBase)) {
            jsonObject.put("status", "2");//失败
            jsonObject.put("message", "密码不正确");
            jsonObject.put("emessage", "incorrect password");
        }else {
            appService.saveLoaction(user);
            String token = loginService.getToken(userInDataBase);
            JSONObject json = new JSONObject();
            json.put("token",token);
            json.put("user",userInDataBase);
            jsonObject.put("status", "1");//成功
            jsonObject.put("message", "登录成功");
            jsonObject.put("emessage", "login successful");
            jsonObject.put("data", json);

        }
        return jsonObject;
    }

    @LoginRequired
    @PostMapping("/class")
    public Object dataclass(@RequestBody DataClass dataClass) {
        JSONObject jsonObject = new JSONObject();
        JSONObject json = new JSONObject();
        List<DataClass> dataClassList =appService.queryOne(dataClass);
        json.put("dataClass",dataClassList);
        jsonObject.put("status", "1");//成功
        jsonObject.put("message", "成功");
        jsonObject.put("emessage", "success");
        jsonObject.put("data", json);

        return jsonObject;
    }

    @LoginRequired
    @PostMapping("/datainfo")
    public Object datainfo(@RequestBody DataInfo dataInfo) {

        JSONObject jsonObject = new JSONObject();
        JSONObject json = new JSONObject();
        List<DataInfo> dataInfoList =appService.queryDataInfo(dataInfo);
        json.put("dataInfo",dataInfoList);
        jsonObject.put("status", "1");//成功
        jsonObject.put("message", "成功");
        jsonObject.put("emessage", "success");
        jsonObject.put("data", json);

        return jsonObject;
    }

    @LoginRequired
    @PostMapping("/news")
    public Object queryNews(@RequestBody(required = false) News news) {
        JSONObject jsonObject = new JSONObject();
        JSONObject json = new JSONObject();
        List<News> newslist =appService.queryNews(news);
        for(int i=0;i<newslist.size();i++){
            String text = newslist.get(i).getDetails();
            newslist.get(i).setDetails(HtmlUtil.getTextFromHtml(text));
        }

        json.put("news",newslist);
        jsonObject.put("status", "1");//成功
        jsonObject.put("message", "成功");
        jsonObject.put("emessage", "success");
        jsonObject.put("data", json);

        return jsonObject;
    }

    @LoginRequired
    @PostMapping("/updatePwd")
    public Object updatePwd(@RequestBody User user) {
        JSONObject jsonObject = new JSONObject();
        JSONObject json = new JSONObject();
        int flag = appService.updatePwd(user);
        if(0 == flag){
            jsonObject.put("status", "2");//成功
            jsonObject.put("message", "旧密码错误");
            jsonObject.put("emessage", "Old password error");
            jsonObject.put("data", json);
        }else{
            jsonObject.put("status", "1");//成功
            jsonObject.put("message", "修改成功");
            jsonObject.put("emessage", "Successfully modified");
            jsonObject.put("data", json);
        }


        return jsonObject;
    }

    //跳转详细页面
    @RequestMapping("/detailePage")
    public ModelAndView detailePage(@RequestParam("id") String id){

        return new ModelAndView("forward:/pages/front/detail.html?id="+id);
    }

}
