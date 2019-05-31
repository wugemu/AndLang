package com.example.test.andlangtest.Presenter;


import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangAdapter;
import com.example.test.andlang.andlangutil.BaseLangPresenter;
import com.example.test.andlang.andlangutil.BaseLangViewHolder;
import com.example.test.andlang.andlangutil.LangHttp;
import com.example.test.andlang.andlangutil.LangHttpInterface;
import com.example.test.andlangtest.AndLangApp;
import com.example.test.andlangtest.R;
import com.example.test.andlangtest.ViewModel.MainViewModel;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 18-3-7.
 */

public class MainPresenter extends BaseLangPresenter {
    private List<String> datalist;
    public MainPresenter(BaseLangActivity activity, Class<MainViewModel> modelClass){
        super(activity,modelClass);
    }

    @Override
    public void initModel(){
        if(model!=null){
            ((MainViewModel) model).setMessage("12414");
            ((MainViewModel) model).setEt_hello("1111111");
            datalist=new ArrayList<String>();
            datalist.add("121");
            datalist.add("122");
            datalist.add("123");
            datalist.add("1214");
            ((MainViewModel) model).setLv_hellow(datalist);
            notifyView("1");
        }
        //网络请求
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("type", "2");
        param.put("needAdver", "1");
        LangHttp.postHttp(activity, "https://192.168.1.1/version/getVersionSix.shtml", param, new TypeToken<Map<String, Object>>(){}.getType(), new LangHttpInterface<Map<String, Object>>() {
            @Override
            public void success(Map<String, Object> map) {
                notifyView("1");
            }

            @Override
            public void empty() {

            }

            @Override
            public void error() {

            }

            @Override
            public void fail() {

            }
        });
    }

    public void addListValue(String value){
        if(datalist!=null){
            datalist.add(value);
            notifyView("2");
        }
    }
    public List<String> getListValue(){
        return datalist;
    }

}
