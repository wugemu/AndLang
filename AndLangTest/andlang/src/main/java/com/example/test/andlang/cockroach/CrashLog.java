package com.example.test.andlang.cockroach;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.example.test.andlang.andlangutil.BaseLangApplication;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 */
public class CrashLog {
    public static final String TAG = "CrashLog";

    public static void saveCrashLog(Context context, Throwable throwable) {
        Map<String, String> map = collectDeviceInfo(context);
        saveCrashInfo2File(context, throwable, map);
    }

    public static String getCrashLog(Context context, Throwable throwable) {
        Map<String, String> map = collectDeviceInfo(context);
        return getCrashInfo(context, throwable, map);
    }


    private static Map<String, String> collectDeviceInfo(Context ctx) {
        Map<String, String> infos = new TreeMap<>();
        try {
            String inviteCode=BaseLangApplication.getInstance().getSpUtil().getString(ctx,"inviteCode");
            if(!BaseLangUtil.isEmpty(inviteCode)){
                infos.put("inviteCode", inviteCode);//用户邀请码
            }
            infos.put("systemVersion", Build.VERSION.RELEASE);
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
            }
        }
        return infos;
    }

    private static String getCrashInfo(Context context, Throwable ex, Map<String, String> infos) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);

        try {
            return sb.toString();
        } catch (Exception e) {

        }
        return "";
    }

    private static void saveCrashInfo2File(Context context, Throwable ex, Map<String, String> infos) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);

        try {
            long timestamp = System.currentTimeMillis();
            String time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            String cachePath = crashLogDir(context);
            LogUtil.d("0.0","crash日志目录："+cachePath);
            File dir = new File(cachePath);
            dir.mkdirs();
            FileOutputStream fos = new FileOutputStream(cachePath + fileName);
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (Exception e) {
        }
    }

    public static String crashLogDir(Context context) {
        return context.getCacheDir().getPath() + File.separator + "crash" + File
                .separator;
    }
}
