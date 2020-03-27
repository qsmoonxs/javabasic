package com.xus.learning.thread;

/**
 * @author 青越 2020/02/03
 */
public class ThreadDemo {

    public static void main(String[] args) {
        MyThread myThread = new MyThread("thread1");
        MyThread myThread2 = new MyThread("thread2");
        MyThread myThread3 = new MyThread("thread3");
        myThread.start();
        myThread2.start();
        myThread3.start();

    }
}
