package com.birdboot.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class A {
    void process() throws Exception{
        throw new Exception();
    }
    public static void main(String[] args) throws Exception {
    A a = new B();
    a.process();
    }
}
class B extends A{
    void process(){
        System.out.println("B");
    }
}
