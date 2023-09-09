package com.birdboot.test;

/**
 * 单例模式
 * java有23种设计模式,每种模式解决一种特定的业务场景问题
 *
 * 单利模式:使用该模式设计的类全局只有一个实例
 * 1:私有化构造器(目的:避免外界肆意实例化)
 * 2:定义私有的静态的当前类属性,并实例化一次(控制实例仅有1个)
 * 3:提供公开的静态的获取当前类实例的方法,并将第二步属性返回
 */
public class Singleton {
    private static Singleton instance = new Singleton();

    private Singleton(){}

    public static Singleton getInstance() {
        return instance;
    }
}





