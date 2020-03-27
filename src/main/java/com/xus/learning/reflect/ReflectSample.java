package com.xus.learning.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 青越 2020/02/01
 */
public class ReflectSample {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Class robot = Class.forName("com.xus.learning.reflect.Robot");
        Object r = robot.newInstance();

        Method method = robot.getMethod("say", String.class);
        System.out.println(method.invoke(r, "xus"));

        Method methodP = robot.getMethod("p");
        System.out.println(methodP.invoke(r));

        Method methodP2 = robot.getSuperclass().getDeclaredMethod("p2");
        methodP2.setAccessible(true);
        System.out.println(methodP2.invoke(r));

        System.out.println(robot.getSuperclass().getSuperclass() == Object.class);

        Field field = robot.getDeclaredField("name");
        field.setAccessible(true);
        field.set(r, "xus");

        Method method1 = robot.getDeclaredMethod("say2");
        method1.setAccessible(true);
        System.out.println(method1.invoke(r));
    }
}
