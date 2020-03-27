package com.xus.learning.thread;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author 青越 2020/02/04
 */
public class ThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        Future<String> future = pool.submit(new MyCallable());
        if (!future.isDone()) {
            System.out.println("not finished wait");
        }
        System.out.println("res; " + future.get());

    }
}
