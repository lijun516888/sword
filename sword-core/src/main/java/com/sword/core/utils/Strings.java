//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sword.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class Strings extends StringUtils {
    private static final char UNDERLINE = '_';

    public static String camelToUnderline(String param) {
        if (param != null && !"".equals(param.trim())) {
            int len = param.length();
            StringBuilder sb = new StringBuilder(len);

            for(int i = 0; i < len; ++i) {
                char c = param.charAt(i);
                if (Character.isUpperCase(c)) {
                    sb.append('_');
                    sb.append(Character.toLowerCase(c));
                } else {
                    sb.append(c);
                }
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public static <T> T isBlankDefault(T text, T defaultValue) {
        if (text == null) {
            return defaultValue;
        } else if (text.getClass().isAssignableFrom(String.class)) {
            return isBlank((String)text) ? defaultValue : text;
        } else {
            return text;
        }
    }

    public static boolean matcher(String regex, String value) {
        Pattern p = Pattern.compile(regex);
        return p.matcher(value).matches();
    }

    public static String mask(String text, int preSize, int postSize) {
        return mask(text, preSize, postSize, '*');
    }

    public static String mask(String text, int preSize, int postSize, Character replaceChar) {
        String source = trimToEmpty(text);
        if (isBlank(source)) {
            return text;
        } else {
            if (replaceChar == null) {
                replaceChar = '*';
            }

            if (preSize + postSize >= text.length()) {
                return leftPad("", text.length(), replaceChar);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(leftPad("", preSize, replaceChar));
                sb.append(substring(text, preSize, text.length() - postSize));
                sb.append(leftPad("", postSize, replaceChar));
                return sb.toString();
            }
        }
    }

    public static String maskUserName(String text) {
        return maskReverse(text, 2, 1);
    }

    public static String maskBankCardNo(String text) {
        return maskReverse(text, 4, 3);
    }

    public static String maskIdCardNo(String text) {
        return maskReverse(text, 3, 4);
    }

    public static String maskMobileNo(String text) {
        return maskReverse(text, 3, 3);
    }

    public static String maskReverse(String text, int start, int end) {
        return maskReverse(text, start, end, '*');
    }

    public static String maskReverse(String text, int start, int end, Character replaceChar) {
        return maskReverse(text, start, end, '*', 0);
    }

    public static String maskReverse(String text, int start, int end, Character replaceChar, int maskLength) {
        String source = trimToEmpty(text);
        if (isBlank(source)) {
            return source;
        } else {
            if (replaceChar == null) {
                replaceChar = '*';
            }

            if (start < source.length() && end < source.length() && source.length() - start - end >= 0) {
                int mLength = text.length() - start - end;
                mLength = maskLength == 0 ? mLength : maskLength;
                return left(text, start) + leftPad("", mLength, replaceChar) + right(text, end);
            } else {
                return source;
            }
        }
    }

    public static boolean isNumber(CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        } else {
            int sz = cs.length();

            for(int i = 0; i < sz; ++i) {
                if (!Character.isDigit(cs.charAt(i)) && cs.charAt(i) != '.') {
                    return false;
                }
            }

            return true;
        }
    }
}
