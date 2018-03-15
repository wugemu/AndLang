package com.example.test.andlang.andlangutil;

import android.util.Log;
import android.view.View;

/**
 * Created by root on 18-3-8.
 */

public abstract class BaseLangPresenter<T> implements LangHttpInterface<T>{
    public static String TAG="andlang";
    public BaseLangActivity activity;
    public BaseLangViewModel model;
    public BaseLangPresenter(BaseLangActivity activity, Class<? extends BaseLangViewModel> modelClass){
        try {
            this.activity=activity;
            model=modelClass.newInstance();
            model.registView(activity);//注册Activity的View到Model
        }catch (Exception e){
            Log.d("0.0",e.getMessage());
        }
    }
    public BaseLangPresenter(BaseLangFragment fragment,BaseLangActivity activity, Class<? extends BaseLangViewModel> modelClass){
        try {
            this.activity=activity;
            model=modelClass.newInstance();
            model.registView(fragment);//注册fragment的View到Model
        }catch (Exception e){
            Log.d("0.0",e.getMessage());
        }
    }
    //注册view绑定model
    public void registView(String key,View view) { //注册View到Model
        if(model!=null){
            model.registView(key,view);
        }
    }

    public abstract void initModel();
}
