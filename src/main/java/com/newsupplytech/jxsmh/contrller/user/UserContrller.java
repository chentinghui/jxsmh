package com.newsupplytech.jxsmh.contrller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newsupplytech.jxsmh.annotation.LoginRequired;
import com.newsupplytech.jxsmh.entity.user.User;
import com.newsupplytech.jxsmh.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserContrller {
    @Resource
    private UserService userService;


    @PostMapping("")
    @LoginRequired
    public Object user() {
        List<User> userList = userService.findAll();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","");
        jsonObject.put("code",0);
        jsonObject.put("count",userList.size());
        jsonObject.put("data",userList);
        return jsonObject;
    }

    @PostMapping("/add")
    public Object add(@RequestBody User user) {
        if (userService.findByName(user.getUsername(),null) != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error","用户名已被使用");
            return jsonObject;
        }

        return userService.add(user);
    }

    @PostMapping("/updateOne")//修改用户数据
    public int updateOne(@RequestBody User user) {

        return userService.updateOne(user);
    }

    @GetMapping("/deleteUser/{ids}")//修改用户数据
    public int deleteUser(@PathVariable(name="ids") List ids) {

        return userService.deleteUser(ids);
    }


//    @GetMapping("{id}")
//    public Object findById(@PathVariable int id) {
//        return userService.findById(id);
//    }
}
