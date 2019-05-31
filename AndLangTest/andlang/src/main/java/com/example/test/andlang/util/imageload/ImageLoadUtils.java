package com.example.test.andlang.util.imageload;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

/**
 * Created by root on 18-3-9.
 */

public final class ImageLoadUtils {
    private static IInnerImageSetter sImageSetter;
    public static void setImageSetter(@NonNull IInnerImageSetter imageSetter) {
        sImageSetter = imageSetter;
    }

    /**
     * load image using {@link IInnerImageSetter}
     * @param view the imageView instance
     * @param url image url
     * @param <IMAGE> ImageView class type
     */
    public static <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view, @Nullable String url) {
       if(sImageSetter==null){

           return;
       }
        sImageSetter.doLoadImageUrl(view, url);
    }

    /**
     * load image using {@link IInnerImageSetter}
     * @param view the imageView instance
     * @param url image url
     * @param <IMAGE> ImageView class type
     */
    public static <IMAGE extends ImageView> void doLoadImageUrlCenterCrop(@NonNull IMAGE view, @Nullable String url) {
        if(sImageSetter==null){

            return;
        }
        sImageSetter.doLoadImageUrlCenterCrop(view, url);
    }

    /**
     * load image using {@link IInnerImageSetter}
     * @param view the imageView instance
     * @param url image url
     * @param <IMAGE> ImageView class type
     */
    public static <IMAGE extends ImageView> void doLoadImageUrlFitCenter(@NonNull IMAGE view, @Nullable String url) {
        if(sImageSetter==null){

            return;
        }
        sImageSetter.doLoadImageUrlFitCenter(view, url);
    }

    /**
     * load image using {@link IInnerImageSetter}
     * @param view the imageView instance
     * @param url image url
     * @param <IMAGE> ImageView class type
     */
    public static <IMAGE extends ImageView> void doLoadCircleImageUrl(@NonNull IMAGE view, @Nullable String url) {
        if(sImageSetter==null){

            return;
        }
        sImageSetter.doLoadCircleImageUrl(view, url);
    }

    /**
     * load image using {@link IInnerImageSetter}
     * @param view the imageView instance
     * @param url image url
     * @param <IMAGE> ImageView class type
     */
    public static <IMAGE extends ImageView> void doLoadByImageLoader(@NonNull IMAGE view, @Nullable String url) {
        if(sImageSetter==null){

            return;
        }
        sImageSetter.doLoadByImageLoader(view, url);
    }

    /**
     * load image using {@link IInnerImageSetter}
     * @param view the imageView instance
     * @param resId image resId
     * @param <IMAGE> ImageView class type
     */
    public static <IMAGE extends ImageView> void doLoadImageRes(@NonNull IMAGE view, @Nullable int resId) {
        if(sImageSetter==null){

            return;
        }
        sImageSetter.doLoadImageRes(view, resId);
    }

    public static <IMAGE extends ImageView> void doLoadImageRound(@NonNull IMAGE view, @Nullable String url,float round) {
        if(sImageSetter==null){

            return;
        }
        sImageSetter.doLoadImageRound(view, url,round);
    }

}
