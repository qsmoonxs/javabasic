package com.xus.learning.thread;

/**
 * @author 青越 2020/02/03
 */
public class RunnableDemo {
    public static void main(String[] args) throws InterruptedException {
        MyRunnable mr1 = new MyRunnable("r1");
        MyRunnable mr2 = new MyRunnable("r2");
        MyRunnable mr3 = new MyRunnable("r3");
        Thread t1 = new Thread(mr1);
        Thread t2 = new Thread(mr2);
        Thread t3 = new Thread(mr3);
        t1.start();
        t2.start();
        t2.join();
        System.out.println("thread2 done");
        t3.start();
    }
}
