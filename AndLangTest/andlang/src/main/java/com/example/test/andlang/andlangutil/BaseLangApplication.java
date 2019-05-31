package com.example.test.andlang.andlangutil;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.test.andlang.BuildConfig;
import com.example.test.andlang.R;
import com.example.test.andlang.cockroach.CrashLog;
import com.example.test.andlang.cockroach.DebugSafeModeTipActivity;
import com.example.test.andlang.log.AppCrashHandler;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.LogUtil;
import com.example.test.andlang.util.PreferencesUtil;
import com.example.test.andlang.util.ToastUtil;
import com.example.test.andlang.util.VersionUtil;
import com.example.test.andlang.util.imageload.*;
import com.example.test.andlang.util.imageload.IInnerImageSetter;
import com.example.test.andlang.widget.dialogview.BaseLangDialog;
import com.example.test.andlang.widget.dialogview.BaseLangDialogInterface;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;
import com.wanjian.cockroach.Cockroach;
import com.wanjian.cockroach.ExceptionHandler;
//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;

import java.io.File;

/**
 * Created by root on 18-3-27.
 */

public class BaseLangApplication extends Application {
    public static final DisplayImageOptions BOUTIQUE_OPTIPON = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.image_def) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.image_def) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.image_def) // 设置图片加载或解码过程中发生错误显示的图片
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
            .cacheInMemory(true)//设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageO

    public static String tmpImageDir = "sudian";
    public static long NOW_TIME; // 当前服务器时间
    public static String logDir;
    private static BaseLangApplication mApp;
    private PreferencesUtil mSpUtil;
//    private RefWatcher refWatcher;//内存检测
    @Override
    public void onCreate() {
        super.onCreate();
        initData();
//        if(LogUtil.isCash) {
//            refWatcher = setupLeakCanary();
//        }
    }
//    private RefWatcher setupLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return RefWatcher.DISABLED;
//        }
//        return LeakCanary.install(this);
//    }

