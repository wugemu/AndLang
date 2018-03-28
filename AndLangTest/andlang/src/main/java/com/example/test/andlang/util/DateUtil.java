package com.example.test.andlang.util;


import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * @author
 */
public final class DateUtil {
    private static final String TAG = "DateUtil";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String DEFAULT_DATEFULLDATE_FORMAT = "yyyyMMdd";

    public static final String DEFAULT_YEAR_FORMAT = "yyyy";

    public static final String DEFAULT_MONTH_FORMAT = "MM";

    private static final long MILLISECONDS_A_DAY = 24 * 3600 * 1000;

    private static final long MILLISECONDS_A_HOUR = 3600 * 1000;

    private static final long MILLISECONDS_A_SECOND = 1000;


    private static final String DEFAULT_DATEFULLTIME_FORMAT = "yyyyMMddHHmmss";

    /**
     * 验证日期字符串，有效日期范围1900-1-1�?099-12-31.
     */
    private static final Pattern PATTERN = Pattern
            .compile("(?:(?:19|20)\\d{2})-(?:0?[1-9]|1[0-2])-(?:0?[1-9]|[12][0-9]|3[01])");

    private static final int FOUR = 4;

    private static final int SIX = 6;

    private static final int TEN = 10;

    /**
     * 当前时间加上days�?
     */
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 当前时间加上days�?
     */
    public static Date addHours(long time, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        cal.add(Calendar.HOUR, hours);
        return cal.getTime();
    }

    public static Date addMinutes(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    public static int getHours(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int hour = cal.get(Calendar.HOUR_OF_DAY);//小时
        return hour;
    }


    public static Date addSeconds(Date date, int seconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, seconds);
        return cal.getTime();
    }

    /**
     * 当前时间加上days�?
     */
    public static Date addMonths(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 获取当前月的�?��日期.
     *
     * @return
     */
    public static Date getMaxDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }

