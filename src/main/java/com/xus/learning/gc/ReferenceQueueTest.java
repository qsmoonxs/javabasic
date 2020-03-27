package com.xus.learning.gc;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * @author 青越 2020/02/03
 */
public class ReferenceQueueTest {
    private static ReferenceQueue<NormalObject> referenceQueue = new ReferenceQueue<NormalObject>();

    private static void checkQueue() {
        Reference<NormalObject> ref;
        while ((ref = (Reference<NormalObject>)referenceQueue.poll()) != null){
            System.out.println("in queue:" + ((NormalObjectWeakReference)(ref)).name);
            System.out.println("reference object: " + ref.get());
        }
    }

    public static void main(String[] args) {
        ArrayList<WeakReference<NormalObject>> weakList = new ArrayList<WeakReference<NormalObject>>();
        for (int i = 0; i < 3; i++) {
            weakList.add(new NormalObjectWeakReference(new NormalObject("weak " + i), referenceQueue));
            System.out.println("Create weak: " + weakList.get(i));
        }
        System.out.println("first time");
        checkQueue();
        System.gc();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("second time");
        checkQueue();
    }
}
