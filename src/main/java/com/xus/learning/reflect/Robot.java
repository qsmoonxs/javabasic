package com.xus.learning.reflect;

/**
 * @author 青越 2020/02/01
 */
public class Robot extends Person{
    private String name;
    public String say(String tag) {
        return "say " + tag;
    }
    private String say2() {
        return "hello " + name;
    }
}
