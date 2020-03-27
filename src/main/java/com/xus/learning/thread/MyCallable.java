package com.xus.learning.thread;

import java.util.concurrent.Callable;

/**
 * @author 青越 2020/02/04
 */
public class MyCallable implements Callable<String> {


    public String call() throws Exception {
        String value = "test";
        System.out.println("ready to work");
        Thread.sleep(5000);
        System.out.println("task done");
        return value;
    }
}
