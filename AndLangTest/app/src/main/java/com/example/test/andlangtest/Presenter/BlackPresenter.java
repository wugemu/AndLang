package com.example.test.andlangtest.Presenter;

import android.view.View;
import android.widget.AdapterView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangFragment;
import com.example.test.andlang.andlangutil.BaseLangPresenter;
import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.example.test.andlangtest.ViewModel.BlackViewModel;

/**
 * Created by root on 18-3-8.
 */

public class BlackPresenter extends BaseLangPresenter<BlackViewModel>{
    public BlackPresenter(BaseLangFragment fragment,BaseLangActivity activity,  Class<? extends BaseLangViewModel> modelClass) {
        super(fragment,activity,modelClass);
    }

    @Override
    public void initModel() {
        if(model!=null) {
            model.setValue("iv_fragment", "http://img.zcool.cn/community/01b0d857b1a34d0000012e7e87f5eb.gif");
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void success(BlackViewModel busModel, String tag) {

    }

    @Override
    public void empty(String tag) {

    }

    @Override
    public void error(String tag) {

    }
}
