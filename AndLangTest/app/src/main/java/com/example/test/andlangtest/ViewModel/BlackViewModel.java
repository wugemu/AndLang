package com.example.test.andlangtest.ViewModel;

import com.example.test.andlang.andlangutil.BaseLangViewModel;

/**
 * Created by root on 18-3-8.
 */

public class BlackViewModel extends BaseLangViewModel<BlackViewModel> {
    private String iv_fragment;

    @Override
    public void updateModel(BlackViewModel model) {
        this.iv_fragment=model.iv_fragment;
    }
}
