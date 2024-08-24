package com.example.demo.mapper;

import com.example.demo.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {


    void add(Article article);

    List<Article> list(Integer userId, Integer categoryId, String state);

    void updata(Article article);

    Article findById(Integer id);

    void delete(Integer id);
}
