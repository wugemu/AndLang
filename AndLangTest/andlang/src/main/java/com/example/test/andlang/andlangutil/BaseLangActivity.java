package com.example.test.andlang.andlangutil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Observer;

import butterknife.ButterKnife;


/**
 * Created by lang on 18-3-7.
 */

public abstract class BaseLangActivity<T extends BaseLangPresenter> extends AppCompatActivity implements Observer {
    public T presenter;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutId();
        ButterKnife.bind(this);
        bindView();
        if(presenter!=null&&presenter.model!=null){
            presenter.model.addObserver(this);
        }else {
            Log.e(BaseLangPresenter.TAG,"presenter 未初始化");
        }
        bindListener();
        initData();
    }

    public void initData(){
        if(presenter==null){
            Log.e(BaseLangPresenter.TAG,"presenter 未创建 ");
        }else {
            presenter.initModel();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter!=null&&presenter.model!=null){
            presenter.model.deleteObserver(this);
        }
    }
    public abstract void setLayoutId();//设置布局id
    public abstract void bindView();
    public abstract void bindListener();
}
