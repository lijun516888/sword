//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sword.core.utils;

import java.util.HashMap;
import java.util.Map;

public abstract class PrimitiveUtils {
    public static final int[] EMPTY_INTS = new int[0];
    public static final long[] EMPTY_LONGS = new long[0];
    public static final short[] EMPTY_SHORTS = new short[0];
    public static final float[] EMPTY_FLOATS = new float[0];
    public static final double[] EMPTY_DOUBLES = new double[0];
    public static final byte[] EMPTY_BYTES = new byte[0];
    public static final char[] EMPTY_CHARS = new char[0];
    public static final boolean[] EMPTY_BOOLEANS = new boolean[0];
    public static final Integer[] EMPTY_INT_WRAPPERS = new Integer[0];
    public static final Long[] EMPTY_LONG_WRAPPERS = new Long[0];
    public static final Short[] EMPTY_SHORT_WRAPPERS = new Short[0];
    public static final Float[] EMPTY_FLOAT_WRAPPERS = new Float[0];
    public static final Double[] EMPTY_DOUBLE_WRAPPERS = new Double[0];
    public static final Byte[] EMPTY_BYTE_WRAPPERS = new Byte[0];
    public static final Character[] EMPTY_CHAR_WRAPPERS = new Character[0];
    public static final Boolean[] EMPTY_BOOLEAN_WRAPPERS = new Boolean[0];
    private static final Map<String, TypeHolder> PRIMITIVES = new HashMap();

    public PrimitiveUtils() {
    }

    public static short value(Short value) {
        return value == null ? 0 : value;
    }

    public static int value(Integer value) {
        return value == null ? 0 : value;
    }

    public static long value(Long value) {
        return value == null ? 0L : value;
    }

    public static float value(Float value) {
        return value == null ? 0.0F : value;
    }

    public static double value(Double value) {
        return value == null ? 0.0D : value;
    }

    public static byte value(Byte value) {
        return value == null ? 0 : value;
    }

    public static boolean value(Boolean value) {
        return value == null ? false : value;
    }

    public static char value(Character value) {
        return value == null ? '\u0000' : value;
    }

    public static Short value(short value) {
        return value;
    }

    public static Integer value(int value) {
        return value;
    }

    public static Long value(long value) {
        return value;
    }

    public static Float value(float value) {
        return value;
    }

    public static Double value(double value) {
        return value;
    }

    public static Byte value(byte value) {
        return value;
    }

    public static Boolean value(boolean value) {
        return value;
    }

    public static Character value(char value) {
        return value;
    }

    public static int[] values(Integer[] values) {
        if (values == null) {
            return EMPTY_INTS;
        } else {
            int[] intValues = new int[values.length];

            for(int i = 0; i < values.length; ++i) {
                intValues[i] = value(values[i]);
            }

            return intValues;
        }
    }

    public static long[] values(Long[] values) {
        if (values == null) {
            return EMPTY_LONGS;
        } else {
            long[] longValues = new long[values.length];

            for(int i = 0; i < values.length; ++i) {
                longValues[i] = value(values[i]);
            }

            return longValues;
        }
    }

    public static short[] values(Short[] values) {
        if (values == null) {
            return EMPTY_SHORTS;
        } else {
            short[] shortValues = new short[values.length];

            for(int i = 0; i < values.length; ++i) {
                shortValues[i] = value(values[i]);
            }

            return shortValues;
        }
    }

    public static float[] values(Float[] values) {
        if (values == null) {
            return EMPTY_FLOATS;
        } else {
            float[] floatValues = new float[values.length];

            for(int i = 0; i < values.length; ++i) {
                floatValues[i] = value(values[i]);
            }

            return floatValues;
        }
    }

    public static double[] values(Double[] values) {
        if (values == null) {
            return EMPTY_DOUBLES;
        } else {
            double[] doubleValues = new double[values.length];

            for(int i = 0; i < values.length; ++i) {
                doubleValues[i] = value(values[i]);
            }

            return doubleValues;
        }
    }