//    public static RefWatcher getRefWatcher(Context context) {
//        BaseLangApplication leakApplication = (BaseLangApplication) context.getApplicationContext();
//        return leakApplication.refWatcher;
//    }
    private void initData() {
        mApp = this;
        //程序错误日志信息收集
        logDir = Environment.getExternalStorageDirectory().getPath() + "/sudian/crash/";
        AppCrashHandler crashHandler = AppCrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        Logger.addLogAdapter(new AndroidLogAdapter());
        initImageLoad();

        LogUtil.e("0.0","版本号：V"+VersionUtil.getVersionName(BaseLangApplication.this));
        installCockroach();//最大化防止应用crash  需要在清单中注册DebugSafeModeTipActivity
    }

    private void installCockroach() {
        final Thread.UncaughtExceptionHandler sysExcepHandler = Thread.getDefaultUncaughtExceptionHandler();
        Cockroach.install(this, new ExceptionHandler() {
            @Override
            protected void onUncaughtExceptionHappened(Thread thread, Throwable throwable) {
                LogUtil.e("--->onUncaughtExceptionHappened:" + thread + "<---");
                if (BaseLangUtil.isApkInDebug()) {
                    //crash日志记录
                    CrashLog.saveCrashLog(getApplicationContext(), throwable);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.show(BaseLangApplication.this,"捕获到导致崩溃的异常");
                        }
                    });
                }else {
                    //上传友盟日志
                    if(throwable!=null) {
                        String errorInfo=CrashLog.getCrashLog(getApplicationContext(), throwable);
                        if(!BaseLangUtil.isEmpty(errorInfo)) {
                            MobclickAgent.reportError(BaseLangApplication.this,errorInfo);
                        }
                    }
                }
            }

            @Override
            protected void onBandageExceptionHappened(Throwable throwable) {
                throwable.printStackTrace();//打印警告级别log，该throwable可能是最开始的bug导致的，无需关心
                LogUtil.e("Cockroach Worked");
            }

            @Override
            protected void onEnterSafeMode() {
                LogUtil.e("已经进入安全模式");
                if (BaseLangUtil.isApkInDebug()) {
                    Intent intent = new Intent(BaseLangApplication.this, DebugSafeModeTipActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    MobclickAgent.onEvent(BaseLangApplication.this,"app_salf","进入APP安全模式V"+ VersionUtil.getVersionName(BaseLangApplication.this));
                }
            }

            @Override
            protected void onMayBeBlackScreen(Throwable e) {
                Thread thread = Looper.getMainLooper().getThread();
                LogUtil.e("--->onUncaughtExceptionHappened:" + thread + "<---");
                //黑屏时建议直接杀死app
                sysExcepHandler.uncaughtException(thread, new RuntimeException("black screen"));
            }

        });

    }

    private void initImageLoad() {
        //配置uil工具
        File cacheDir = StorageUtils.getOwnCacheDirectory(
                getApplicationContext(), tmpImageDir);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800)          // default = device screen dimensions
                //  .discCacheExtraOptions(480, 800, Bitmap.CompressFormat.JPEG, 75)
                .threadPriority(Thread.NORM_PRIORITY - 1)   // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .threadPoolSize(3)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13)              // default
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .diskCacheSize(50 * 1024 * 1024)        // 缓冲大小
                .diskCacheFileCount(100)                // 缓冲文件数目
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();

        //  2.单例ImageLoader类的初始化
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

        ImageLoadUtils.setImageSetter(new IInnerImageSetter() {
            @Override
            public <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view, @Nullable String url) {
                if (!BaseLangUtil.isEmpty(url)) {
                    if (url.toLowerCase().contains(".gif")) {
                        GlideUtil.getInstance().displayGif(getApplicationContext(), url, view);
                    } else {
                        GlideUtil.getInstance().display(getApplicationContext(), url, view);
                    }
                }
            }

            @Override
            public <IMAGE extends ImageView> void doLoadImageUrlCenterCrop(@NonNull IMAGE view, @Nullable String url) {
                if (!BaseLangUtil.isEmpty(url)) {
                    if (url.toLowerCase().contains(".gif")) {
                        GlideUtil.getInstance().displayGifCenterCrop(getApplicationContext(), url, view);
                    } else {
                        GlideUtil.getInstance().displayCenterCrop(getApplicationContext(), url, view);
                    }
                }
            }

            @Override
            public <IMAGE extends ImageView> void doLoadImageUrlFitCenter(@NonNull IMAGE view, @Nullable String url) {
                if (!BaseLangUtil.isEmpty(url)) {
                    if (url.toLowerCase().contains(".gif")) {
                        GlideUtil.getInstance().displayGifFitCenter(getApplicationContext(), url, view);
                    } else {
                        GlideUtil.getInstance().displayFitCenter(getApplicationContext(), url, view);
                    }
                }
            }

            @Override
            public <IMAGE extends ImageView> void doLoadCircleImageUrl(@NonNull IMAGE view, @Nullable String url) {
                if (!BaseLangUtil.isEmpty(url)) {
                    if (url.toLowerCase().contains(".gif")) {
                        GlideUtil.getInstance().displayGif(getApplicationContext(), url, view);
                    } else {
                        GlideUtil.getInstance().displayHead(getApplicationContext(), url, view);
                    }
                }
            }

            @Override
            public <IMAGE extends ImageView> void doLoadImageRound(@NonNull IMAGE view, @Nullable String url, float round) {
                if (!BaseLangUtil.isEmpty(url)) {
                    if (url.toLowerCase().contains(".gif")) {
                        GlideUtil.getInstance().displayGif(getApplicationContext(), url, view);
                    } else {
                        GlideUtil.getInstance().displayRoundImg(getApplicationContext(), url, view,round);
                    }
                }
            }

            @Override
            public <IMAGE extends ImageView> void doLoadByImageLoader(@NonNull IMAGE view, @Nullable String url) {
                //测试比较加载速度使用
                ImageLoader.getInstance().displayImage(url,view, BOUTIQUE_OPTIPON);
            }

            @Override
            public <IMAGE extends ImageView> void doLoadImageRes(@NonNull IMAGE view, @Nullable int resId) {
                GlideUtil.getInstance().displayLocRes(getApplicationContext(), resId, view);
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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    /**imei*/
    public String getIMEI(){
        String imei=BaseLangApplication.getInstance().getSpUtil().getString(this,"imei");
        if(BaseLangUtil.isEmpty(imei)){
            try {
                TelephonyManager mTelephonyMgr=(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
                imei=mTelephonyMgr.getDeviceId();
                BaseLangApplication.getInstance().getSpUtil().putString(this,"imei",imei);
            } catch (SecurityException e) {
                imei= Settings.Secure.getString(
                        this.getContentResolver(), Settings.Secure.ANDROID_ID);
                BaseLangApplication.getInstance().getSpUtil().putString(this,"imei",imei);
            }
        }
        return imei;
    }

    public boolean checkApkExist(String packageName){
        if (TextUtils.isEmpty(packageName))
            return false;
        try {
            ApplicationInfo info = getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
