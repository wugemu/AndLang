package com.example.test.andlang.util;

import android.util.Log;

/**
 * Created by Bill56 on 2017/9/5.
 */

public class LogUtil {

    public static final String TAG = "0.0";  // 默认的tag

    private static final int level = 0;     // 当前的输出级别，只要大于该级别才输出,1-v,2-d,3-i,4-w,5-e，所以0是输出所有

    public static final boolean isLog = BaseLangUtil.isApkInDebug();  // 是否输出日志
    public static final boolean isCash= false; //是否内存检测应用安装
    public static void v(String tag,String msg) {
        if(isLog && 1>level) {
            Log.v(tag,msg);
        }
    }

    public static void v(String msg) {
        v(TAG,msg);
    }

    public static void d(String tag,String msg){
        if(isLog && 2>level) {
            Log.d(tag,msg);
        }
    }

    public static void d(String msg) {
        d(TAG,msg);
    }

    public static void i(String tag,String msg) {
        if(isLog && 3 >level) {
            Log.i(tag,msg);
        }
    }

    public static void i(String msg) {
        i(TAG,msg);
    }

    public static void w(String tag,String msg) {
        if(isLog && 4 > level) {
            Log.w(tag,msg);
        }
    }

    public static void w(String msg) {
        w(TAG,msg);
    }

    public static void e(String tag,String msg) {
        if(isLog && 5 > level) {
            Log.e(tag,msg);
        }
    }

    public static void e(String msg) {
        e(TAG,msg);
    }

}
