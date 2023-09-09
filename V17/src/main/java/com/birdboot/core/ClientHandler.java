package com.birdboot.core;

import com.birdboot.http.EmptyRequestException;
import com.birdboot.http.HttpServletRequest;
import com.birdboot.http.HttpServletResponse;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 该线程任务负责与指定客户端进行一次HTTP交互
 *
 * 1:解析请求
 * 2:处理请求
 * 3:回复响应
 *
 */
public class ClientHandler implements Runnable{
    private Socket socket;
    public ClientHandler(Socket socket){
        this.socket = socket;
    }
    public void run() {
        try {
            //1解析请求
            HttpServletRequest request = new HttpServletRequest(socket);
            HttpServletResponse response = new HttpServletResponse(socket);

            //2处理请求
            DispatcherServlet servlet = DispatcherServlet.getDispatcherServlet();//单例模式
            servlet.service(request,response);

            //3发送响应
            response.response();


        } catch (IOException | URISyntaxException | ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (EmptyRequestException | NoSuchMethodException e) {
//            e.printStackTrace();
            System.out.println("一个空请求");
            //如果捕获到异常 什么也不做,会结束try/catch 从而实现客户端无响应和断开连接
        } finally {
            try {
                //HTTP协议要求,一次交互后要断开链接
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }




    public static void main(String[] args) {
        try {
            File baseDir = new File(
                ClientHandler.class.getClassLoader().getResource(".").toURI()
            );
            //定位类加载路径下的static目录
            File staticDir = new File(baseDir,"static");
            //定位static下的index.html
            File file = new File(staticDir,"index.html");

            System.out.println("是否存在:"+file.exists());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}