    public static boolean[] values(Boolean[] values) {
        if (values == null) {
            return EMPTY_BOOLEANS;
        } else {
            boolean[] booleanValues = new boolean[values.length];

            for(int i = 0; i < values.length; ++i) {
                booleanValues[i] = value(values[i]);
            }

            return booleanValues;
        }
    }

    public static byte[] values(Byte[] values) {
        if (values == null) {
            return EMPTY_BYTES;
        } else {
            byte[] byteValues = new byte[values.length];

            for(int i = 0; i < values.length; ++i) {
                byteValues[i] = value(values[i]);
            }

            return byteValues;
        }
    }

    public static char[] values(Character[] values) {
        if (values == null) {
            return EMPTY_CHARS;
        } else {
            char[] charValues = new char[values.length];

            for(int i = 0; i < values.length; ++i) {
                charValues[i] = value(values[i]);
            }

            return charValues;
        }
    }

    public static Integer[] values(int[] values) {
        if (values == null) {
            return EMPTY_INT_WRAPPERS;
        } else {
            Integer[] integerValues = new Integer[values.length];

            for(int i = 0; i < values.length; ++i) {
                integerValues[i] = values[i];
            }

            return integerValues;
        }
    }

    public static Long[] values(long[] values) {
        if (values == null) {
            return EMPTY_LONG_WRAPPERS;
        } else {
            Long[] longValues = new Long[values.length];

            for(int i = 0; i < values.length; ++i) {
                longValues[i] = values[i];
            }

            return longValues;
        }
    }

    public static Short[] values(short[] values) {
        if (values == null) {
            return EMPTY_SHORT_WRAPPERS;
        } else {
            Short[] shortValues = new Short[values.length];

            for(int i = 0; i < values.length; ++i) {
                shortValues[i] = values[i];
            }

            return shortValues;
        }
    }

    public static Float[] values(float[] values) {
        if (values == null) {
            return EMPTY_FLOAT_WRAPPERS;
        } else {
            Float[] floatValues = new Float[values.length];

            for(int i = 0; i < values.length; ++i) {
                floatValues[i] = values[i];
            }

            return floatValues;
        }
    }

    public static Double[] values(double[] values) {
        if (values == null) {
            return EMPTY_DOUBLE_WRAPPERS;
        } else {
            Double[] doubleValues = new Double[values.length];

            for(int i = 0; i < values.length; ++i) {
                doubleValues[i] = values[i];
            }

            return doubleValues;
        }
    }

    public static Boolean[] values(boolean[] values) {
        if (values == null) {
            return EMPTY_BOOLEAN_WRAPPERS;
        } else {
            Boolean[] booleanValues = new Boolean[values.length];

            for(int i = 0; i < values.length; ++i) {
                booleanValues[i] = values[i];
            }

            return booleanValues;
        }
    }

    public static Byte[] values(byte[] values) {
        if (values == null) {
            return EMPTY_BYTE_WRAPPERS;
        } else {
            Byte[] byteValues = new Byte[values.length];

            for(int i = 0; i < values.length; ++i) {
                byteValues[i] = values[i];
            }

            return byteValues;
        }
    }

    public static Character[] values(char[] values) {
        if (values == null) {
            return EMPTY_CHAR_WRAPPERS;
        } else {
            Character[] characterValues = new Character[values.length];

            for(int i = 0; i < values.length; ++i) {
                characterValues[i] = values[i];
            }

            return characterValues;
        }
    }

    public static Class<?> getPrimitiveClass(String primitiveName) {
        PrimitiveUtils.TypeHolder typeHolder = (PrimitiveUtils.TypeHolder)PRIMITIVES.get(primitiveName);
        return typeHolder == null ? null : typeHolder.getType();
    }

    public static Class<?> getWrapperClass(String primitiveName) {
        PrimitiveUtils.TypeHolder typeHolder = (PrimitiveUtils.TypeHolder)PRIMITIVES.get(primitiveName);
        return typeHolder == null ? null : typeHolder.getWrapper();
    }

