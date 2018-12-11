package com.example.test.andlang.http;

import android.content.Context;

import com.example.test.andlang.andlangutil.BaseLangApplication;
import com.example.test.andlang.util.LogUtil;
import com.example.test.andlang.util.NetUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestCacheI implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetUtil.isConnected(BaseLangApplication.getInstance().getApplicationContext())){
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        int code = response.code();
        if ( code == 504){
            response = chain.proceed(chain.request());
        }
        if(response.networkResponse()!=null){
            LogUtil.e("从网络获取数据:"+request.toString());
        } else
        if (response.cacheResponse()!=null){
            LogUtil.e("从本地缓存获取数据:"+request.toString());
        }

        return response;
    }
}