    /**
     * 获取当前年份.
     *
     * @return
     */
    public static int getYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前�?
     *
     * @return
     */
    public static int getMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前月的�?��日期.
     *
     * @return
     */
    public static Date getMinDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        return cal.getTime();
    }

    /**
     * 获取指定月的�?��时间.
     *
     * @param date
     * @return
     */
    public static Date getMinDateByMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        return cal.getTime();
    }

    /**
     * 获取指定月的�?��时间.
     *
     * @param date
     * @return
     */
    public static Date getMaxDateByMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }

    /**
     * 取得某月的的�?���?��.
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfLastMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 当前时间加上years�?
     *
     * @param date
     * @param years
     * @return
     */
    public static Date addYears(Date date, int years) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
    }

    /**
     * 获得指定格式的日期时间字符串.
     *
     * @param format
     * @return
     */
    public static String datetime(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return formatter.format(new Date());
    }

    /**
     * 获得指定格式的日期时间字符串.
     *
     * @param date
     * @param format
     * @return
     */
    public static String datetime(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return formatter.format(date);
    }

    /**
     * 获得指定格式的日期时间字符串.
     *
     * @param date
     * @param format
     * @return
     */
    public static String datetime(String date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return formatter.format(date);
    }

    public static String datetime(String date, String format, String toformat) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        try {
            Date d = formatter.parse(date);
            formatter = new SimpleDateFormat(toformat);
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            return formatter.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获得指定格式的当前日期字符串.
     *
     * @param date
     * @param format
     * @return
     */
    public static String date(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return formatter.format(date);
    }

    /**
     * 获得指定格式的当前日期字符串.
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static String date(String dateStr, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return formatter.format(dateStr);
    }

    /**
     * 获得"yyyy-MM-dd"格式的当前日期字符串.
     *
     * @return
     */
    public static String getNowDateStr() {
        return getNowDatetimeStr(DEFAULT_DATE_FORMAT);
    }

    /**
     * 获得"yyyy-MM-dd HH:mm:ss"格式的当前日期时间字符串.
     *
     * @return
     */
    public static String getNowDatetimeStr() {
        return getNowDatetimeStr(DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 获得"yyyyMMddHHmmss"格式的当前日期时间字符串.
     *
     * @return
     */
    public static String getNowDateminStr() {
        return getNowDatetimeStr(DEFAULT_DATEFULLTIME_FORMAT);
    }

    /**
     * 获得当前日期时间字符�?
     *
     * @param format 日期格式
     * @return 日期时间字符�?
     */
    public static String getNowDatetimeStr(String format) {
        Calendar cal = Calendar.getInstance();
        return datetime(cal.getTime(), format);
    }

    /**
     * ֻ只取当前时间的日期部分，小时、分、秒等字段归�?
     */
    public static Date dateOnly(Date date) {
        return new Date(date.getTime() / MILLISECONDS_A_DAY);
    }

    /**
     * ֻ只取当前时间的日期部分，小时、分、秒等字段归�?
     */
    public static Date dateOnlyExt(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * ֻ只取当前时间的日期部分，小时、分、秒等字段归�?
     */
    public static Date dateMinTime(Date date) {
        return dateOnlyExt(date);
    }

    /**
     * 把类�?007-2-2 7:1:8的时间串变为标准�?007-02-02 07:01:08.
     *
     * @param dateTimeStr 未校正日期�?
     * @return 日期对象
     */
    public static String getStandDateTimeStr(String dateTimeStr) {
        if (dateTimeStr == null || "".equals(dateTimeStr)) {
            return "";
        }

        String str = dateTimeStr.replaceAll("\\s+", "|");
        String[] a = str.split("\\|");
        List<String> list = Arrays.asList(a);
        String datetime = "";
        int count = 1;
        for (int i = 0; i < list.size(); i++) {
            String temp = (String) list.get(i);
            StringTokenizer st;
            if (i == 0) {
                st = new StringTokenizer(temp, "-");
            } else {
                st = new StringTokenizer(temp, ":");
            }
            int k = st.countTokens();
            for (int j = 0; j < k; j++) {
                String sttemp = (String) st.nextElement();
                if (count == 1) {
                    datetime = sttemp;
                } else {
                    if (("0".equals(sttemp)) || ("00".equals(sttemp))) {
                        sttemp = "0";
                    } else if ((Integer.valueOf(sttemp).intValue()) < TEN) {
                        sttemp = sttemp.replaceAll("0", "");
                    }
                    if (count < FOUR) {
                        if ((Integer.valueOf(sttemp).intValue()) < TEN) {
                            datetime = datetime + "-0" + sttemp;
                        } else {
                            datetime = datetime + "-" + sttemp;
                        }
                    }
                    if (count == FOUR) {
                        if ((Integer.valueOf(sttemp).intValue()) < TEN) {
                            datetime = datetime + " 0" + sttemp;
                        } else {
                            datetime = datetime + " " + sttemp;
                        }
                    }
                    if (count > FOUR) {
                        if ((Integer.valueOf(sttemp).intValue()) < TEN) {
                            datetime = datetime + ":0" + sttemp;
                        } else {
                            datetime = datetime + ":" + sttemp;
                        }
                    }
                }
                count++;
            }
        }

        try {
            getDateFromStr(datetime);
            return datetime;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 把标准的2007-02-02 07:01:08格式转换成日期对�?
     *
     * @param datetime 日期,标准�?007-02-02 07:01:08格式
     * @return 日期对象
     */
    @SuppressWarnings("deprecation")
    public static Date getDateFromStr(String datetime) {
        if (datetime == null || "".equals(datetime)) {
            return new Date();
        }

        String nyr = datetime.trim();

        if (nyr.indexOf(' ') > 0) {
            nyr = nyr.substring(0, nyr.indexOf(' '));
        } else {
            nyr = nyr.substring(0, nyr.length());
        }

        StringTokenizer st = new StringTokenizer(nyr, "-");
        Date date = new Date();
        String temp = "";
        int count = st.countTokens();
        for (int i = 0; i < count; i++) {
            temp = (String) st.nextElement();
            if (i == 0) {
                date.setYear(Integer.parseInt(temp) - 1900);
            }
            if (i == 1) {
                date.setMonth(Integer.parseInt(temp) - 1);
            }
            if (i == 2) {
                date.setDate(Integer.parseInt(temp));
            }
        }

        if (datetime.length() > TEN) {
            String sfm = datetime.substring(11, 19);
            StringTokenizer st2 = new StringTokenizer(sfm, ":");
            count = st2.countTokens();
            for (int i = 0; i < count; i++) {
                temp = (String) st2.nextElement();
                if (i == 0) {
                    date.setHours(Integer.parseInt(temp));
                }
                if (i == 1) {
                    date.setMinutes(Integer.parseInt(temp));
                }
                if (i == 2) {
                    date.setSeconds(Integer.parseInt(temp));
                }
            }
        }
        return date;
    }

    /**
     * 返回两个日期相差天数.
     *
     * @param startDate 起始日期对象
     * @param endDate   截止日期对象
     * @return
     */
    public static int getQuot(Date startDate, Date endDate) {
        long quot = 0;
        quot = endDate.getTime() - startDate.getTime();
        quot = quot / MILLISECONDS_A_DAY;
        return (int) quot;
    }

    /**
     * 返回两个日期相差天数.
     *
     * @param startDateStr 起始日期字符�?
     * @param endDateStr   截止期字符串
     * @param format       时间格式
     * @return
     */
    public static int getQuot(String startDateStr, String endDateStr, String format) {
        long quot = 0;
        String str = (format != null && format.length() > 0) ? format : DEFAULT_DATE_FORMAT;
        SimpleDateFormat ft = new SimpleDateFormat(str);
        ft.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        try {
            Date date1 = ft.parse(endDateStr);
            Date date2 = ft.parse(startDateStr);
            quot = date1.getTime() - date2.getTime();
            quot = quot / MILLISECONDS_A_DAY;
        } catch (ParseException e) {
            Log.e(TAG, "获取两个日期相差天数异常: ");
            quot = -1;
        }
        return (int) quot;
    }

    /**
     * 返回日期字符串："yyyy-MM-dd HH:mm" 格式.
     *
     * @param date
     * @return
     */
    public static String getDateTime(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat ymdhmsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ymdhmsFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return ymdhmsFormat.format(date);
    }

    /**
     * 按给定格式返回时间的字符�?
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String getDateTime(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        DateFormat ymdhmsFormat = new SimpleDateFormat(pattern);
        ymdhmsFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return ymdhmsFormat.format(date);
    }

    /**
     * 返回两个日期相差的小�?
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getQuotHours(Date startDate, Date endDate) {
        long quot = 0;
        quot = endDate.getTime() - startDate.getTime();
        quot = quot / MILLISECONDS_A_HOUR;
        return (int) quot;
    }

    /**
     * 返回两个日期相差的秒.
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getQuotSeconds(Date startDate, Date endDate) {
        long quot = 0;
        quot = endDate.getTime() - startDate.getTime();
        quot = quot / MILLISECONDS_A_SECOND;
        return (int) quot;
    }

    /**
     * 将字符串转换为日期型 格式�? yyyy-MM-dd.
     *
     * @param dateTime
     * @return
     */
    public static Date getDateTime(String dateTime) {
        return getDateTime(dateTime, "yyyy-MM-dd");
    }

    public static Date getDateTime(String dateTime, String formatPattern) {
        try {
            if (dateTime != null && formatPattern != null) {
                SimpleDateFormat format = new SimpleDateFormat(formatPattern);
                format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                return format.parse(dateTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将字符串转换为日期型 格式�? yyyy-MM-dd.
     *
     * @param dateTime
     * @return
     */
    public static Date getDateDetailTime(String dateTime) {
        try {
            if (dateTime != null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                return format.parse(dateTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取当前的时间戳，在页面上保证URL唯一，防止缓�?
     *
     * @return
     */
    public static long getDtSeq() {
        return System.currentTimeMillis();
    }

    /**
     * 判断是否在参数日期的�?��值和�?��值之�?
     *
     * @param min
     * @param compare
     * @return
     */
    public static boolean isBetween(Date min, Date compare) {
        Boolean ret = false;
        Date minDate = DateUtil.dateOnlyExt(min);
        Date maxDate = DateUtil.dateOnlyExt(DateUtil.addDays(min, 1));
        if (compare.after(minDate) && compare.before(maxDate)) {
            ret = true;
        }
        return ret;
    }

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        return cal.getTime();
    }

    /**
     * 获取本月/上月/本季度的月初和月�?
     *
     * @param monthRange 取�?范围{cm:本月，pm:上月，sm:本季度}
     * @return Map{firstDay:yyyy-MM-dd, lastDay:yyyy-MM-dd}
     */
    public static Map<String, String> getFLDayMap(String monthRange) {
        return getFLDayMap(monthRange, DEFAULT_DATE_FORMAT);
    }

    /**
     * 获取本月/上月/本季度的月初和月�?
     *
     * @param monthRange 取�?范围{cm:本月，pm:上月，sm:本季度}
     * @param pattern
     * @return Map{firstDay:yyyy-MM-dd, lastDay:yyyy-MM-dd}
     */
    public static Map<String, String> getFLDayMap(String monthRange, String pattern) {
        Map<String, String> rs = new LinkedHashMap<String, String>();

        SimpleDateFormat df = new SimpleDateFormat(pattern);
        df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        String range = monthRange;

        if (range == null) {
            range = "cm";
        }

        if (!"sm".equals(range)) {
            if ("pm".equals(range)) {
                calendar.add(Calendar.MONTH, -1);
            }

            calendar.set(Calendar.DAY_OF_MONTH, 1);
            rs.put("firstDay", df.format(calendar.getTime()));

            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            rs.put("lastDay", df.format(calendar.getTime()));

            return rs;
        }

		/*
         * 本季度的月初和月�?
		 */
        int[][] seasons = {{2, 4}, {5, 7}, {8, 10}, {11, 1}};
        int cm = calendar.get(Calendar.MONTH) + 1;

        for (int[] im : seasons) {
            if (cm >= im[0] && cm <= im[1]) {
                calendar.set(Calendar.MONTH, im[0] - 1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                rs.put("firstDay", df.format(calendar.getTime()));

                calendar.set(Calendar.MONTH, im[1] - 1);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                rs.put("lastDay", df.format(calendar.getTime()));

                break;
            }
        }

        return rs;
    }

    /**
     * 获取某日期的年份字符�?
     *
     * @param date
     * @return 字符串类型的年份
     */
    public static String getYearString(Date date) {
        return DateUtil.date(date, DEFAULT_YEAR_FORMAT);
    }

    /**
     * 获取某日期的年份数字.
     *
     * @param date
     * @return 数字类型的年�?
     */
    public static int getYearInteger(Date date) {
        return Integer.parseInt(DateUtil.date(date, DEFAULT_YEAR_FORMAT));
    }

    /**
     * 获取某日期的月份字符�?
     *
     * @param date
     * @return
     */
    public static String getMonthString(Date date) {
        return DateUtil.date(date, DEFAULT_MONTH_FORMAT);
    }

    /**
     * 获取某日期的月份数字.
     *
     * @param date
     * @return 数字类型的月�?
     */
    public static int getMonthInteger(Date date) {
        return Integer.parseInt(DateUtil.date(date, DEFAULT_MONTH_FORMAT));
    }

    /**
     * 取得当前月的的最后一�?
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfCurMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 取得当前月的的第�?��.
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfCurMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.DATE, 0);
        return cal.getTime();
    }

    /**
     * 取得某天�?��周的第一�?
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return c.getTime();
    }

    /**
     * 取得某天�?��周的�?���?��.
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + SIX);
        return c.getTime();
    }

    /**
     * 验证日期是否有效，有效日期范�?900-1-1�?099-12-31.
     *
     * @param ds
     * @return
     */
    public static boolean isValidDate(String ds) {
        if (ds == null) {
            return false;
        }
        return PATTERN.matcher(ds).matches();
    }

    /**
     * 验证日期是否有效，有效日期范�?900-1-1�?099-12-31.
     *
     * @param d
     * @return
     */
    public static boolean isValidDate(Date d) {
        if (d == null) {
            return false;
        }
        return PATTERN.matcher(date(d, DEFAULT_DATE_FORMAT)).matches();
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }

    /**
     * 日期格式化到月日
     *
     * @param
     * @param
     * @return
     */
    public static String date2MD(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("M月dd日");
        return sdf.format(date);
    }

    /**
     * 日期格式化到月日
     *
     * @param
     * @param
     * @return
     */
    public static String date2String(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月dd日");
        return sdf.format(date);
    }

    /**
     * 日期格式化到月日
     *
     * @param
     * @param
     * @return
     */
    public static String date2String2(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月dd日HH:mm");
        return sdf.format(date);
    }

    public static String dateStr2String(String dateStr, String format) {
        if (dateStr == null || "".equalsIgnoreCase(dateStr)) {
            return "";
        }
        try {
            SimpleDateFormat f = new SimpleDateFormat(format);
            f.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            Date date = f.parse(dateStr);
            SimpleDateFormat f2 = new SimpleDateFormat("yyyy年M月d日");
            return f2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

}