    public static Class<?>[] getAllPrimitiveClasses() {
        return new Class[]{Byte.TYPE, Character.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Boolean.TYPE};
    }

    public static Class<?>[] getAllPrimitiveArrayClasses() {
        return new Class[]{byte[].class, char[].class, short[].class, int[].class, long[].class, float[].class, double[].class, boolean[].class};
    }

    public static Class<?>[] getAllWrapperClasses() {
        return new Class[]{Byte.class, Character.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Boolean.class};
    }

    public static Class<?>[] getAllWrapperArrayClasses() {
        return new Class[]{Byte[].class, Character[].class, Short[].class, Integer[].class, Long[].class, Float[].class, Double[].class, Boolean[].class};
    }

    static {
        PRIMITIVES.put(Byte.TYPE.getName(), new PrimitiveUtils.ByteTypeHolder());
        PRIMITIVES.put(Character.TYPE.getName(), new PrimitiveUtils.CharTypeHolder());
        PRIMITIVES.put(Short.TYPE.getName(), new PrimitiveUtils.ShortTypeHolder());
        PRIMITIVES.put(Integer.TYPE.getName(), new PrimitiveUtils.IntTypeHolder());
        PRIMITIVES.put(Long.TYPE.getName(), new PrimitiveUtils.LongTypeHolder());
        PRIMITIVES.put(Float.TYPE.getName(), new PrimitiveUtils.FloatTypeHolder());
        PRIMITIVES.put(Double.TYPE.getName(), new PrimitiveUtils.DoubleTypeHolder());
        PRIMITIVES.put(Boolean.TYPE.getName(), new PrimitiveUtils.BooleanTypeHolder());
    }

    private static class BooleanTypeHolder implements PrimitiveUtils.TypeHolder {
        private BooleanTypeHolder() {
        }

        public Class<?> getType() {
            return Boolean.TYPE;
        }

        public Class<?> getWrapper() {
            return Boolean.class;
        }
    }

    private static class DoubleTypeHolder implements PrimitiveUtils.TypeHolder {
        private DoubleTypeHolder() {
        }

        public Class<?> getType() {
            return Double.TYPE;
        }

        public Class<?> getWrapper() {
            return Double.class;
        }
    }

    private static class FloatTypeHolder implements PrimitiveUtils.TypeHolder {
        private FloatTypeHolder() {
        }

        public Class<?> getType() {
            return Float.TYPE;
        }

        public Class<?> getWrapper() {
            return Float.class;
        }
    }

    private static class LongTypeHolder implements PrimitiveUtils.TypeHolder {
        private LongTypeHolder() {
        }

        public Class<?> getType() {
            return Long.TYPE;
        }

        public Class<?> getWrapper() {
            return Long.class;
        }
    }

    private static class IntTypeHolder implements PrimitiveUtils.TypeHolder {
        private IntTypeHolder() {
        }

        public Class<?> getType() {
            return Integer.TYPE;
        }

        public Class<?> getWrapper() {
            return Integer.class;
        }
    }

    private static class ShortTypeHolder implements PrimitiveUtils.TypeHolder {
        private ShortTypeHolder() {
        }

        public Class<?> getType() {
            return Short.TYPE;
        }

        public Class<?> getWrapper() {
            return Short.class;
        }
    }

    private static class CharTypeHolder implements PrimitiveUtils.TypeHolder {
        private CharTypeHolder() {
        }

        public Class<?> getType() {
            return Character.TYPE;
        }

        public Class<?> getWrapper() {
            return Character.class;
        }
    }

    private static class ByteTypeHolder implements PrimitiveUtils.TypeHolder {
        private ByteTypeHolder() {
        }

        public Class<?> getType() {
            return Byte.TYPE;
        }

        public Class<?> getWrapper() {
            return Byte.class;
        }
    }

    private interface TypeHolder {
        Class<?> getType();

        Class<?> getWrapper();
    }
}
