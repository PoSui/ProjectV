package com.birdboot.test;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;

/*
        用api测试请求头的contenttype内容

     */
public class ContentTypeDemo {
    public static void main(String[] args) {
        MimetypesFileTypeMap mtft = new MimetypesFileTypeMap();
        File f = new File("test.java");
        System.out.println(mtft.getContentType(f));
    }
}
