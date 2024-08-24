package com.example.demo.service;

import com.example.demo.pojo.Article;
import com.example.demo.pojo.PageBean;

public interface ArticleService {
    void add(Article article);//添加

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);//条件分页列表查询

    void updata(Article article);

    Article findById(Integer id);

    void delete(Integer id);
}
