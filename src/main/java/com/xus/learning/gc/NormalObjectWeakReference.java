package com.xus.learning.gc;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author 青越 2020/02/03
 */
public class NormalObjectWeakReference extends WeakReference<NormalObject> {

    public String name;

    public NormalObjectWeakReference(NormalObject referent, ReferenceQueue<NormalObject> queue) {
        super(referent, queue);
        this.name = referent.name;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalizing NormalObjectWeakReference : " + name);
        super.finalize();
    }
}
