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

import com.example.test.andlang.R;

import java.util.Observer;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public abstract class BaseLangFragment<T extends BaseLangPresenter> extends Fragment implements Observer{

    public BaseLangActivity activity;
    public T presenter;

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
        View rootview= inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, rootview);
        bindView();
        if(presenter!=null&&presenter.model!=null){
            presenter.model.addObserver(this);
        }else {
            Log.e(BaseLangPresenter.TAG,"presenter 未初始化");
        }
        bindListener();
        initData();
        return rootview;
    }

    public void initData(){
        if(presenter==null){
            Log.e(BaseLangPresenter.TAG,"presenter 未创建 ");
        }else {
            presenter.initModel();
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

    public abstract int getLayoutId();
    public abstract void bindView();
    public abstract void bindListener();
}
