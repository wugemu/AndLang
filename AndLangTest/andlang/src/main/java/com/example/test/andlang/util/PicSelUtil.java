package com.example.test.andlang.util;

import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.example.test.andlang.andlangutil.BaseLangApplication;

import java.io.File;
import java.io.IOException;

/**
 * 图片选择的工具类，可以选择，也可以从本地获取
 * Created by Bill56 on 2016/7/7.
 */
public class PicSelUtil {
    // 四个文件引用
    public static String[] CACHE_FILE_NAME = new String[]{"camera_0.jpg", "camera_1.jpg", "camera_2.jpg", "camera_3.jpg"};

    // 所需的全部权限——摄像
    public static String[] TAKE_PHOTO_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static String[] WRITE_EXTERNAL_STORAGE_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    // 权限检查相关
    public static final int TAKE_PHOTO_CODE = 100; // 请求码
    public static final int SELECT_PHOTO_CODE = 200; // 请求码
    public static final int INIT_IMAGE_HEAPLER_CODE = 300; // 请求码
    public static final int SAVE_TO_PICTURE_CODE = 400; // 请求码
    public static final int GET_NYSO_IMAGES = 500; // 请求码

    //读写本地外置sd卡
    //public static File imageCacheDir = new File(Environment.getExternalStorageDirectory(), "yxx");
    //读写内置sd卡
    //public static File imageCacheDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "yxx");

    public static File imageDir=null;
    //读写本地外置sd卡
    public static File getImgFileDir()
    {
        if(imageDir==null)
        {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)){
                imageDir = new File(Environment.getExternalStorageDirectory(), BaseLangApplication.tmpImageDir);
            }else {
                imageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), BaseLangApplication.tmpImageDir);
            }
        }
        return imageDir;
    }

    //获取缓存文件
    public static File getCacheDir(){
        // 如果不存在目录，则创建
        if (!getImgFileDir().exists()) {
            getImgFileDir().mkdir();
        }
        // 创建File对象，用于存储牌照后的图片
        File cacheFile = new File(getImgFileDir(), "langcache");
        return cacheFile;
    }

    public static Uri getImageCacheUri(Context context,String fileName) {
        // 如果不存在目录，则创建
        if (!getImgFileDir().exists()) {
            getImgFileDir().mkdir();
        }
        // 创建File对象，用于存储牌照后的图片
        File outputImage = new File(getImgFileDir(), fileName);
        try {
            // 如果存在则删除
            if (outputImage.exists()) {
                outputImage.delete();
            }
            // 创建一个新的文件
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileUtil.file2Uri(context,outputImage);
    }

    public static File getImageFile(String fileName) {
        // 如果不存在目录，则创建
        if (!getImgFileDir().exists()) {
            getImgFileDir().mkdir();
        }
        // 创建File对象，用于存储牌照后的图片
        File outputImage = new File(getImgFileDir(), fileName);
        try {
            // 如果存在则删除
            if (outputImage.exists()) {
                outputImage.delete();
            }
            // 创建一个新的文件
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputImage;
    }


}
