//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sword.core.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dates {
    public static final String CHINESE_DATE_FORMAT_LINE = "yyyy-MM-dd";
    public static final String CHINESE_DATETIME_FORMAT_LINE = "yyyy-MM-dd HH:mm:ss";
    public static final String CHINESE_DATE_FORMAT_SLASH = "yyyy/MM/dd";
    public static final String CHINESE_DATETIME_FORMAT_SLASH = "yyyy/MM/dd HH:mm:ss";
    public static final String DATETIME_NOT_SEPARATOR = "yyyyMMddHHmmssSSS";
    public static final int FIRST_DAY_OF_WEEK = 2;
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final List<String> patterns = Lists.newArrayList(new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyyMMddHHmmssSSS", "yyyy-MM-dd", "yyyy/MM/dd"});

    public Dates() {
    }

    public static void registerPattern(String pattern) {
        patterns.add(pattern);
        sort();
    }

    public static void unRegisterPattern(String pattern) {
        patterns.remove(pattern);
        sort();
    }

    private static void sort() {
        Collections.sort(patterns, new Comparator<String>() {
            public int compare(String o1, String o2) {
                if (o1 != null && o2 != null) {
                    if (o1.length() > o2.length()) {
                        return -1;
                    } else {
                        return o1.length() < o2.length() ? 1 : 0;
                    }
                } else {
                    return 0;
                }
            }
        });
        System.out.println(patterns);
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = getSimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String format(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String format(String pattern) {
        return format(new Date(), pattern);
    }

    public static Date parse(String dateString, String pattern) {
        SimpleDateFormat sdf = getSimpleDateFormat(pattern);

        try {
            return sdf.parse(dateString);
        } catch (Exception var4) {
            throw new RuntimeException("parse String[" + dateString + "] to Date faulure with pattern[" + pattern + "].");
        }
    }

    public static Date parse(String dateString, String[] patterns) {
        String[] var2 = patterns;
        int var3 = patterns.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String pattern = var2[var4];
            if (!StringUtils.isBlank(pattern)) {
                SimpleDateFormat sdf = getSimpleDateFormat(pattern);

                try {
                    return sdf.parse(dateString);
                } catch (Exception var8) {
                    ;
                }
            }
        }

        throw new UnsupportedOperationException("Parse String[" + dateString + "] to Date faulure with patterns[" + Arrays.toString(patterns) + "]");
    }

    public static Date parse(String dateString) {
        return parse(dateString, (String[])patterns.toArray(new String[0]));
    }

    public static Date addYear(Date date, int years) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(1, years);
        return c.getTime();
    }

    public static Date addMonth(Date date, int months) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(2, months);
        return c.getTime();
    }

    public static Date addWeek(Date date, int weeks) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(3, weeks);
        return c.getTime();
    }

    public static Date addDay(Date date) {
        return addDay(date, 1);
    }

    public static Date addDay(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(6, days);
        return c.getTime();
    }

    public static Date addWorkDay(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int totalDays = 0;

        do {
            c.add(6, 1);
            int d = c.get(7) - 1;
            if (d != 0 && d != 6) {
                ++totalDays;
            }
        } while(totalDays < days);

        return c.getTime();
    }

    public static Date minusDay(Date date) {
        long oneDayMillisecond = 86400000L;
        return addDate(date, -oneDayMillisecond);
    }

    public static Date addDate(Date date, long millisecond) {
        return new Date(date.getTime() + millisecond);
    }

    public static long sub(Date left, Date right, int type) {
        long subms = left.getTime() - right.getTime();
        if (subms < 0L) {
            throw new RuntimeException("left日期小于right日期");
        } else {
            switch(type) {
                case 5:
                    return subms % 1000L / 60L / 60L / 24L == 0L ? subms / 1000L / 60L / 60L / 24L : subms / 1000L / 60L / 60L / 24L + 1L;
                case 6:
                case 7:
                case 8:
                case 9:
                case 11:
                default:
                    return subms;
                case 10:
                    return subms % 1000L / 60L / 60L == 0L ? subms / 1000L / 60L / 60L : subms / 1000L / 60L / 60L + 1L;
                case 12:
                    return subms % 1000L / 60L == 0L ? subms / 1000L / 60L : subms / 1000L / 60L + 1L;
                case 13:
                    return subms % 1000L == 0L ? subms / 1000L : subms / 1000L + 1L;
            }
        }
    }

    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(1);
        return year;
    }

    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(2);
        return month + 1;
    }

    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int da = c.get(5);
        return da;
    }

    public static int getWeekDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week_of_year = c.get(7);
        return week_of_year - 1;
    }

    public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week_of_year = c.get(3);
        return week_of_year;
    }

    public static Date getMondayOfWeek(Date date) {
        Calendar monday = Calendar.getInstance();
        monday.setTime(date);
        monday.setFirstDayOfWeek(2);
        monday.set(7, 2);
        return monday.getTime();
    }

    public static Date getSundayOfWeek(Date date) {
        Calendar sunday = Calendar.getInstance();
        sunday.setTime(date);
        sunday.setFirstDayOfWeek(2);
        sunday.set(7, 1);
        return sunday.getTime();
    }

    public static int getRemainDayOfMonth(Date date) {
        int dayOfMonth = getDayOfMonth(date);
        int day = getPassDayOfMonth(date);
        return dayOfMonth - day;
    }

    public static int getPassDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(5);
    }

    public static int getDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(5);
    }

    public static Date getFirstDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(5, c.getActualMinimum(5));
        return c.getTime();
    }

    public static Date getLastDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(5, c.getActualMaximum(5));
        return c.getTime();
    }

    public static Date getFirstDateOfSeason(Date date) {
        return getFirstDateOfMonth(getQuarterDate(date)[0]);
    }

    public static Date getLastDateOfSeason(Date date) {
        return getLastDateOfMonth(getQuarterDate(date)[2]);
    }

    public static int getDayOfSeason(Date date) {
        int day = 0;
        Date[] seasonDates = getQuarterDate(date);
        Date[] var3 = seasonDates;
        int var4 = seasonDates.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Date date2 = var3[var5];
            day += getDayOfMonth(date2);
        }

        return day;
    }

    public static int getRemainDayOfSeason(Date date) {
        return getDayOfSeason(date) - getPassDayOfSeason(date);
    }

    public static int getPassDayOfSeason(Date date) {
        int day = 0;
        Date[] seasonDates = getQuarterDate(date);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(2);
        if (month != 0 && month != 3 && month != 6 && month != 9) {
            if (month != 1 && month != 4 && month != 7 && month != 10) {
                if (month == 2 || month == 5 || month == 8 || month == 11) {
                    day = getDayOfMonth(seasonDates[0]) + getDayOfMonth(seasonDates[1]) + getPassDayOfMonth(seasonDates[2]);
                }
            } else {
                day = getDayOfMonth(seasonDates[0]) + getPassDayOfMonth(seasonDates[1]);
            }
        } else {
            day = getPassDayOfMonth(seasonDates[0]);
        }

        return day;
    }

    public static Date[] getQuarterDate(Date date) {
        Date[] quarters = new Date[3];
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int nq = getQuarter(date);
        if (nq == 1) {
            c.set(2, 0);
            quarters[0] = c.getTime();
            c.set(2, 1);
            quarters[1] = c.getTime();
            c.set(2, 2);
            quarters[2] = c.getTime();
        } else if (nq == 2) {
            c.set(2, 3);
            quarters[0] = c.getTime();
            c.set(2, 4);
            quarters[1] = c.getTime();
            c.set(2, 5);
            quarters[2] = c.getTime();
        } else if (nq == 3) {
            c.set(2, 6);
            quarters[0] = c.getTime();
            c.set(2, 7);
            quarters[1] = c.getTime();
            c.set(2, 8);
            quarters[2] = c.getTime();
        } else if (nq == 4) {
            c.set(2, 9);
            quarters[0] = c.getTime();
            c.set(2, 10);
            quarters[1] = c.getTime();
            c.set(2, 11);
            quarters[2] = c.getTime();
        }

        return quarters;
    }

    public static int getQuarter(Date date) {
        int quarter = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(2);
        switch(month) {
            case 0:
            case 1:
            case 2:
                quarter = 1;
                break;
            case 3:
            case 4:
            case 5:
                quarter = 2;
                break;
            case 6:
            case 7:
            case 8:
                quarter = 3;
                break;
            case 9:
            case 10:
            case 11:
                quarter = 4;
        }

        return quarter;
    }

    private static SimpleDateFormat getSimpleDateFormat(String defaultFormat) {
        if (Strings.isBlank(defaultFormat)) {
            defaultFormat = "yyyy-MM-dd HH:mm:ss";
        }

        return new SimpleDateFormat(defaultFormat);
    }

    public static List<String> collectLocalDates(Date timeStart, Date timeEnd) {
        return collectLocalDates(format(timeStart, "yyyy-MM-dd"), format(timeEnd, "yyyy-MM-dd"));
    }

    public static List<String> collectLocalDates(String timeStart, String timeEnd) {
        return collectLocalDates(LocalDate.parse(timeStart), LocalDate.parse(timeEnd));
    }

    public static List<String> collectLocalDates(LocalDate start, LocalDate end) {
        return (List)Stream.iterate(start, (localDate) -> {
            return localDate.plusDays(1L);
        }).limit(ChronoUnit.DAYS.between(start, end) + 1L).map(LocalDate::toString).collect(Collectors.toList());
    }
}
