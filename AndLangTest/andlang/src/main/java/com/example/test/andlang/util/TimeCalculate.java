package com.example.test.andlang.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 1 on 2015/12/25.
 * 时间计算工具
 */
public class TimeCalculate {
    private static final long nd = 24 * 60 * 60;//一天的秒数
    private static final long nh = 60 * 60;//一小时的秒数
    private static final long nm =  60;//一分钟的秒数
    private static final long ns = 1;//一秒钟的秒数
    private static StringBuilder sb = new StringBuilder();

    public static String getTime(long now, long target) {

        sb.delete(0, sb.length());
        long diff = target - now;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;

        if (diff > 0) {
            day = diff / nd;//计算差多少天
            hour = diff % nd / nh;//计算差多少小时
            min = diff % nd % nh / nm;//计算差多少分钟
            sec = diff % nd % nh % nm / ns;//计算差多少秒//输出结果
        } else {
            return "";
        }
        sb.delete(0, sb.length());
        sb.append("剩");
        if (day > 0) {
            sb.append(day);
            sb.append("天 ");
        }
        if (hour <= 9) {
            sb.append("0");
        }
        sb.append(hour);
        sb.append(":");
        if (min <= 9) {
            sb.append("0");
        }
        sb.append(min);
        sb.append(":");
        if (sec <= 9) {
            sb.append("0");
        }
        sb.append(sec);
        return sb.toString();
    }

    public static String getTime5(long diff) {
        sb.delete(0, sb.length());
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;

        if (diff > 0) {
            day = diff / nd;//计算差多少天
            hour = diff % nd / nh;//计算差多少小时
            min = diff % nd % nh / nm;//计算差多少分钟
            sec = diff % nd % nh % nm / ns;//计算差多少秒//输出结果
        } else {
            return "";
        }
        sb.delete(0, sb.length());
//        sb.append("剩");
//        if (day > 0) {
//            sb.append(day);
//            sb.append("天 ");
//        }
        if (hour <= 9) {
            sb.append("0");
        }
        sb.append(hour);
        sb.append("时");
        if (min <= 9) {
            sb.append("0");
        }
        sb.append(min);
        sb.append("分");
        if (sec <= 9) {
            sb.append("0");
        }
        sb.append(sec);
        sb.append("秒");
        return sb.toString();
    }

    public static String getTime5(long now, long target) {
        long diff = target - now;
        return getTime5(diff);
    }

//    public static String getTime4(long diff) {
//        sb.delete(0, sb.length());
//        long day = 0;
//        long hour = 0;
//        long min = 0;
//        long sec = 0;
//
//        if (diff > 0) {
//            day = diff / nd;//计算差多少天
//            hour = diff % nd / nh;//计算差多少小时
//            min = diff % nd % nh / nm;//计算差多少分钟
//            sec = diff % nd % nh % nm / ns;//计算差多少秒//输出结果
//        } else {
//            return "";
//        }
//        sb.delete(0, sb.length());
//
//        if (day > 0) {
//            sb.append(day);
//            sb.append("天");
//        }
//        if (hour <= 9) {
//            sb.append("0");
//        }
//        sb.append(hour);
//        sb.append(":");
//        if (min <= 9) {
//            sb.append("0");
//        }
//        sb.append(min);
//        sb.append(":");
//        if (sec <= 9) {
//            sb.append("0");
//        }
//        sb.append(sec);
//        return sb.toString();
//    }

    public static String getTime4(long now, long target) {
        sb.delete(0, sb.length());
        long diff = target - now;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;

        if (diff > 0) {
            day = diff / nd;//计算差多少天
            hour = diff % nd / nh;//计算差多少小时
            min = diff % nd % nh / nm;//计算差多少分钟
            sec = diff % nd % nh % nm / ns;//计算差多少秒//输出结果
        } else {
            return "";
        }
        sb.delete(0, sb.length());
        sb.append("剩余\u3000");
//        if(day>0){
        sb.append(day);
        sb.append("\u3000天\u3000");
//        }
        if (hour <= 9) {
            sb.append("0");
        }
        sb.append(hour);
        sb.append(":");
        if (min <= 9) {
            sb.append("0");
        }
        sb.append(min);
        sb.append(":");
        if (sec <= 9) {
            sb.append("0");
        }
        sb.append(sec);
        return sb.toString();
    }

