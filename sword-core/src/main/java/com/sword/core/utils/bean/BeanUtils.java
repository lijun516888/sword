//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sword.core.utils.bean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanUtils {
    public BeanUtils() {
    }

    public static Object getPrivateProperty(Object object, String propertyName) throws IllegalAccessException, NoSuchFieldException {
        Assert.notNull(object);
        Assert.hasText(propertyName);
        Field field = object.getClass().getDeclaredField(propertyName);
        field.setAccessible(true);
        return field.get(object);
    }

    public static void setPrivateProperty(Object object, String propertyName, Object newValue) throws IllegalAccessException, NoSuchFieldException {
        Assert.notNull(object);
        Assert.hasText(propertyName);
        Field field = object.getClass().getDeclaredField(propertyName);
        field.setAccessible(true);
        field.set(object, newValue);
    }

    public static Object invokePrivateMethod(Object object, String methodName, Object[] params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Assert.notNull(object);
        Assert.hasText(methodName);
        Class[] types = new Class[params.length];

        for(int i = 0; i < params.length; ++i) {
            types[i] = params[i].getClass();
        }

        Method method = object.getClass().getDeclaredMethod(methodName, types);
        method.setAccessible(true);
        return method.invoke(object, params);
    }

    public static Object invokePrivateMethod(Object object, String methodName, Object param) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return invokePrivateMethod(object, methodName, new Object[]{param});
    }

    public static Field getDeclaredField(Object object, String propertyName) throws NoSuchFieldException {
        Assert.notNull(object);
        Assert.hasText(propertyName);
        return getDeclaredField(object.getClass(), propertyName);
    }

    public static Field getDeclaredField(Class clazz, String propertyName) throws NoSuchFieldException {
        Assert.notNull(clazz);
        Assert.hasText(propertyName);
        Class superClass = clazz;

        while(superClass != Object.class) {
            try {
                return superClass.getDeclaredField(propertyName);
            } catch (NoSuchFieldException var4) {
                superClass = superClass.getSuperclass();
            }
        }

        throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + propertyName);
    }

    public static Object forceGetProperty(Object object, String propertyName) throws NoSuchFieldException {
        Assert.notNull(object);
        Assert.hasText(propertyName);
        Field field = getDeclaredField(object, propertyName);
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        Object result = null;

        try {
            result = field.get(object);
        } catch (IllegalAccessException var6) {
            ;
        }

        field.setAccessible(accessible);
        return result;
    }

    public static void forceSetProperty(Object object, String propertyName, Object newValue) throws NoSuchFieldException {
        Assert.notNull(object);
        Assert.hasText(propertyName);
        Field field = getDeclaredField(object, propertyName);
        boolean accessible = field.isAccessible();
        field.setAccessible(true);

        try {
            field.set(object, newValue);
        } catch (IllegalAccessException var6) {
            ;
        }

        field.setAccessible(accessible);
    }

    public static Object getDeclaredProperty(Object object, String propertyName) throws IllegalAccessException, NoSuchFieldException {
        Assert.notNull(object);
        Assert.hasText(propertyName);
        Field field = getDeclaredField(object, propertyName);
        return getDeclaredProperty(object, field);
    }

    public static Object getDeclaredProperty(Object object, Field field) throws IllegalAccessException {
        Assert.notNull(object);
        Assert.notNull(field);
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        Object result = field.get(object);
        field.setAccessible(accessible);
        return result;
    }

    public static void setDeclaredProperty(Object object, String propertyName, Object newValue) throws IllegalAccessException, NoSuchFieldException {
        Assert.notNull(object);
        Assert.hasText(propertyName);
        Field field = object.getClass().getDeclaredField(propertyName);
        setDeclaredProperty(object, field, newValue);
    }

    public static void setDeclaredProperty(Object object, Field field, Object newValue) throws IllegalAccessException {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        field.set(object, newValue);
        field.setAccessible(accessible);
    }

    public static List<Field> getFieldsByType(Object object, Class type) {
        ArrayList<Field> list = new ArrayList();
        Field[] fields = object.getClass().getDeclaredFields();
        Field[] var4 = fields;
        int var5 = fields.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Field field = var4[var6];
            if (field.getType().isAssignableFrom(type)) {
                list.add(field);
            }
        }

        return list;
    }

    public static String getAccessorName(Class type, String fieldName) {
        Assert.hasText(fieldName, "FieldName required");
        Assert.notNull(type, "Type required");
        return "boolean".equals(type.getName()) ? "is" + StringUtils.capitalize(fieldName) : "get" + StringUtils.capitalize(fieldName);
    }

    public static Method getAccessor(Class type, String fieldName) {
        try {
            return type.getMethod(getAccessorName(type, fieldName));
        } catch (NoSuchMethodException var3) {
            return null;
        }
    }

    public static void setProperty(Object object, String name, Object value) throws IllegalAccessException, NoSuchFieldException {
        String[] properties = getProperties(object);
        String[] var4 = properties;
        int var5 = properties.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String p = var4[var6];
            if (p.equalsIgnoreCase(name)) {
                setDeclaredProperty(object, p, value);
                break;
            }
        }

    }

    public static String[] getProperties(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        String[] properties = new String[fields.length];
        int i = 0;
        Field[] var4 = fields;
        int var5 = fields.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Field field = var4[var6];
            properties[i++] = field.getName();
        }

        return properties;
    }

    public static Class getPropertyType(Class type, String name) throws NoSuchFieldException {
        return getDeclaredField(type, name).getType();
    }
}
