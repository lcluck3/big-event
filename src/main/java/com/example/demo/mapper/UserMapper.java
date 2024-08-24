package com.example.demo.mapper;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    //根据用户名查询用户
    User findByUserName(String username);
    //添加
    void add(String username, String password);

    void updata(User user);

    void updataAvatar(String avatarUrl,Integer id);

    void updataPwd(String md5String,Integer id);
}
