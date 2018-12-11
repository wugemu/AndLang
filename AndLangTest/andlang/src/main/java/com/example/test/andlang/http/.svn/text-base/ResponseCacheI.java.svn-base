package com.example.test.andlang.http;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ResponseCacheI implements Interceptor {
    private Context context;

    public ResponseCacheI(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        return  response.newBuilder()
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public,max-age="+60)//缓存1分钟
                .removeHeader("Pragma")
                .build();
    }
}
