package com.example.test.andlang.cockroach;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.test.andlang.R;
import com.example.test.andlang.R2;
import com.example.test.andlang.andlangutil.BaseLangActivity;

import java.util.Observable;

import butterknife.OnClick;


/**
 * Created by wanjian on 2018/5/21.
 */

public class DebugSafeModeTipActivity extends BaseLangActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_safe_mode_warning;
    }

    @Override
    public void initView() {
        initTitleBar(true,"Crash 日志");
    }

    @Override
    public void initPresenter() {

    }

    @OnClick(R2.id.log)
    public void clickLogItem(){
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(CrashLogFragment.class.getName());
        if (fragment == null) {
            fragment = new CrashLogFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, CrashLogFragment.class.getName())
                .commit();
    }

    @Override
    public void initData() {

    }

    @Override
    public void notifyView(String arg) {

    }
}
