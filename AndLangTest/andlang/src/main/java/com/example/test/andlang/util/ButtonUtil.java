package com.example.test.andlang.util;

/**
 * Created by think on 2016-07-13.
 */
public class ButtonUtil {
    //判断button连续点击的问题
    private static long lastClickTime1;
    private static long lastClickTime2;
    public static long lastClickTime3;
    public static int btnId=-1;
    public static boolean isFastDoubleClick(int btn) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime1;
        if(btnId!=btn)
        {
            btnId=btn;
            return false;
        }
        if ( 0 < timeD && timeD <3000) {
            return true;
        }
        lastClickTime1 = time;
        return false;
    }
    public static boolean isFastDoubleClick2() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime2;
        if ( 0 < timeD && timeD <3000) {
            return true;
        }
        lastClickTime2 = time;
        return false;
    }
    public static boolean isFastDoubleClick3() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime3;
        if ( 0 < timeD && timeD <5000) {
            return true;
        }
        lastClickTime3 = time;
        return false;
    }
}
