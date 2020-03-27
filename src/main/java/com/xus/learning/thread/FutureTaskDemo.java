package com.xus.learning.thread;

import java.util.concurrent.*;

/**
 * @author 青越 2020/02/04
 */
public class FutureTaskDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<String>(new MyCallable());
        Thread t = new Thread(futureTask);
        t.start();
        if (!futureTask.isDone()) {
            System.out.println("not finish wait");
        }
        System.out.println("res: " + futureTask.get());
        ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();
    }
}
