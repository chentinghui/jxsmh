package com.newsupplytech.jxsmh.contrller;


import com.newsupplytech.jxsmh.entity.user.User;
import com.newsupplytech.jxsmh.service.LoginService;
import com.newsupplytech.jxsmh.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/login")
public class LoginContrller {

//    @Resource
//    private LoginService loginService;
//
//    @GetMapping(value = "/user/{id}")
//    private User test(@PathVariable("id") Integer id){
//
//        return loginService.selectUserById(id);
//    }


    private LoginService loginService;
    private UserService userService;

    @Autowired
    public LoginContrller(LoginService loginService, UserService userService) {
        this.loginService = loginService;
        this.userService = userService;
    }

    @PostMapping("")
    public Object login(@RequestBody User user) {
        User userInDataBase = userService.findByName(user.getUsername(),user.getPermission());
        JSONObject jsonObject = new JSONObject();
        if (userInDataBase == null) {
            jsonObject.put("message", "用户不存在");
        } else if (!userService.comparePassword(user, userInDataBase)) {
            jsonObject.put("message", "密码不正确");
        }else {
            String token = loginService.getToken(userInDataBase);
            jsonObject.put("token", token);
            jsonObject.put("user", userInDataBase);
        }
        return jsonObject;
    }

}

