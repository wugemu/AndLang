package com.example.test.andlangtest.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlangtest.Presenter.MainPresenter;
import com.example.test.andlangtest.ViewModel.MainViewModel;
import com.example.test.andlangtest.R;

import butterknife.BindView;

public class MainActivity extends BaseLangActivity<MainPresenter> implements BlankFragment.BlackFragemtInterface{

    @BindView(R.id.tv_hello)
    TextView ywtDiscount;
    @BindView(R.id.tv_hello2)
    TextView tv_hello2;
    @BindView(R.id.et_hello)
    EditText et_hello;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.lv_hellow)
    ListView lv_hellow;

    private BlankFragment blankFragment;
    public FragmentManager fManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayoutId() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void bindView() {
        //绑定extView EditText ListView 到 model
        presenter=new MainPresenter(this,MainViewModel.class);

        //设置 TextView EditText ListView 之外的View
        fManager = getSupportFragmentManager();
        if (blankFragment == null) {
            blankFragment = new BlankFragment();
            fManager.beginTransaction().add(R.id.fl_layout, blankFragment, "BlankFragment")
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void bindListener() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_hello2.setText("点击按钮");
                presenter.addListValue(et_hello.getText().toString());
            }
        });
        lv_hellow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et_hello.setText(presenter.getListValue().get(position));
            }
        });
    }

    @Override
    public void setData(String value){

    }

}
