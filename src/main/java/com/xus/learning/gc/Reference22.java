package com.xus.learning.gc;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.WeakHashMap;

/**
 * Java 四种引用
 * @author 青越 2020/01/08
 */
public class Reference22 {
    public static void main(String[] args) throws InterruptedException {
        Object o = new Object();
       // SoftReference<Object> weakR = new SoftReference<Object>(o);
       // byte[] bytes = new byte[1024*100];

       // System.gc();
        //WeakReference<Object> weakReference = new WeakReference<Object>(o); //weakHashMap

        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<Object>();
        PhantomReference<Object> phantomReference = new PhantomReference<Object>(o, referenceQueue);
       // o = null;
        //System.gc();
        //System.out.println(phantomReference.get());
//        System.out.println(referenceQueue.poll());
//        WeakHashMap weakHashMap = new WeakHashMap();
//        weakHashMap.put("key", new Object());
//        o.wait();
        int a = new BigDecimal(1000000000).multiply(new BigDecimal(100)).intValue();
        System.out.println(a);

    }
}
