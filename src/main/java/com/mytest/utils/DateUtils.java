
package com.mytest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 日期工具
 * @ClassName: DateUtils
 * @Description: Description of this class
 * @author <a href="mailto:zengpeng5281314@163.com">曾鹏</a> 于 2017年5月5日 下午6:54:46
 */
public final class DateUtils {

    private static Logger log = Logger.getLogger(DateUtils.class);
    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";

    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * 中文简写 如：2010年12月01日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd";

    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";

    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return FORMAT_LONG;
    }

    public static void main(String[] args) {
        System.out.println(new Date());
        Calendar c1 = Calendar.getInstance();
        c1.set(2012, 2, 15);

        Calendar c2 = Calendar.getInstance();
        c2.set(2013, 1, 20);

        List<Date> list = getMonthList(c1, c2);
        for (Date d : list) {
            System.out.println(date2Str(d));
        }
    }

    /**
     * 根据预设格式返回当前日期
     * 
     * @return
     */
    public static String getNow() {
        return format(new Date());
    }

    public static Date getDate() {
        return new Date();
    }

    /**
     * 根据用户格式返回当前日期
     * 
     * @param format
     * @return
     */
    public static String getNow(String format) {
        return format(new Date(), format);
    }

    /**
     * 使用预设格式格式化日期
     * 
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, getDatePattern());
    }

    /**
     * 使用用户格式格式化日期
     * 
     * @param date 日期
     * @param pattern 日期格式
     * @return
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 使用预设格式提取字符串日期
     * 
     * @param strDate 日期字符串
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    /**
     * 使用用户格式提取字符串日期
     * 
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 在日期上增加数个整月
     * 
     * @param date 日期
     * @param n 要增加的月数
     * @return
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     * 
     * @param date 日期
     * @param n 要增加的天数
     * @return
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    /**
     * 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取日期年份
     * 
     * @param date 日期
     * @return
     */
    public static String getYear(Date date) {
        return format(date).substring(0, 4);
    }

    /**
     * 按默认格式的字符串距离今天的天数
     * 
     * @param date 日期字符串
     * @return
     */
    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 按用户格式字符串距离今天的天数
     * 
     * @param date 日期字符串
     * @param format 日期格式
     * @return
     */
    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    public static Date str2Date(String str) {
        return str2Date(str, null);
    }

    /**
     * 把字符串根据制定的格式转换成日期
     * 
     * @param str
     * @param format
     * @return
     */

    public static Date str2Date(String str, String format) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT_LONG;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(str);

        } catch (Exception e) {
            log.error("", e);
        }
        return date;

    }

    /**
     * 把字符串转换成calendar对象
     * 
     * @param str
     * @return
     */
    public static Calendar str2Calendar(String str) {
        return str2Calendar(str, null);

    }

    public static Calendar str2Calendar(String str, String format) {

        Date date = str2Date(str, format);
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c;

    }

    /**
     * 把calendar转换成字符串
     * 
     * @param c
     * @return
     */
    public static String date2Str(Calendar c) {// yyyy-MM-dd HH:mm:ss
        return date2Str(c, null);
    }

    public static String date2Str(Calendar c, String format) {
        if (c == null) {
            return null;
        }
        return date2Str(c.getTime(), format);
    }

    public static String date2Str(Date d) {// yyyy-MM-dd HH:mm:ss
        return date2Str(d, null);
    }

    /**
     * 把日期转换成制定的格式
     * 
     * @param d
     * @param format
     * @return
     */
    public static String date2Str(Date d, String format) {// yyyy-MM-dd HH:mm:ss
        if (d == null) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT_LONG;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(d);
        return s;
    }

    /**
     * 获得当前日期的字符串格式
     * 
     * @param format
     * @return
     */
    public static String getCurDateStr(String format) {
        Calendar c = Calendar.getInstance();
        return date2Str(c, format);
    }

    /**
     * 获得从当前年到2011年的年份数组
     * 
     * @return
     */
    public static Integer[] getYears() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int lowYear = 2011;
        List<Integer> list = new ArrayList<Integer>();
        for (int i = year; i >= lowYear; i--) {
            list.add(i);
        }

        return list.toArray(new Integer[1]);
    }

    /**
     * @return
     */
    public static Integer getCurrtYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        return year;
    }

    /**
     * 开始日期，结束日期.去到的是相差的天数
     * 
     * @param enddate 日期具体到天不要到小时
     * @param begindate日期具体到天不要到小时
     * @return
     */
    public static int getIntervalDays(Date enddate, Date begindate) {
        // SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        // enddate.get
        long millisecond = enddate.getTime() - begindate.getTime();
        int day = (int) (millisecond / 24L / 60L / 60L / 1000L);
        return day;
    }

    /**
     * 日期+分钟
     * 
     * @param oldDate
     * @param m
     * @return
     */
    public static Date addMinute(Date oldDate, Integer m) {
        if (oldDate == null || m == null) {
            return null;
        }
        long ml = m * 60 * 1000;
        long s = oldDate.getTime() + ml;
        Date d = new Date(s);
        return d;
    }

    public static List<Date> getMonthList(Calendar start, Calendar end) {
        if (start == null || end == null) {
            return null;
        }
        return getMonthList(start.getTime(), end.getTime());
    }

    public static List<Date> getMonthList(Date start, Date end) {
        if (start == null || end == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        List<Date> list = new ArrayList<Date>();

        list.add(start);
        calendar.setTimeInMillis(start.getTime());
        int sm = calendar.get(Calendar.MARCH);
        int sy = calendar.get(Calendar.YEAR);
        calendar.setTimeInMillis(end.getTime());
        int em = calendar.get(Calendar.MARCH);
        int ey = calendar.get(Calendar.YEAR);
        if (sy > ey) {
            return null;
        }
        if (sy == ey && sm > em) {
            return null;
        }
        while (true) {
            sm++;
            calendar.set(sy, sm, 1, 0, 0, 0);
            // Date d=new Date(calendar.getTime().getTime()-1000);
            Date d = calendar.getTime();
            if (d.after(end)) {
                break;
            }
            list.add(d);
        }

        list.add(end);

        return list;
    }

    /**
     * 返回星期几 周一到周日 ：1->7
     * 
     * @param date
     * @return
     */
    public static int getWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return week_index <= 0 ? 7 : week_index;
    }

    public static int getWeek() {
        return getWeek(new Date());
    }

}
