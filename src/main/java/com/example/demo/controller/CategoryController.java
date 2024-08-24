package com.example.demo.controller;

import com.example.demo.pojo.Category;
import com.example.demo.pojo.Result;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    //新增文章分类
    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category) {

        categoryService.add(category);
        return Result.success();
    }

    //显示文章分类列表
    @GetMapping
    public Result<List<Category>> list() {
        List<Category> cs =  categoryService.list();
        return Result.success(cs);
    }

    //显示指定文章分类
    @GetMapping("/detail")
    public Result<Category> detail( Integer id) {
        Category c =  categoryService.findById(id);
        return Result.success(c);
    }
    //更新文章分类
    @PutMapping
    public Result update(@RequestBody @Validated(Category.Updata.class) Category category) {
        categoryService.updata(category);
        return Result.success();
    }
    //删除文章分类
    @DeleteMapping
    public Result delete(Integer id) {
        categoryService.delete(id);
        return Result.success();
    }
}
