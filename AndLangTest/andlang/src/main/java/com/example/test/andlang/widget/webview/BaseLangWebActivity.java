package com.example.test.andlang.widget.webview;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.test.andlang.R;
import com.example.test.andlang.R2;
import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangPresenter;
import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.ToastUtil;
import com.example.test.andlang.util.VersionUtil;

import java.util.HashMap;
import java.util.Observable;

import butterknife.BindView;

/**
 * Created by root on 18-4-2.
 */

public abstract class BaseLangWebActivity extends BaseLangActivity<BaseLangPresenter> {
    @BindView(R2.id.baselang_webview)
    WebView baselang_webview;

    private Class childClass;
    @Override
    public int getLayoutId() {
        return R.layout.activity_baselang_web;
    }

    @Override
    public void initView() {
        initLoading();
        initWebview();
        childClass=getClass();
    }

    public void loadUrl(String url){
        if (!BaseLangUtil.isEmpty(url)) {
            if (url.contains("?")) {
                url += "&versionAndr=" + VersionUtil.getVersionCode(this);
            } else {
                url += "?versionAndr=" + VersionUtil.getVersionCode(this);
            }
            baselang_webview.loadUrl(url);
        }
        showWaitDialog();
    }
    private void initWebview(){
        baselang_webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        baselang_webview.setBackgroundColor(0);
        baselang_webview.setVerticalScrollBarEnabled(false); // 垂直不显示
        WebSettings setting = baselang_webview.getSettings();
        baselang_webview.addJavascriptInterface(new myJavaScriptInterface(), "android");
        setting.setJavaScriptEnabled(true);
        setting.setDefaultTextEncodingName("UTF-8");
        setting.setBuiltInZoomControls(false);
        setting.setSupportZoom(false);
        setting.setTextZoom(100);
        setting.setDisplayZoomControls(false);
        setting.supportMultipleWindows();
        setting.setDatabaseEnabled(true);
        setting.setAppCacheMaxSize(1024 * 1024 * 8);
        setting.setAllowFileAccess(true);
        setting.setAppCacheEnabled(true);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            //http https 混合模式加载
            setting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 设置缓存
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE); // 设置
        baselang_webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                dismissWaitDialog();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                Intent intent = new Intent(BaseLangWebActivity.this, childClass);
                intent.putExtra("url", url);
                ActivityUtil.getInstance().start(BaseLangWebActivity.this, intent);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub
                dismissWaitDialog();
            }
        });
    }
    // webview与安卓系统交互接口
    final class myJavaScriptInterface {
        public myJavaScriptInterface() {
            // TODO Auto-generated constructor stub
        }

        // 404返回首页
        @JavascriptInterface
        public void showToast() {
            ToastUtil.show(BaseLangWebActivity.this,"webview");
        }
    }
}
