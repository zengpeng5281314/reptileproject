package com.mytest.utils;

/**
 * 转换Object对象为基础类型
 */
public final class TypeConverter {

    private TypeConverter() {
    }

    /**
     * 将对象转换为 long。如果对象为null或为空则返回0.
     *
     * @param object
     * @return 返回转换后的值。对象为null或 "" 将返回默认值0
     * @throws java.lang.RuntimeException 转换失败将抛出异常。
     */
    public static long parseLong(Object object) {
        return parseLong(object, 0);
    }

    /**
     * 将对象转换为 long。如果对象为null或为空则返回0.
     * 。
     *
     * @param object
     * @param defaultValue
     * @return 对象为null或 "" 将返回默认值
     * @throws java.lang.RuntimeException 转换失败将抛出异常
     */
    public static long parseLong(Object object, long defaultValue) {

        if (object == null) {
            return defaultValue;
        }

        if (object instanceof Number) {
            Number number = (Number) object;
            return number.longValue();
        } else if (object instanceof String) {
            String value = (String) object;
            if (value.length() > 0) {
                return Long.parseLong(value);
            } else {
                return defaultValue;
            }
        } else {
            throw new RuntimeException(String.format("Can't convert %s to long", object.getClass()));
        }
    }

    /**
     * 将object 转换成字符串类型
     *
     * @param object
     * @return 对象为null时返回null.
     * @throws java.lang.RuntimeException 无法转换将抛出异常
     */
    public static String parseString(Object object) {
        if (object == null) {
            return null;
        }

        if (object instanceof String) {
            return (String) object;
        } else if (object instanceof Number) {
            return object.toString();
        } else {
            throw new RuntimeException(String.format("Can't convert %s to String", object.getClass()));
        }
    }

    /**
     * 将对象装换为int类型.
     *
     * @param object
     * @return 如果对象为null或 “” 则返回0.
     */
    public static int parseInt(Object object) {
        return parseInt(object, 0);
    }

    /**
     * 将对象转换为int类型值
     *
     * @param object
     * @param defaultValue 默认值
     * @return 当对象为 null 或 "" 返回默认值.
     * @throws java.lang.RuntimeException 无法转换时抛出异常
     */
    public static int parseInt(Object object, int defaultValue) {
        if (object == null) {
            return defaultValue;
        }

        if (object instanceof Number) {
            Number value = (Number) object;
            return value.intValue();
        } else if (object instanceof String) {
            String value = (String) object;
            if (value.length() > 0) {
                return Integer.parseInt(value);
            } else {
                return defaultValue;
            }
        } else {
            throw new RuntimeException(String.format("Can't convert %s to int", object.getClass()));
        }
    }

    /**
     * 将对象装换为double类型.
     *
     * @param object
     * @return 如果对象为null或 “” 则返回 0.
     */
    public static double parseDouble(Object object) {
        return parseDouble(object, 0);
    }

    /**
     * 将对象转换为double类型值
     *
     * @param object
     * @param defaultValue 默认值
     * @return 当对象为 null 或 "" 返回默认值.
     * @throws java.lang.RuntimeException 无法转换时抛出异常
     */
    public static double parseDouble(Object object, double defaultValue) {
        if (object == null) {
            return defaultValue;
        }

        if (object instanceof Number) {
            Number value = (Number) object;
            return value.doubleValue();
        } else if (object instanceof String) {
            String value = (String) object;
            if (value.length() > 0) {
                return Double.parseDouble(value);
            } else {
                return defaultValue;
            }
        } else {
            throw new RuntimeException(String.format("Can't convert %s to double.", object.getClass()));
        }
    }


}
