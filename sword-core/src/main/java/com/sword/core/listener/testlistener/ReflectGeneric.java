package com.sword.core.listener.testlistener;

public abstract class ReflectGeneric<T, M> implements ReflectGenericInterface {

    private Class<T> clazz;

    public ReflectGeneric() {
        Class clazz = ReflectTest.getSuperClassGenricType(this.getClass());
        System.out.println(clazz);
    }

}
