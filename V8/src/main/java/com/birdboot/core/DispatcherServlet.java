package com.birdboot.core;

import com.birdboot.http.HttpServletRequest;
import com.birdboot.http.HttpServletResponse;
import com.sun.scenario.effect.impl.prism.PrImage;

import java.io.File;
import java.net.URISyntaxException;

/**
 * 该类实际上是SpringMVC框架的一个核心类,用于与服务器(Tomcat)整合,接手处理请求的工作
 */
public class DispatcherServlet {
    //饿汉式单例模式
    private DispatcherServlet(){}//私有构造器

    private static DispatcherServlet dispatcherServlet;//创建静态的对象

    public static DispatcherServlet getDispatcherServlet(){//创建公共的静态get方法让别的类调用
        return dispatcherServlet;
    }

    private static File baseDir;//类加载路径
    private static File staticDir;//类加载路径下的static目录
    static {
        try {
            baseDir = new File(
               ClientHandler.class.getClassLoader().getResource(".").toURI()
            );
            staticDir = new File(baseDir,"static");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public void service(HttpServletRequest request, HttpServletResponse response){
        String path = request.getUri();
        System.out.println("抽象路径:"+path);


        File file = new File(staticDir,path);

        if(file.isFile()){//file表示的是一个实际存在的文件
            response.setStatusCode(200);
            response.setStatusReason("OK");
            response.setContentFile(file);
        }else{
            response.setStatusCode(404);
            response.setStatusReason("NotFound");
            file = new File(staticDir,"404.html");
            response.setContentFile(file);
        }
    }
}
