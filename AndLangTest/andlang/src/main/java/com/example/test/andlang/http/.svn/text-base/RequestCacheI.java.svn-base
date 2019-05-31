package com.example.test.andlang.http;

import com.example.test.andlang.log.AppCrashHandler;
import com.example.test.andlang.util.LogUtil;

import java.io.IOException;
import java.net.UnknownHostException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestCacheI implements Interceptor {

    /**
     * 最大重试次数
     */
    private int maxRetry=2; //总共会请求两次，网络请求失败，会再请求1次，再失败，走本地缓存，缓存未命中，抛出网络请求异常

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response=null;

        //--- 重试机制 start
        int count = 0;
        IOException exc=null;
        while (count < maxRetry) {
            try {
                //发起网络请求
                response = chain.proceed(request);
                // 得到结果跳出循环
                break;
            } catch (IOException exception) {
                count++;
                exc=exception;//记录异常
                if(exception!=null) {
                    try {
                        String msg="请求将进行重试----"+exception.toString()+"----"+request.toString();
                        LogUtil.e(msg);
                        //保存日志
                        AppCrashHandler crashHandler = AppCrashHandler.getInstance();
                        if(crashHandler!=null) {
                            crashHandler.saveReqInfo2File(exc.getCause(),msg);
                        }
                    }catch (Exception e){
                        LogUtil.e("网络请求日志记录异常----"+e.toString());
                    }

                    //重试
                    if (exception instanceof UnknownHostException) {
                        // Unknown host 时等待2s重试
                        try {
                            Thread.sleep(2000);
                        }catch (Exception e){

                        }
                        continue;
                    }else {
                        // 其它异常无需等待 立即重试
                        continue;
                    }
                }
                break;
            }
        }
        if(response==null) {
            //网络请求失败 走本地缓存逻辑
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            response = chain.proceed(request);
            int code = response.code();
            if ( code == 504) {
                //网络请求失败 又未命中缓存
                if(exc!=null){
                    throw exc;
                }else {
                    throw new IOException("未知异常...");
                }
            }
        }
        //--- 重试机制 end

        if(response.networkResponse()!=null){
            LogUtil.e("从网络获取数据:"+request.toString());
        } else
        if (response.cacheResponse()!=null){
            LogUtil.e("从本地缓存获取数据:"+request.toString());
        }

        return response;
    }
}
