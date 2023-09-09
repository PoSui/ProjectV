package com.birdboot.controller;

import com.birdboot.annotation.Controller;
import com.birdboot.annotation.RequestMapping;
import com.birdboot.http.HttpServletRequest;
import com.birdboot.http.HttpServletResponse;

import java.io.File;

@Controller
public class ArticleController {
    private static File Article;
    static{
        Article = new File("./article");
        if(!Article.exists()){
            Article.mkdirs();
        }
    }


    @RequestMapping("writeArticle")
    public void reg(HttpServletRequest request, HttpServletResponse response){//点击提交文章后的逻辑
        System.out.println("收到发帖内容,开始处理");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String content = request.getParameter("content");






    }
}
