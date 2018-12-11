package com.example.test.andlang.util;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.v4.app.NotificationManagerCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;


/**
 * Created by root on 18-3-14.
 */

public class BaseLangUtil {

    private static StringBuilder sb = new StringBuilder();

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
    //Screen Width
    public static int getDisplayWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    //检验手机号码是否有效
    public static boolean isMobile(String str){
        if (str == null || "".equals(str)) {
            return false;
        }
        if (str.length() != 11) {
            return false;
        }
        if (!str.startsWith("1")) {
            return false;
        }
        return true;
    }

    public static final String getLenthTxt(String str,int lenth){
        sb.delete(0,sb.length());
        if(isEmpty(str)){
            return "";
        }
        if(str.length()>lenth){
            sb.append(str.substring(0,lenth));
            sb.append("...");
            return sb.toString();
        }
        return str;
    }

    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static int setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return 0;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        return params.height;
    }

    public static boolean isNotificationEnabled(Context context) {

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        boolean areNotificationsEnabled = notificationManagerCompat.areNotificationsEnabled();
        return areNotificationsEnabled;
    }

    /**
     * dp转换成px
     *
     * @param context Context
     * @param dp      dp
     * @return px值
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    //判断是否是纯数字字符串
    public static boolean isNumericStr(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public static String getUrlParma(String url, String key) {

        if (url != null && key != null && url.contains(key)) {
            String paramUrl=url.substring(url.indexOf("?")+1);
            if (paramUrl != null && paramUrl.indexOf("=") > -1) {
                String[] arrTemp = paramUrl.split("&");
                for (String str : arrTemp) {
                    String[] qs = str.split("=");
                    if(key.equals(qs[0])){
                        return qs[1];
                    }
                }
            }
        }
        return "";
    }
}
