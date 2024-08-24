package com.example.demo.mapper;

import com.example.demo.pojo.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> list(Integer userid);//显示文章分类列表


    void add(Category category);//新增

    Category findById(Integer id);//显示指定文章分类

    void updata(Category category);//更新文章分类

    void delete(Integer id);//删除
}
