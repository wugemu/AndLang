package com.example.test.andlangtest.ViewModel;

import com.example.test.andlang.andlangutil.BaseLangViewModel;

import java.util.List;

/**
 * Created by root on 18-3-7.
 */

public class MainViewModel extends BaseLangViewModel<MainViewModel> {
    private String ywtDiscount;
    private String tv_hello2;
    private String et_hello;
    private List<String> lv_hellow;
    public void updateModel(MainViewModel model){
        this.ywtDiscount=model.ywtDiscount;
        this.tv_hello2=model.tv_hello2;
        this.et_hello=model.et_hello;
        this.lv_hellow=model.lv_hellow;
    }
}
