package com.example.demo.controller;


import com.example.demo.pojo.Article;
import com.example.demo.pojo.PageBean;
import com.example.demo.pojo.Result;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated Article article) {
        articleService.add(article);
        return Result.success();
    }
    @GetMapping
    public Result<PageBean<Article>> list(Integer pageNum, Integer pageSize,
                                        @RequestParam(required = false)  Integer categoryId,
                                        @RequestParam(required = false)  String state) {
        PageBean<Article> pageBean= articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pageBean);
    }
    @PutMapping
    public Result update(@RequestBody @Validated Article article) {
        articleService.updata(article);
        return Result.success();
    }
    @GetMapping("/detail")
    public Result<Article> findById(Integer id) {
        Article article = articleService.findById(id);
        return Result.success(article);
    }
    @DeleteMapping
    public Result delete(Integer id) {
        articleService.delete(id);
        return Result.success();
    }

}
