package com.birdboot.test;

import java.util.HashMap;
import java.util.Map;

public class splitTest {
    public static void main(String[] args) {

       // String str ="1=2=3=4=5=6=7=========";
        //split(regex,limit)
        //limit分为0,N,负数
        //当limit为负数,表示全部拆出,保留一个空字符串
        //N表示拆多少,不够的时候保留空格
        //0的效果和没有limit的效果一致


        String url = "index.html?username=&password=123&nickname=ddlp&age=21";
        String requestURI;//url?左侧的/index.html
        String queryString;//url/右侧
        Map<String,String> parameters = new HashMap<>();//用来保存切割出来的键值对
        //
        if(url.contains("?") && url.split("\\?").length!=1){
            requestURI = url.split("\\?")[0];
            queryString = url.split("\\?")[1];

            String[] para = queryString.split("&");
            for(String s : para){
                String key = s.split("=",2)[0];
                parameters.put(key,s.split("=",2)[1]);
            }
        }else{
            requestURI = url.split("\\?")[0];
        }

        System.out.println(parameters.entrySet());
        System.out.println(parameters.get("username"));
        System.out.println(requestURI);
    }
}
