package com.newsupplytech.jxsmh.service.user;

import com.newsupplytech.jxsmh.entity.user.User;
import com.newsupplytech.jxsmh.mapper.user.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserService {
    @Resource
    public UserMapper userMapper;

    public int add(User user){
        String passwordHash =  passwordToHash(user.getPassword());
        user.setPassword(passwordHash);
        return userMapper.add(user);
    }


    public int deleteUser(List list){

        return userMapper.deleteUser(list);
    }

    public int updateOne(User user){
        if("on".equals(user.getPassword())){
            user.setPassword(passwordToHash("123456"));
        }
        return userMapper.updateOne(user);
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public User findById(int id) {
        User user = new User();
        user.setId(id);
        return userMapper.findOne(user);
    }

    public User findByName(String name,Integer permission) {
        User param = new User();
        param.setUsername(name);
        param.setPermission(permission);
        return userMapper.findOne(param);
    }

    public boolean comparePassword(User user, User userInDataBase) {
        return passwordToHash(user.getPassword())      // 将用户提交的密码转换为 hash
                .equals(userInDataBase.getPassword()); // 数据库中的 password 已经是 hash，不用转换
    }

    private String passwordToHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(password.getBytes());
            byte[] src = digest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            // 字节数组转16进制字符串
            // https://my.oschina.net/u/347386/blog/182717
            for (byte aSrc : src) {
                String s = Integer.toHexString(aSrc & 0xFF);
                if (s.length() < 2) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(s);
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException ignore) {
        }
        return null;
    }
}
