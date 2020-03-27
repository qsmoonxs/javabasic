package com.xus.learning.juc;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 青越 2020/02/13
 */
public class ReentrantLockDemo {
    private static  ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    public static void main(String[] args) {


        /*for (int i = 0; i < 5; i ++) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    ReentrantLock reentrantLock = new ReentrantLock();
                    reentrantLock.lock();
                    try {
                        Thread.sleep(2000);
                        System.out.println(Thread.currentThread().getName());
                    } catch (Exception e) {

                    } finally {
                        reentrantLock.unlock();
                    }
                }
            });
            t.start();
        }*/



        for (int i = 0; i < 5; i ++) {
            Thread t = new Thread(new Runnable() {
                public void run() {

                    reentrantReadWriteLock.readLock().lock();
                    try {
                        Thread.sleep(2000);
                        System.out.println(Thread.currentThread().getName());
                    } catch (Exception e) {

                    } finally {
                        reentrantReadWriteLock.readLock().unlock();
                    }
                }
            });
            t.start();
        }

    }
}
