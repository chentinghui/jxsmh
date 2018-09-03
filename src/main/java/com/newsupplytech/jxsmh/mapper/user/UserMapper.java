package com.newsupplytech.jxsmh.mapper.user;

import com.newsupplytech.jxsmh.entity.user.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    //    @Select("select * from user where id=#{id}")
//    public User selectid(@Param("id") Integer id);
//
//    @Insert("insert into user values(null,#{name})")
//    public boolean insertUser(@Param("name") String name);
//
//    @Select("select * from user u join user u1  where u.id=#{id}")
//    public List<Map> select(@Param("id") Integer id);
    //XML自动注入
   // public User selectUserById(@Param("id") Integer id);

    public int add(User user);

    public User findOne(User user);

    public List<User> findAll();

    public int updateOne(User use);

    public int deleteUser(List list);

}
