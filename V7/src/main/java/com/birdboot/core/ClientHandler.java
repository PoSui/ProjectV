package com.birdboot.core;

import com.birdboot.http.HttpServletRequest;
import com.birdboot.http.HttpServletResponse;

import java.io.*;
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
            String path = request.getUri();
            System.out.println("抽象路径:"+path);

            File baseDir = new File(
               ClientHandler.class.getClassLoader().getResource(".").toURI()
            );
            File staticDir = new File(baseDir,"static");
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

            //3发送响应
            response.response();


        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
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

