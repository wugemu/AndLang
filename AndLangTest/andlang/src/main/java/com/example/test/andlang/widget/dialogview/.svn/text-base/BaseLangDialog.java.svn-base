package com.example.test.andlang.widget.dialogview;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.test.andlang.R;
import com.example.test.andlang.R2;
import com.example.test.andlang.util.BaseLangUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by root on 18-4-9.
 */

public class BaseLangDialog {
    @BindView(R2.id.lang_dialog_text)
    TextView tvTitle;

    private Context context;
    private Dialog overdialog;
    private BaseLangDialogInterface oki;
    private String title;
    public BaseLangDialog(Context context, String title,BaseLangDialogInterface oki){
        this.context = context;
        this.oki = oki;
        this.title = title;
        initView();
    }
    private void initView() {
        View view = View.inflate(context, R.layout.lang_common_dialog, null);
        ButterKnife.bind(this, view);
        overdialog = new Dialog(context, R.style.dialog_lhp);
        if (BaseLangUtil.isEmpty(title)) {
            title = "是否确认？";
        }

        tvTitle.setText(title);
        overdialog.setContentView(view);
        overdialog.show();
        Window win = overdialog.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);

    }
    @OnClick(R2.id.dialog_ok_btn)
    public void ok() {
        if (oki != null) {
            oki.executeOk();
        }
        cancelDialog();
    }

    @OnClick(R2.id.dialog_cancel_btn)
    public void cancel() {
        cancelDialog();

    }

    public void cancelDialog() {
        if (overdialog != null) {
            overdialog.dismiss();
        }
    }
}
