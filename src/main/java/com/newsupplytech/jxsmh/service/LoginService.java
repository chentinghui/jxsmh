package com.newsupplytech.jxsmh.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.newsupplytech.jxsmh.entity.user.User;
import com.newsupplytech.jxsmh.mapper.user.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class LoginService {
    @Resource
    public UserMapper userMapper;


    public String getToken(User user) {
        String token = "";
        try {
            token = JWT.create().withAudience(String.valueOf(user.getId())) // 将 user id 保存到 token 里面
                    .sign(Algorithm.HMAC256(user.getPassword()));   // 以 password 作为 token 的密钥
        } catch (UnsupportedEncodingException ignore) {
        }
        return token;
    }


}

