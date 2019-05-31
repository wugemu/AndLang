package com.example.test.andlang.util;

/**
 * Created by think on 2016-07-13.
 */
public class ButtonUtil {
    //判断button连续点击的问题
    private static long lastClickTime1;
    private static long lastClickTime2;
    public static long lastClickTime3;
    private static long lastClickTime4;
    public static long btnId=-1;
    private static int clickNum=0;
    public static boolean isFastDoubleClick(long btn) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime1;
        if(btnId!=btn)
        {
            btnId=btn;
            lastClickTime1 = time;
            return false;
        }
        if ( 0 < timeD && timeD <2500) {
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

    public static boolean isFastTenClick(){
        long time = System.currentTimeMillis();
        if(clickNum==0){
            lastClickTime4=time;
        }
        long timeD = time - lastClickTime4;
        if(0<=timeD && timeD<10000){
            clickNum++;
            LogUtil.d("0.0",clickNum+"");
            if(clickNum==10){
                clickNum=0;
                return true;
            }else {
                return false;
            }
        }
        clickNum=0;
        lastClickTime4 = time;
        return false;
    }
}
