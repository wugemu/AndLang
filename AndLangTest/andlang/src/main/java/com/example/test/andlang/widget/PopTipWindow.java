package com.example.test.andlang.widget;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.test.andlang.R;
import com.example.test.andlang.R2;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopTipWindow extends PopupWindow {

    private View contentView;
    private Activity activity;

    private String copyStr;

    public PopTipWindow(Activity activity,String copyStr){
        this.activity = activity;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.lang_common_pop, null);
        ButterKnife.bind(this, contentView);
        this.setContentView(contentView);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.copyStr=copyStr;
    }
    public void showWindow(View parent) {
        if(parent==null){
            return;
        }
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        parent.getLocationOnScreen(anchorLoc);
        final int anchorHeight = parent.getHeight();

        int popWidth=(int)activity.getResources().getDimension(R.dimen.lang_pop_width);
        int popHeight=(int)activity.getResources().getDimension(R.dimen.lang_pop_height);
        this.setWidth(popWidth);
        this.setHeight(popHeight);

        if (!this.isShowing()) {
            showAtLocation(parent, Gravity.TOP, 0, anchorLoc[1] - popHeight);
        } else {
            this.dismiss();
        }
    }

    @OnClick(R2.id.rl_copy)
    public void goCopy(){
        if(!BaseLangUtil.isEmpty(copyStr)){
            BaseLangUtil.copy(copyStr, activity);
            ToastUtil.show(activity, "已经复制到剪切板");
        }
        this.dismiss();
    }

}
