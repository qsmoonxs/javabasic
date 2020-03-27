package com.xus.learning.thread;

/**
 * @author 青越 2020/02/03
 */
public class MyThread extends Thread {

    private String name;

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("thread start: " + this.name + " , i= "+ i);
        }
    }
}