    public static Map<String, String> getTimeMap(long now, long target) {

        Map<String, String> map = new HashMap<String, String>();
        long diff = target - now;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long day = 0;
        if (diff > 0) {
            day = diff / nd;
            hour = diff % nd / nh;//计算差多少小时
            min = diff % nd % nh / nm;//计算差多少分钟
            sec = diff % nd % nh % nm / ns;//计算差多少秒//输出结果
        } else {
            return null;
        }
        hour+=day*24;
        if (hour <= 9) {
            map.put("hour", "0" + hour);
        } else {
            map.put("hour", "" + hour);
        }

        if (min <= 9) {
            map.put("min", "0" + min);
        } else {
            map.put("min", "" + min);
        }

        if (sec <= 9) {
            map.put("sec", "0" + sec);
        } else {
            map.put("sec", "" + sec);
        }

        return map;
    }


    public static String getTime2(long now, long target) {
        sb.delete(0, sb.length());
        long diff = target - now;
        long min = 0;
        long sec = 0;

        if (diff > 0) {
            min = diff % nd % nh / nm;//计算差多少分钟
            sec = diff % nd % nh % nm / ns;//计算差多少秒//输出结果
        } else {
            return "";
        }
        sb.delete(0, sb.length());
        if (min <= 9) {
            sb.append("0");
        }
        sb.append(min);
        sb.append(":");
        if (sec <= 9) {
            sb.append("0");
        }
        sb.append(sec);
        return sb.toString();
    }

    public static String getTime3(long now, long target) {
        sb.delete(0, sb.length());
        long diff = target - now;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;

        if (diff > 0) {
            day = diff / nd;//计算差多少天
            hour = diff % nd / nh;//计算差多少小时
            min = diff % nd % nh / nm;//计算差多少分钟
            sec = diff % nd % nh % nm / ns;//计算差多少秒//输出结果
        } else {
            return "";
        }
        sb.delete(0, sb.length());
        if (day > 0) {
            hour += 24 * day;
        }
        if (hour <= 9) {
            sb.append("0");
        }
        sb.append(hour);
        sb.append(":");
        if (min <= 9) {
            sb.append("0");
        }
        sb.append(min);
        sb.append(":");
        if (sec <= 9) {
            sb.append("0");
        }
        sb.append(sec);
        return sb.toString();
    }
    public static SpannableString getTimeForDraw(long now, long target, int timeColor, int type) {

        sb.delete(0, sb.length());
        long diff = target - now;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;

        if (diff > 0) {
            day = diff / nd;//计算差多少天
            hour = diff % nd / nh;//计算差多少小时
            min = diff % nd % nh / nm;//计算差多少分钟
            sec = diff % nd % nh % nm / ns;//计算差多少秒//输出结果
        } else {
            return new SpannableString("");
        }
        // 用于保存时间字符大小的变量，默认为0
        int dayStrSize = 0;
        sb.delete(0, sb.length());
        if(type == 1) {
            sb.append("距结束仅剩\u3000");
        } else {
            sb.append("距开始仅剩\u3000");
        }
        if (day > 0) {
            sb.append(day);
            sb.append("天");
            dayStrSize = String.valueOf(day).length();
        }
        if (hour <= 9) {
            sb.append("0");
        }
        sb.append(hour);
        sb.append("时");
        if (min <= 9) {
            sb.append("0");
        }
        sb.append(min);
        sb.append("分");
        if (sec <= 9) {
            sb.append("0");
        }
        sb.append(sec);
        sb.append("秒");
        // 描述文字对象
        SpannableString ssb = new SpannableString(sb);
        if(day > 0) {
            ssb.setSpan(new ForegroundColorSpan(timeColor),6,6+dayStrSize, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        ssb.setSpan(new ForegroundColorSpan(timeColor),sb.indexOf("时")-2,sb.indexOf("时"),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new ForegroundColorSpan(timeColor),sb.indexOf("分")-2,sb.indexOf("分"),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new ForegroundColorSpan(timeColor),sb.indexOf("秒")-2,sb.indexOf("秒"),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // ForegroundColorSpan timeText = new ForegroundColorSpan(timeColor);
        return ssb;
    }
    public static long isOverTime(long now, long target) {
        long diff = target - now;
        return diff;
    }
}