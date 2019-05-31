package com.example.test.andlang.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test.andlang.R;
import com.example.test.andlang.R2;
import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangApplication;
import com.example.test.andlang.cockroach.DebugSafeModeTipActivity;
import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.Constants;
import com.example.test.andlang.util.ToastUtil;
import com.example.test.andlang.widget.dialogview.BaseLangDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class LangTestActivity extends BaseLangActivity {
    public static final String HOST_DEBUG="SplashActivity_debug";//测试环境
    public static final String HOST_PRERE="SplashActivity_prere";//预发环境
    public static final String HOST_LOCAL="SplashActivity_local";//开发环境
    public static final String HOST_USERLOCAL="SplashActivity_userlocal";//本地环境

    @BindView(R2.id.lv_lang_test)
    ListView lv_lang_test;
    @BindView(R2.id.tv_text_host)
    TextView tv_text_host;

    List<String> list = new ArrayList<String>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_lang_test;
    }

    @Override
    public void initView() {
        initTitleBar(true,"app测试人员配置页面");
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {
        String str=BaseLangApplication.getInstance().getSpUtil().getString(LangTestActivity.this,Constants.TEST_HOST_TAG);
        if(HOST_USERLOCAL.equals(str)){
            tv_text_host.setText("本地环境");
        }else if(HOST_LOCAL.equals(str)){
            tv_text_host.setText("开发环境");
        }else if(HOST_DEBUG.equals(str)){
            tv_text_host.setText("测试环境");
        }else if(HOST_PRERE.equals(str)){
            tv_text_host.setText("预发环境");
        }else {
            tv_text_host.setText("正式环境");
        }

        list.add("本地环境");
        list.add("开发环境");
        list.add("测试环境");
        list.add("预发环境");
        list.add("正式环境");
        list.add("");
        list.add("查看本地日志");
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        lv_lang_test.setAdapter(adapter);
    }

    @OnItemClick(R2.id.lv_lang_test)
    public void clickItem(int postion){
        String str=list.get(postion);
        if("本地环境".equals(str)){
            BaseLangApplication.getInstance().getSpUtil().putString(LangTestActivity.this, Constants.TEST_HOST_TAG,HOST_USERLOCAL);
            restartApp();
        }else if("开发环境".equals(str)){
            BaseLangApplication.getInstance().getSpUtil().putString(LangTestActivity.this, Constants.TEST_HOST_TAG,HOST_LOCAL);
            restartApp();
        }else if("测试环境".equals(str)){
            BaseLangApplication.getInstance().getSpUtil().putString(LangTestActivity.this, Constants.TEST_HOST_TAG,HOST_DEBUG);
            restartApp();
        }else if("预发环境".equals(str)){
            BaseLangApplication.getInstance().getSpUtil().putString(LangTestActivity.this, Constants.TEST_HOST_TAG,HOST_PRERE);
            restartApp();
        }else if("正式环境".equals(str)){
            BaseLangApplication.getInstance().getSpUtil().putString(LangTestActivity.this, Constants.TEST_HOST_TAG,"");
            restartApp();
        }else if("查看本地日志".equals(str)){
            Intent intent = new Intent(LangTestActivity.this, DebugSafeModeTipActivity.class);
            ActivityUtil.getInstance().start(LangTestActivity.this,intent);
        }
    }


    //重新启动
    public void restartApp() {
        ToastUtil.show(LangTestActivity.this,"app重新启动中");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = BaseLangApplication.getInstance().getPackageManager()
                        .getLaunchIntentForPackage(BaseLangApplication.getInstance().getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                BaseLangApplication.getInstance().startActivity(intent);
            }
        },500);
    }

    @Override
    public void notifyView(String arg) {

    }
}
