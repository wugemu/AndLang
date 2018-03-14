package com.example.test.andlangtest.Presenter;


import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangAdapter;
import com.example.test.andlang.andlangutil.BaseLangPresenter;
import com.example.test.andlang.andlangutil.BaseLangViewHolder;
import com.example.test.andlang.andlangutil.LangHttp;
import com.example.test.andlangtest.R;
import com.example.test.andlangtest.ViewModel.MainViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 18-3-7.
 */

public class MainPresenter extends BaseLangPresenter<MainViewModel> {
    private List<String> datalist;
    private BaseLangAdapter adapter;
    public MainPresenter(BaseLangActivity activity, Class<MainViewModel> modelClass){
        super(activity,modelClass);
    }

    @Override
    public void initModel(){
        if(model!=null){
            model.setValue("ywtDiscount","123124141");
            model.setValue("tv_hello2","afdafsfafsd");
            model.setValue("et_hello","121111");
            datalist=new ArrayList<String>();
            datalist.add("121");
            datalist.add("122");
            datalist.add("123");
            datalist.add("1214");
            adapter=new BaseLangAdapter<String>(activity, R.layout.listview_main_item) {
                @Override
                public void convert(BaseLangViewHolder helper, int postion, String item) {
                    TextView tv_item_hello=(TextView) helper.getView(R.id.tv_item_hello);
                    tv_item_hello.setText(item);
                }
            };
            model.setListValue("lv_hellow",datalist,adapter);
        }

        //网络请求
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("type", "2");
        param.put("needAdver", "1");
        LangHttp.postHttp(activity,"http://192.168.1.1/sysAction!queryVerNew.action",param,MainViewModel.class,this,"test");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                model.setValue("tv_hello","点击按钮");
                Log.d("0.0",(String) model.getValueFromView("et_hello"));
                if(datalist!=null&&adapter!=null){
                    datalist.add(model.getValueFromView("et_hello"));
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.lv_hellow:
                model.setValue("et_hello",datalist.get(position));
                break;
        }
    }

    @Override
    public void success(MainViewModel busModel, String tag) {

        model.updateModel(busModel);
        model.notifyView();
    }

    @Override
    public void empty(String tag) {

    }

    @Override
    public void error(String tag) {

    }
}
