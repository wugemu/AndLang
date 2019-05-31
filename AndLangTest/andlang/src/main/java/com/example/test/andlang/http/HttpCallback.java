package com.example.test.andlang.http;

import android.content.Context;

import okhttp3.Request;

/**
 * Created by 1 on 2016/1/20.
 */
public abstract class HttpCallback {

    /**
     * UI Thread
     *
     * @param request
     */
    public void onBefore(Request request) {

    }

    /**
     * UI Thread
     *
     * @param
     */
    public void onAfter() {
    }

    /**
     * UI Thread
     *
     * @param progress
     */
    public void inProgress(float progress) {

    }

    //code=-1 时code不存在
    public void onError(Request request, Exception e,int code){

    }


    public void loginFail(){

    }

    public void errorCode(int code){

    }

    public abstract void onResponse(String response);
}
