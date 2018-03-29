package com.example.test.andlang.andlangutil;

import android.content.Context;
import android.util.Log;

import com.example.test.andlang.http.HttpCallback;
import com.example.test.andlang.http.HttpU;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.VersionUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by root on 18-3-13.
 */

public class LangHttp {
    public static Gson gson=new Gson();
    public static void postHttp(final BaseLangActivity context, final String url, Map<String, Object> params, final Type type, final LangHttpInterface listener){
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        params.put("versionAndr", VersionUtil.getVersionCode(context));
        HttpU.getInstance().post(context, url, params, context, new HttpCallback() {

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                context.showWaitDialog();
            }

            @Override
            public void onAfter() {
                super.onAfter();
                context.dismissWaitDialog();
            }

            @Override
            public void onResponse(String response) {
                try {
                    if(BaseLangUtil.isEmpty(response)){
                        if(listener!=null) {
                            listener.empty();
                        }
                    } else{
                        if(listener!=null) {
                            listener.success( gson.fromJson(response, type));
                         }
                    }
                }catch (Exception e){
                    Log.e(BaseLangPresenter.TAG,e.getMessage());
                    if(listener!=null) {
                        listener.error();
                    }
                }
            }

            @Override
            public void onError(Request request, Exception e, Context context) {
                super.onError(request, e, context);
                if(listener!=null){
                    listener.error();
                }
            }
        });
    }
    public static void getHttp(Context context, final String url, Map<String, Object> params, LangHttpInterface listener){
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        params.put("versionAndr", VersionUtil.getVersionCode(context));
        HttpU.getInstance().get(context, url, new HttpCallback() {

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
            }

            @Override
            public void onAfter() {
                super.onAfter();
            }

            @Override
            public void onResponse(String response) {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
