package com.birdboot.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class URLDecoderDemo {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "username=%E5%A4%A7%E6%8B%89%E7%9A%AE&password=123&nickname=1&age=33";
        System.out.println(URLDecoder.decode(s,"utf-8"));
    }
}
