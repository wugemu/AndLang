package com.example.test.andlang.andlangutil;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

/**
 * Created by root on 18-3-9.
 */

public interface IInnerImageSetter {
    <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view, @Nullable String url);
}
