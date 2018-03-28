package com.example.test.andlang.andlangutil;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.example.test.andlang.util.PreferencesUtil;
import com.example.test.andlang.util.imageload.*;
import com.example.test.andlang.util.imageload.IInnerImageSetter;

/**
 * Created by root on 18-3-27.
 */

public class BaseLangApplication extends Application {
    private static BaseLangApplication mApp;
    private PreferencesUtil mSpUtil;
    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }

    private void initData() {
        mApp = this;
        initImageLoad();
    }

    private void initImageLoad() {
        ImageLoadUtils.setImageSetter(new IInnerImageSetter() {
            @Override
            public <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view, @Nullable String url) {
                if(url.toLowerCase().contains(".gif")) {
                    GlideUtil.getInstance().displayGif(getApplicationContext(),url,view);
                } else {
                    GlideUtil.getInstance().display(getApplicationContext(),url,view);
                }
            }
        });
    }

    public static BaseLangApplication getInstance() {
        return mApp;
    }

    public synchronized PreferencesUtil getSpUtil() {
        if (mSpUtil == null)
            mSpUtil = new PreferencesUtil(this, PreferencesUtil.PREFERENCES_DEFAULT);
        return mSpUtil;
    }
}
