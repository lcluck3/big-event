package com.example.demo.service;

import com.example.demo.pojo.User;

public interface UserService {

    //根据用户名查找用户
    User findByUserName(String username);
    //注册
    void register(String username, String password);
    //更新数据
    void updata(User user);
    //更新头像
    void updataAvatar(String avatarUrl);
    //更新密码
    void updataPwd(String newPwd);
}
