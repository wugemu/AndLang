package com.example.test.andlangtest.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.widget.webview.BaseLangWebActivity;
import com.example.test.andlangtest.Presenter.WebPresenter;
import com.example.test.andlangtest.R;
import com.example.test.andlangtest.ViewModel.WebModel;

import java.util.Observable;

public class WebActivity extends BaseLangWebActivity {

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        Intent intent=getIntent();
        if(intent!=null){
            String url=intent.getStringExtra("url");
            if(!BaseLangUtil.isEmpty(url)){
                initTitleBar(true,BaseLangUtil.getUrlParma(url,"title"));
                loadUrl(url);
            }
        }
    }

    @Override
    public void initPresenter() {
        presenter=new WebPresenter(this, WebModel.class);
    }

    @Override
    public void notifyView(String arg) {

    }
}
