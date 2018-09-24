//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sword.core.utils.bean;



import com.sword.core.utils.PrimitiveUtils;
import com.sword.core.utils.TypeConverterUtils;
import javassist.*;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BeanCopier {
    private static final Logger logger = LoggerFactory.getLogger(BeanCopier.class);
    private static final String packageName = getPackageName(BeanCopier.class);
    private static final Map<Key, Copy> copierMap = new ConcurrentHashMap();
    public static String dumpClass = null;
    public static boolean logSource = false;

    public static <T> T copy(Object from, Class<T> toClass, String... ignorePropeties) {
        return copy(from, toClass, BeanCopier.CopyStrategy.CONTAIN_NULL, ignorePropeties);
    }

    public static <T> T copy(Object from, Class<T> toClass, BeanCopier.CopyStrategy strategy, String... ignorePropeties) {
        notNull(toClass, "toClass不能为空");

        try {
            T target = toClass.newInstance();
            copy(from, target, strategy, BeanCopier.NoMatchingRule.IGNORE, ignorePropeties);
            return target;
        } catch (Exception var5) {
            throw new RuntimeException(var5);
        }
    }

    public static void copy(Object from, Object to, String... ignorePropeties) {
        copy(from, to, BeanCopier.CopyStrategy.CONTAIN_NULL, BeanCopier.NoMatchingRule.IGNORE, ignorePropeties);
    }

    public static void copy(Object from, Object to, BeanCopier.CopyStrategy strategy, BeanCopier.NoMatchingRule noMatchingRule, String... ignorePropeties) {
        BeanCopier.Key key = getKey(notNull(from, "源对象不能为空"), notNull(to, "目标对象不能为空"), (BeanCopier.CopyStrategy)notNull(strategy, "拷贝策略不能为空"), ignorePropeties);
        BeanCopier.Copy copy = (BeanCopier.Copy)copierMap.get(key);
        if (copy == null) {
            Class var7 = BeanCopier.class;
            synchronized(BeanCopier.class) {
                copy = (BeanCopier.Copy)copierMap.get(key);
                if (copy == null) {
                    BeanCopier.Generator generator = new BeanCopier.Generator();
                    generator.setSource(from.getClass());
                    generator.setTarget(to.getClass());
                    generator.setIgnorePropeties(ignorePropeties);
                    generator.setNoMatchingRule(noMatchingRule);
                    generator.setStrategy(strategy);

                    try {
                        copy = (BeanCopier.Copy)generator.generate().newInstance();
                        copierMap.put(key, copy);
                    } catch (Exception var11) {
                        throw new RuntimeException(var11);
                    }
                }
            }
        }

        copy.copy(from, to);
    }

    private static BeanCopier.Key getKey(Object from, Object to, BeanCopier.CopyStrategy strategy, String[] ignoreProperties) {
        Class<?> fromClass = from.getClass();
        Class<?> toClass = to.getClass();
        return new BeanCopier.Key(fromClass, toClass, ignoreProperties);
    }

    private static String getPackageName(Class<?> clazz) {
        String className = clazz.getName();
        int lastDotIndex = className.lastIndexOf(".");
        return lastDotIndex != -1 ? className.substring(0, lastDotIndex) : "";
    }

    private static <T> T notNull(T obj, String message) {
        if (obj == null) {
            throw new NullPointerException(message);
        } else {
            return obj;
        }
    }

    private static <T> T notNull(T obj) {
        return notNull(obj, (String)null);
    }

    private static class Key {
        private Class<?> fromClass;
        private Class<?> toClass;
        private String[] ignoreProperties;
        private BeanCopier.CopyStrategy strategy;

        public Key(Class<?> fromClass, Class<?> toClass) {
            this.fromClass = fromClass;
            this.toClass = toClass;
        }

        public Key(Class<?> fromClass, Class<?> toClass, String[] ignoreProperties) {
            this.fromClass = fromClass;
            this.toClass = toClass;
            this.ignoreProperties = ignoreProperties;
        }

        public Key(Class<?> fromClass, Class<?> toClass, BeanCopier.CopyStrategy strategy, String[] ignoreProperties) {
            this.fromClass = fromClass;
            this.toClass = toClass;
            this.ignoreProperties = ignoreProperties;
            this.strategy = strategy;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (o != null && this.getClass() == o.getClass()) {
                BeanCopier.Key key;
                label41: {
                    key = (BeanCopier.Key)o;
                    if (this.fromClass != null) {
                        if (this.fromClass.equals(key.fromClass)) {
                            break label41;
                        }
                    } else if (key.fromClass == null) {
                        break label41;
                    }

                    return false;
                }

                if (!Arrays.equals(this.ignoreProperties, key.ignoreProperties)) {
                    return false;
                } else if (this.strategy != key.strategy) {
                    return false;
                } else {
                    if (this.toClass != null) {
                        if (!this.toClass.equals(key.toClass)) {
                            return false;
                        }
                    } else if (key.toClass != null) {
                        return false;
                    }

                    return true;
                }
            } else {
                return false;
            }
        }

        public int hashCode() {
            int result = this.fromClass != null ? this.fromClass.hashCode() : 0;
            result = 31 * result + (this.toClass != null ? this.toClass.hashCode() : 0);
            result = 31 * result + (this.ignoreProperties != null ? Arrays.hashCode(this.ignoreProperties) : 0);
            result = 31 * result + (this.strategy != null ? this.strategy.hashCode() : 0);
            return result;
        }
    }

    private static class Convertor {
        private String sourceName;
        private String readMethodName;
        private Class<?> targetType;

        private Convertor(Class<?> targetType, String sourceName, String readMethodName) {
            this.targetType = (Class) BeanCopier.notNull(targetType);
            this.sourceName = (String) BeanCopier.notNull(sourceName);
            this.readMethodName = (String) BeanCopier.notNull(readMethodName);
        }

        public String convert() {
            String f;
            if (this.targetType.isPrimitive()) {
                f = this.getPrimitiveFormat();
                return this.getterSource() + "." + f + "()";
            } else if (BeanCopier.Generator.isWrapClass(this.targetType)) {
                f = this.getWrapperFormat();
                return f + "(" + this.getterSource() + ")";
            } else {
                return null;
            }
        }

        private String getterSource() {
            return this.sourceName + "." + this.readMethodName + "()";
        }

        private String getPrimitiveFormat() {
            return this.targetType.getName() + "Value";
        }

        private String getWrapperFormat() {
            return this.targetType.getSimpleName() + ".valueOf";
        }
    }

    private static class Generator {
        private static final String SOURCE = "s";
        private static final String TARGET = "t";
        private static AtomicInteger classNameIndex = new AtomicInteger(1000);
        private Class<?> source;
        private Class<?> target;
        private String[] ignorePropeties;
        private BeanCopier.NoMatchingRule noMatchingRule;
        private BeanCopier.CopyStrategy strategy;
        private String beginSource;
        private List<String> propSources;
        private String endSources;

        private Generator() {
            this.ignorePropeties = new String[0];
            this.propSources = new ArrayList();
        }

        public static boolean isWrapClass(Class<?> clazz) {
            try {
                return ((Class)clazz.getField("TYPE").get((Object)null)).isPrimitive();
            } catch (Exception var2) {
                return false;
            }
        }

        public static boolean isWrapClass(Class<?> source, Class<?> target) {
            if (!target.isPrimitive()) {
                return false;
            } else {
                try {
                    return source.getField("TYPE").get((Object)null) == target;
                } catch (Exception var3) {
                    return false;
                }
            }
        }

        public void setStrategy(BeanCopier.CopyStrategy strategy) {
            this.strategy = strategy;
        }

        public void setSource(Class<?> source) {
            this.source = source;
        }

        public void setTarget(Class<?> target) {
            this.target = target;
        }

        public void setIgnorePropeties(String[] ignorePropeties) {
            this.ignorePropeties = ignorePropeties;
        }

        public void setNoMatchingRule(BeanCopier.NoMatchingRule noMatchingRule) {
            this.noMatchingRule = noMatchingRule;
        }

        private void generateBegin() {
            this.beginSource = "public void copy(Object s1 ,Object t1){\n";
            String convertSource = this.source.getName() + " " + "s" + " =(" + this.source.getName() + ")" + "s" + "1;\n";
            String convertTarget = this.target.getName() + " " + "t" + " =(" + this.target.getName() + ")" + "t" + "1;\n";
            this.beginSource = this.beginSource + convertSource + convertTarget;
        }

        private void generateEnd() {
            this.endSources = "\n}";
        }

        private void generateBody() {
            PropertyDescriptor[] getters = this.getPropertyDescriptors(this.source);
            PropertyDescriptor[] setters = this.getPropertyDescriptors(this.target);
            Map<String, PropertyDescriptor> getterMap = new HashMap();
            PropertyDescriptor[] var4 = getters;
            int var5 = getters.length;

            int var6;
            PropertyDescriptor setter;
            for(var6 = 0; var6 < var5; ++var6) {
                setter = var4[var6];
                getterMap.put(setter.getName(), setter);
            }

            var4 = setters;
            var5 = setters.length;

            for(var6 = 0; var6 < var5; ++var6) {
                setter = var4[var6];
                PropertyDescriptor getter = (PropertyDescriptor)getterMap.get(setter.getName());
                if (this.checkCanGenSource(setter, getter)) {
                    Method readMethod = getter.getReadMethod();
                    Method writeMethod = setter.getWriteMethod();
                    String readMethodName = readMethod.getName();
                    String writerMethodName = writeMethod.getName();
                    Class<?> getterPropertyType = getter.getPropertyType();
                    if (this.compatible(getter, setter)) {
                        if (this.strategy == BeanCopier.CopyStrategy.IGNORE_NULL && !getterPropertyType.isPrimitive()) {
                            String source = this.genCheckWrapperIsNotNullSource(readMethod.getName());
                            source = source + "\t" + this.genPropertySource(writerMethodName, "s." + readMethodName + "()");
                            this.propSources.add(source);
                        } else {
                            this.propSources.add(this.genPropertySource(writerMethodName, "s." + readMethodName + "()"));
                        }
                    } else {
                        Class<?> setterPropertyType = setter.getPropertyType();
                        String f;
                        if (this.compatibleWrapper(getter, setter)) {
                            BeanCopier.Convertor convert = new BeanCopier.Convertor(setterPropertyType, "s", readMethod.getName());
                            f = convert.convert();
                            if (f != null) {
                                if (isWrapClass(getterPropertyType)) {
                                    String source = this.genCheckWrapperIsNotNullSource(readMethod.getName());
                                    source = source + "\t" + this.genPropertySource(writerMethodName, f);
                                    this.propSources.add(source);
                                } else {
                                    this.propSources.add(this.genPropertySource(writerMethodName, f));
                                }
                            } else {
                                this.warnCantConvert(setter, getter);
                            }
                        } else {
                            String typeName = this.buildTypeName(setter, false);
                            f = "t." + writerMethodName + "(" + (setterPropertyType.isPrimitive() ? PrimitiveUtils.class.getName() + ".value((" + PrimitiveUtils.getWrapperClass(setterPropertyType.getName()).getName() + ") " + TypeConverterUtils.class.getName() + ".convertValue(" + (getterPropertyType.isPrimitive() ? PrimitiveUtils.class.getName() + ".value(" + "s" + "." + readMethodName + "())" : this.buildTypeName(getter, false) + ".class.cast(" + "s" + "." + readMethodName + "())") + ", " + typeName + ".class)));\n" : "(" + typeName + ") " + typeName + ".class.cast( " + TypeConverterUtils.class.getName() + ".convertValue(" + (getterPropertyType.isPrimitive() ? PrimitiveUtils.class.getName() + ".value(" + "s" + "." + readMethodName + "())" : this.buildTypeName(getter, false) + ".class.cast(" + "s" + "." + readMethodName + "())") + ", " + typeName + ".class)));\n");
                            StringBuilder sb = new StringBuilder(400);
                            switch(this.noMatchingRule) {
                                case IGNORE:
                                    sb.append("try {\n");
                                    sb.append(f);
                                    sb.append("} catch (java.lang.ClassCastException e) {\n");
                                    sb.append("\n");
                                    sb.append("}\n");
                                    break;
                                case EXCEPTION:
                                    sb.append(f);
                                    break;
                                default:
                                    throw new InternalError();
                            }

                            this.propSources.add(sb.toString());
                        }
                    }
                }
            }

        }

        private String buildTypeName(PropertyDescriptor setter, boolean isPrimitive) {
            String typeName;
            if (setter.getPropertyType().isArray()) {
                int dn = this.countDimension(setter.getPropertyType());
                typeName = setter.getPropertyType().getComponentType().getName();

                for(int i = 0; i < dn; ++i) {
                    typeName = typeName + "[]";
                }
            } else {
                typeName = setter.getPropertyType().getName();
            }

            return typeName;
        }

        private int countDimension(Class<?> arrayClass) {
            int c = 0;

            for(Class comp = arrayClass; comp != null && comp.isArray(); comp = comp.getComponentType()) {
                ++c;
            }

            return c;
        }

        private String genCheckWrapperIsNotNullSource(String readName) {
            return "if(s." + readName + "()!=null)\n";
        }

        private String genPropertySource(String writerMethodName, String getterSource) {
            return "t." + writerMethodName + "(" + getterSource + ");\n";
        }

        private void warnCantConvert(PropertyDescriptor setter, PropertyDescriptor getter) {
            BeanCopier.logger.debug("[对象属性复制]属性类型转换失败{}.{}({})->{}.{}({})", new Object[]{getter.getReadMethod().getDeclaringClass().getSimpleName(), getter.getName(), getter.getPropertyType(), setter.getWriteMethod().getDeclaringClass().getSimpleName(), setter.getName(), setter.getPropertyType()});
        }

        private boolean checkCanGenSource(PropertyDescriptor setter, PropertyDescriptor getter) {
            if (this.ignorePropeties != null && this.isIgnoredProperty(setter)) {
                return false;
            } else if (getter == null) {
                BeanCopier.logger.debug("[对象属性复制]原对象[{}.{}]getter方法不存在", this.source.getCanonicalName(), setter.getName());
                return false;
            } else if (getter.getReadMethod() == null) {
                BeanCopier.logger.debug("[对象属性复制]原对象[{}.{}]getter方法不存在", this.source.getCanonicalName(), getter.getName());
                return false;
            } else if (setter.getWriteMethod() == null) {
                BeanCopier.logger.debug("[对象属性复制]目标对象[{}.{}]setter方法不存在", this.target.getCanonicalName(), setter.getName());
                return false;
            } else {
                return true;
            }
        }

        private boolean compatibleWrapper(PropertyDescriptor getter, PropertyDescriptor setter) {
            return isWrapClass(getter.getPropertyType(), setter.getPropertyType()) || isWrapClass(setter.getPropertyType(), getter.getPropertyType());
        }

        private boolean isIgnoredProperty(PropertyDescriptor descriptor) {
            String propertyName = descriptor.getName();
            if (ArrayUtils.isEmpty(this.ignorePropeties)) {
                return false;
            } else {
                String[] var3 = this.ignorePropeties;
                int var4 = var3.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    String ignorePropety = var3[var5];
                    if (ignorePropety.equals(propertyName)) {
                        return true;
                    }
                }

                return false;
            }
        }

        public Class<Copy> generate() {
            this.generateBegin();
            this.generateBody();
            this.generateEnd();
            StringBuilder sb = new StringBuilder();
            sb.append(this.beginSource);
            Iterator var2 = this.propSources.iterator();

            while(var2.hasNext()) {
                String propSource = (String)var2.next();
                sb.append(propSource);
            }

            sb.append(this.endSources);
            String source = sb.toString();
            if (BeanCopier.logSource) {
                BeanCopier.logger.info("\n{}", source);
            }

            ClassPool pool = ClassPool.getDefault();
            ClassClassPath classPath = new ClassClassPath(this.getClass());
            pool.insertClassPath(classPath);
            CtClass cc = pool.makeClass(BeanCopier.packageName + ".CopierImpl" + classNameIndex.incrementAndGet());
            Class copyClass = null;

            try {
                cc.addInterface(pool.get(BeanCopier.Copy.class.getName()));
                CtMethod m = CtNewMethod.make(source, cc);
                cc.addMethod(m);
                if (BeanCopier.dumpClass != null) {
                    CtClass.debugDump = BeanCopier.dumpClass;
                }

                ClassLoader classLoader = this.getDefaultClassLoader();
                BeanCopier.logger.debug("classloader:{}", classLoader);
                copyClass = cc.toClass(classLoader, (ProtectionDomain)null);
                return copyClass;
            } catch (Exception var9) {
                throw new RuntimeException(var9);
            }
        }

        private boolean compatible(PropertyDescriptor getter, PropertyDescriptor setter) {
            return setter.getPropertyType().isAssignableFrom(getter.getPropertyType());
        }

        private ClassLoader getDefaultClassLoader() {
            ClassLoader cl = null;

            try {
                cl = Thread.currentThread().getContextClassLoader();
            } catch (Throwable var4) {
                ;
            }

            if (cl == null) {
                cl = BeanCopier.class.getClassLoader();
                if (cl == null) {
                    try {
                        cl = ClassLoader.getSystemClassLoader();
                    } catch (Throwable var3) {
                        ;
                    }
                }
            }

            return cl;
        }

        public PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) {
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
                return beanInfo.getPropertyDescriptors();
            } catch (IntrospectionException var4) {
                throw new RuntimeException(var4);
            }
        }
    }

    public interface Copy {
        void copy(Object var1, Object var2);
    }

    public static enum NoMatchingRule {
        IGNORE,
        EXCEPTION;

        private NoMatchingRule() {
        }
    }

    public static enum CopyStrategy {
        IGNORE_NULL,
        CONTAIN_NULL;

        private CopyStrategy() {
        }
    }
}
