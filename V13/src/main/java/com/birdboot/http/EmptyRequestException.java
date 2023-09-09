package com.birdboot.http;
/*
    在浏览器发送一个空请求时,由于http协议要求健壮性,所以需要服务器自己判断是否
    request获取到了一个空的请求头,当空请求时,用try/catch特性,让request的获
    取异常,从而使得代码结束

        定义异常1.名称 2.继承 3.创建全部构造器  结束 少有逻辑

 */
public class EmptyRequestException extends Exception {
    public EmptyRequestException() {
    }

    public EmptyRequestException(String message) {
        super(message);
    }

    public EmptyRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyRequestException(Throwable cause) {
        super(cause);
    }

    public EmptyRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
