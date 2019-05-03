package com.sword.core.listener.testlistener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectTest extends ReflectGeneric<Long, String> {

    public static void main(String[] args) {
        ReflectGeneric r = new ReflectTest();
        System.out.println(ReflectTest.getGenericClass(ReflectTest.class));
    }

    public static Class getGenericClass(Class clazz) {
        return getGenericClass(clazz, 0);
    }

    public static Class getGenericClass(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (genType instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
            if (params != null && params.length >= index - 1) {
                return (Class)params[index];
            }
        }

        return null;
    }

    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    public static Class getSuperClassGenricType(Class clazz, int index) throws IndexOutOfBoundsException {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        } else {
            Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
            if (index < params.length && index >= 0) {
                Type type = params[index];
                if (type instanceof ParameterizedType) {
                    return (Class)((ParameterizedType)type).getRawType();
                } else {
                    return !(params[index] instanceof Class) ? Object.class : (Class)params[index];
                }
            } else {
                return Object.class;
            }
        }
    }

}
