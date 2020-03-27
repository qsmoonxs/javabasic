package com.xus.learning.thread;

/**
 * @author 青越 2020/02/03
 */
public class ThreadTest {

    private static void attack(){
        System.out.println("Fight");
        System.out.println("current thread:" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        Thread t = new Thread() {
            @Override
            public void run(){
                attack();
            }
        };
        t.start();
    }
}
