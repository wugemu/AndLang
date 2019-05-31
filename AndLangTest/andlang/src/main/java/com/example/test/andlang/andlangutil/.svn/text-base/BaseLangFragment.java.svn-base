package com.example.test.andlang.andlangutil;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.test.andlang.R;
import com.example.test.andlang.util.LogUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.Observer;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public abstract class BaseLangFragment<T extends BaseLangPresenter> extends Fragment implements Observer{
    public RelativeLayout rlLoading;
    private ImageView ivLoading;
    public BaseLangActivity activity;
    public T presenter;
    private View rootview;

    public BaseLangFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity=(BaseLangActivity)getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview= inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, rootview);
        initView();
        initLoading();
        initPresenter();
        setObserModelForTag();
        initData();
        initModel();
        return rootview;
    }

    private void initModel(){
        if(presenter==null){
            LogUtil.e(BaseLangPresenter.TAG,"presenter 未创建 ");
        }else {
            presenter.initModel();
        }
    }

    private void setObserModelForTag(){
        if(presenter!=null&&presenter.model!=null){
            presenter.model.addObserver(this);
        }else {
            LogUtil.e(BaseLangPresenter.TAG,"presenter 未初始化");
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(presenter!=null&&presenter.model!=null){
            presenter.model.deleteObserver(this);
        }
    }

    public void initLoading(){
        rlLoading=ButterKnife.findById(rootview,R.id.rl_loading);
        ivLoading=ButterKnife.findById(rootview,R.id.iv_loading);
    }

    /**
     * 展示网络加载动画
     */
    public void showWaitDialog() {
        if (rlLoading != null && ivLoading != null) {
            final Animation operatingAnim = AnimationUtils.loadAnimation(activity, R.anim.loading);
            LinearInterpolator lin = new LinearInterpolator();
            operatingAnim.setInterpolator(lin);
            rlLoading.post(new Runnable() {
                @Override
                public void run() {
                    ivLoading.startAnimation(operatingAnim);
                }
            });
            rlLoading.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 关闭网络加载动画
     */
    public void dismissWaitDialog() {
        if (rlLoading != null && ivLoading != null) {
            ivLoading.clearAnimation();
            rlLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String localClassName = getClass().getSimpleName();
        MobclickAgent.onPageStart(localClassName); //手动统计页面("SplashScreen"为页面名称，可自定义)
    }

    @Override
    public void onPause() {
        super.onPause();
        String localClassName = getClass().getSimpleName();
        MobclickAgent.onPageEnd(localClassName);
    }

    public abstract int getLayoutId();
    public abstract void initView();
    public abstract void initPresenter();
    public abstract void initData();
}
