package com.example.test.andlang.util;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * Created by root on 18-3-14.
 */

public class BaseLangUtil {
    public static boolean isEmpty(String str){
        if(str==null||str.length()==0){
            return true;
        }
        return false;
    }
    //为某个域名 设置cookie
    public static void syncCookie(Context context,String url,String cookie) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, cookie);
        CookieSyncManager.getInstance().sync();
    }
    //获取url中的参数
    public static String getUrlParma(String url, String key) {
        try {
            url = URLDecoder.decode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {

        }
        if (url != null && key != null && url.contains(key)) {
            int start = url.indexOf(key + "=");
            url.indexOf("&", start);
            int end = url.indexOf("&", start);
            if (end == -1) {
                end = url.length();
            }
            return url.substring(start + key.length() + 1, end);
        }
        return "";
    }
}
