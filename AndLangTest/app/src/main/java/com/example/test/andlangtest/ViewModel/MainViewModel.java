package com.example.test.andlangtest.ViewModel;

import com.example.test.andlang.andlangutil.BaseLangViewModel;

import java.util.List;

/**
 * Created by root on 18-3-7.
 */

public class MainViewModel extends BaseLangViewModel {
    private String message;
    private String tv_hello2;
    private String et_hello;
    private List<String> lv_hellow;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTv_hello2() {
        return tv_hello2;
    }

    public void setTv_hello2(String tv_hello2) {
        this.tv_hello2 = tv_hello2;
    }

    public String getEt_hello() {
        return et_hello;
    }

    public void setEt_hello(String et_hello) {
        this.et_hello = et_hello;
    }

    public List<String> getLv_hellow() {
        return lv_hellow;
    }

    public void setLv_hellow(List<String> lv_hellow) {
        this.lv_hellow = lv_hellow;
    }
}
