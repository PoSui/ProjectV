package com.birdboot.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求对象
 * 该类的每一个实例用于表示HTTP协议规定的请求
 * 一个请求由三个部分构成:
 * 请求行,消息头,消息正文
 */
public class HttpServletRequest {
    private Socket socket;
    //请求行相关信息
    private String method;
    private String uri;
    private String protocol;

    //消息头相关信息
    //key:消息头的名字，value:消息头的值
    private Map<String,String> headers = new HashMap<>();

    public HttpServletRequest(Socket socket) throws IOException {
        this.socket = socket;
        //1解析请求行
        parseRequestLine();
        //2解析消息头
        parseHeaders();
        //3解析消息正文
        parseContent();
    }
    //解析请求行
    private void parseRequestLine() throws IOException {
        String line = readLine();
        System.out.println(line);
        //[GET, /index.html, HTTP/1.1]
        String[] data = line.split("\\s");
        method = data[0];
        uri = data[1];
        protocol = data[2];
        System.out.println("method:"+method);//method:GET
        System.out.println("uri:"+uri);//uri:/index.html
        System.out.println("protocol:"+protocol);//protocol:HTTP/1.1
    }
    //解析消息头
    private void parseHeaders() throws IOException {
        while(true) {
            String line = readLine();
            if(line.isEmpty()){//返回值为空串说明单独读取到了空行
                break;
            }
            System.out.println("消息头:" + line);
            //将消息头按照"冒号空格"拆分并存入到headers中
            //data:[Host, localhost:8088]
            String[] data = line.split(":\\s");
            headers.put(data[0],data[1]);
        }
        System.out.println("headers:"+headers);
    }
    //解析消息正文
    private void parseContent(){}


    /**
     * 读取浏览器发送过来的一行字符串
     * @return
     */
    private String readLine() throws IOException {
        InputStream in = socket.getInputStream();
        int d;
        StringBuilder builder = new StringBuilder();
        //pre:表示上次读取的字符,cur表示本次读取的字符
        char pre='a',cur='a';
        while( (d = in.read()) != -1    ){
            cur = (char)d;//本次读取的字符
            //是否连续读取到了回车+换行
            if(pre==13 && cur==10){
                break;//停止读取,一行已经读取完毕了
            }
            builder.append(cur);//将本次读取的字符拼接
            pre = cur;//在下次读取前,将本次读取的字符记作上次读取的字符
        }
        return builder.toString().trim();
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    /**
     * 获取指定的消息头
     * @param name  消息头的名字
     * @return  消息头对应的值
     */
    public String getHeader(String name) {
        return headers.get(name);
    }
}
