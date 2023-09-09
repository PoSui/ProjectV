package com.birdboot.core;

import com.birdboot.annotation.Controller;
import com.birdboot.annotation.RequestMapping;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HandlerMapping {
/*
    MAP
    key:路径
    value:具体方法
 */
    private static Map<String, Method> mapping = new HashMap<>();

    static{
        initMapping();
    }

    private static void initMapping(){
        try {
            File baseDir = new File(
                    Objects.requireNonNull(HandlerMapping.class.getClassLoader().getResource(".")).toURI()
            );
            File dir = new File(baseDir,"com/birdboot/controller");
            File[] fArr = dir.listFiles(f->f.getName().endsWith(".class"));
            if(fArr != null){
                for(File f : fArr){
                    String classname = f.getName().split("\\.")[0];
                    Class<?> cls = Class.forName("com.birdboot.controller."+classname);
                    if(cls.isAnnotationPresent(Controller.class)){
                        Method[] methods = cls.getMethods();
                        for(Method m : methods){
                            if(m.isAnnotationPresent(RequestMapping.class)){
//                                if(path.equals(m.getAnnotation(RequestMapping.class).value())){
//                                    Object obj = cls.newInstance();
//                                    m.invoke(obj,request,response);
//                                    return;
//                                }
                                String value = m.getAnnotation(RequestMapping.class).value();
                                //将路径为key,方法为value存入key
                                mapping.put(value,m);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public static Method getMethod(String path){
        return mapping.get(path);
    }


    public static void main(String[] args) {
        Method m = mapping.get("/loginUser");
        System.out.println(m);
    }


}
