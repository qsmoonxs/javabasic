package com.xus.learning.gc;

/**
 * @author 青越 2020/02/03
 */
public class NormalObject {

    public String name;

    public NormalObject(String name) {
        this.name = name;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalizing obj: " + name);
        super.finalize();
    }
}
