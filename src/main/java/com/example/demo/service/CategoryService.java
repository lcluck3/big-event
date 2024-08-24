package com.example.demo.service;

import com.example.demo.pojo.Category;

import java.util.List;

public interface CategoryService {

    void add(Category category);//新增分类

    List<Category> list();//查找所以分类

    Category findById(Integer id);//根据id

    void updata(Category category);//更新

    void delete(Integer id);//删除
}
