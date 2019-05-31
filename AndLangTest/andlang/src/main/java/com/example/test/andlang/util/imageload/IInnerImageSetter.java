package com.example.test.andlang.util.imageload;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

/**
 * Created by root on 18-3-9.
 */

public interface IInnerImageSetter {
    <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view, @Nullable String url);
    <IMAGE extends ImageView> void doLoadCircleImageUrl(@NonNull IMAGE view, @Nullable String url);
    <IMAGE extends ImageView> void doLoadByImageLoader(@NonNull IMAGE view, @Nullable String url);
    <IMAGE extends ImageView> void doLoadImageRes(@NonNull IMAGE view, @Nullable int resId);
    <IMAGE extends ImageView> void doLoadImageUrlCenterCrop(@NonNull IMAGE view, @Nullable String url);
    <IMAGE extends ImageView> void doLoadImageUrlFitCenter(@NonNull IMAGE view, @Nullable String url);
    <IMAGE extends ImageView> void doLoadImageRound(@NonNull IMAGE view, @Nullable String url,float round);
}
