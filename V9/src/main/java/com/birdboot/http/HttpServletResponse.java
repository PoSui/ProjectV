package com.birdboot.http;

import sun.dc.pr.PRError;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 响应对象
 * 该类的每一个实例表示HTTP协议规定的一个响应
 * 每个响应由三部分构成:
 * 状态行,响应头,响应正文
 */
public class HttpServletResponse {
    private Socket socket;

    //状态行相关信息
    private int statusCode;         //状态代码
    private String statusReason;    //状态描述

    //响应行相关信息
    private Map<String,String> headers = new HashMap<String,String>();

    //响应正文相关信息
    private File contentFile;       //响应正文对应的实体文件

    public HttpServletResponse(Socket socket){
        this.socket = socket;
    }

    /**
     * 将当前响应对象表示的响应信息发送给浏览器
     */
    public void response() throws IOException {
        //3.1发送状态行
        sendStatusLine();
        //3.2发送响应头
        sendHeaders();
        //3.3发送正文
        sendContent();
    }
    //发送状态行
    private void sendStatusLine() throws IOException {
        println("HTTP/1.1" + " " + statusCode + " " + statusReason);
    }
    //发送响应头
    private void sendHeaders() throws IOException {
     /*
        headers
        key   value
        contentType   html/text
      */
        Set<Map.Entry<String,String>> entrySet = headers.entrySet();
        for(Map.Entry<String ,String> e : entrySet){
            String key = e.getKey();
            String value = e.getValue();
            println(key+": "+value);
        }

//        println("Content-Type: text/html");
//        println("Content-Length: "+contentFile.length());
        //单独发送回车+换行表示响应头部分发送完毕
        println("");
    }
    //发送响应正文
    private void sendContent() throws IOException {
        OutputStream out = socket.getOutputStream();
        try(
            //使用自动关闭特性,确保文件流使用完毕后被关闭
            FileInputStream fis = new FileInputStream(contentFile);
        ){
            int len = 1;//记录每次实际读取的数据量
            byte[] buf = new byte[1024 * 10];//10kb
            while ((len = fis.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
        }
    }

    private void println(String line) throws IOException {
        OutputStream out = socket.getOutputStream();
        byte[] data = line.getBytes(StandardCharsets.ISO_8859_1);
        out.write(data);
        out.write(13);//发送回车符
        out.write(10);//发送换行符
    }



    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public File getContentFile() {
        return contentFile;
    }

    public void setContentFile(File contentFile) {
        this.contentFile = contentFile;
    }

    public void addHeaders(String name,String value){
        this.headers.put(name,value);
    }
}




