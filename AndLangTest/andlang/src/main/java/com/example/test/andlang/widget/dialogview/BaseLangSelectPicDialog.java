package com.example.test.andlang.widget.dialogview;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

import com.example.test.andlang.R;
import com.example.test.andlang.R2;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by root on 18-5-29.
 */

public class BaseLangSelectPicDialog {
    private Context context;
    private Dialog overdialog;
    private BaseLangSelectPicInterface oki;
    public BaseLangSelectPicDialog(Context context, BaseLangSelectPicInterface oki){
        this.context = context;
        this.oki = oki;
        initView();
    }
    private void initView() {
        View view = View.inflate(context, R.layout.lang_selectpic_dialog, null);
        ButterKnife.bind(this, view);
        overdialog = new Dialog(context, R.style.dialog_lhp);
        overdialog.setContentView(view);
        overdialog.show();
        Window win = overdialog.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);

    }
    @OnClick(R2.id.dialog_xiangce_btn)
    public void ok1() {
        if (oki != null) {
            oki.executeBtn1();
        }
        cancelDialog();
    }
    @OnClick(R2.id.dialog_paizhao_btn)
    public void ok2(){
        if (oki != null) {
            oki.executeBtn2();
        }
        cancelDialog();
    }

    @OnClick(R2.id.selectpic_cancel_btn)
    public void cancel() {
        cancelDialog();
    }

    public void cancelDialog() {
        if (overdialog != null) {
            overdialog.dismiss();
        }
    }
}
