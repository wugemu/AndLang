package com.example.test.andlangtest.Presenter;

import android.view.View;
import android.widget.AdapterView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangFragment;
import com.example.test.andlang.andlangutil.BaseLangPresenter;
import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.example.test.andlangtest.ViewModel.BlackViewModel;

import java.util.Map;

/**
 * Created by root on 18-3-8.
 */

public class BlackPresenter extends BaseLangPresenter {
    public BlackPresenter(BaseLangFragment fragment, BaseLangActivity activity, Class<? extends BaseLangViewModel> modelClass) {
        super(fragment, activity, modelClass);
    }

    @Override
    public void initModel() {
        if (model != null) {
            model=(BlackViewModel)model;
            ((BlackViewModel) model).setIv_fragment("http://img.zcool.cn/community/01b0d857b1a34d0000012e7e87f5eb.gif");
            notifyView("1");
        }
    }
}
