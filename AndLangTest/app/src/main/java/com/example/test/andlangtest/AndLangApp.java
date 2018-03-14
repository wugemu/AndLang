package com.example.test.andlangtest;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.test.andlang.andlangutil.IInnerImageSetter;
import com.example.test.andlang.andlangutil.ImageUtils;

/**
 * Created by root on 18-3-9.
 */

public class AndLangApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageUtils.setImageSetter(new IInnerImageSetter() {
            @Override
            public <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view, @Nullable String url) {
                if (url.toLowerCase().contains(".gif")) {
                    Glide.with(getApplicationContext())
                            .load(url)
                            .asGif()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(view);
                }else {
                    Glide.with(getApplicationContext())
                            .load(url)
                            .placeholder(com.example.test.andlang.R.mipmap.ic_launcher)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(view);
                }
            }
        });
    }
}
