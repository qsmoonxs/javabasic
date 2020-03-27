package com.xus.learning.thread;

/**
 * @author 青越 2020/02/04
 */
public class InterruptDemo {

    public static void main(String[] args) {
        Thread thread1 = new Thread() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("thread interrupt current states :" + Thread.currentThread().getState());
                    e.printStackTrace();
                }
            }
        };
        thread1.start();
        thread1.interrupt();
    }
}
