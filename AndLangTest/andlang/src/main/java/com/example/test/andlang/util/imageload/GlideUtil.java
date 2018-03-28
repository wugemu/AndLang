package com.example.test.andlang.util.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.Gravity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.test.andlang.R;

/**
 * Created by 1 on 2018/1/16.
 */
public class GlideUtil {

    private static GlideUtil glide;
    private GlideUtil() {

    }

    public static GlideUtil getInstance() {
        if (glide == null) {
            glide = new GlideUtil();
        }
        return glide;
    }

    public void displayHead(final Context context, String url, final ImageView imageView){
        Glide.with(context).load(url).asBitmap().placeholder(R.mipmap.def_head_image).centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                imageView.setImageDrawable(createRoundImageWithBorder(context,resource,(int)context.getResources().getDimension(R.dimen.lang_broder), Color.WHITE));
            }
        });
    }

    public void displayBitmap(final Context context, String url, final ImageView imageView){
        Glide.with(context).load(url).asBitmap().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                imageView.getLayoutParams().width=resource.getWidth();
                imageView.getLayoutParams().height=resource.getHeight();
                imageView.setImageBitmap(resource);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
               }
        });
    }

    public void displayByScreenWidth(final Context context, String url, final ImageView imageView, final int screenWidth){
        Glide.with(context).load(url).asBitmap().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
               double kgb= resource.getHeight()*1.0/resource.getWidth();
                imageView.getLayoutParams().height= (int) (screenWidth*kgb);
                imageView.setImageBitmap(resource);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        });
    }

    public void displayBrand(final Context context, String url, final ImageView imageView){
        Glide.with(context).load(url).asBitmap().placeholder(R.mipmap.image_def).centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                imageView.setImageDrawable(createRoundImageWithBorder(context,resource,(int)context.getResources().getDimension(R.dimen.lang_broder2), R.color.lang_colorHint));
            }
        });
    }

    public void display(Context context, String url, ImageView imageView){
        display(context,url,imageView, R.mipmap.image_def);
    }

    public void display(Context context, String url, ImageView imageView, int defaultImgeId){
        Glide.with(context)
                .load(url)
                .placeholder(defaultImgeId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    public void displayGif(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    private Drawable createRoundImageWithBorder(Context context, Bitmap bitmap, int borderWidthHalf, int borderColor){
        //原图宽度
        int bitmapWidth = bitmap.getWidth();
        //原图高度
        int bitmapHeight = bitmap.getHeight();
        //边框宽度 pixel
//        int borderWidthHalf = (int)context.getResources().getDimension(R.dimen.head_broder);

        //转换为正方形后的宽高
        int bitmapSquareWidth = Math.min(bitmapWidth,bitmapHeight);

        //最终图像的宽高
        int newBitmapSquareWidth = bitmapSquareWidth+borderWidthHalf;

        Bitmap roundedBitmap = Bitmap.createBitmap(newBitmapSquareWidth,newBitmapSquareWidth, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundedBitmap);
        int x = borderWidthHalf + bitmapSquareWidth - bitmapWidth;
        int y = borderWidthHalf + bitmapSquareWidth - bitmapHeight;

        //裁剪后图像,注意X,Y要除以2 来进行一个中心裁剪
        canvas.drawBitmap(bitmap, x/2, y/2, null);
        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidthHalf);
        borderPaint.setColor(borderColor);
        borderPaint.setAntiAlias(true);
        //添加边框
        canvas.drawCircle(canvas.getWidth()/2, canvas.getWidth()/2, newBitmapSquareWidth/2, borderPaint);

        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(),roundedBitmap);
        roundedBitmapDrawable.setGravity(Gravity.CENTER);
        roundedBitmapDrawable.setCircular(true);
        return roundedBitmapDrawable;
    }

}
