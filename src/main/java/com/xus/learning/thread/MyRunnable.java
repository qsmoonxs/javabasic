package com.xus.learning.thread;

/**
 * @author 青越 2020/02/03
 */
public class MyRunnable implements Runnable {

    private String name;

    public MyRunnable(String name) {
        this.name = name;
    }

    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("thread start: " + this.name + " , i= "+ i);
        }
    }
}
