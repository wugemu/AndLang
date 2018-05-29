package com.example.test.andlangtest.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangAdapter;
import com.example.test.andlang.andlangutil.BaseLangViewHolder;
import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.PermissionsCheckerUtil;
import com.example.test.andlang.util.ToastUtil;
import com.example.test.andlang.widget.dialogview.BaseLangDialog;
import com.example.test.andlang.widget.dialogview.BaseLangDialogInterface;
import com.example.test.andlang.widget.dialogview.BaseLangSelectPicDialog;
import com.example.test.andlang.widget.dialogview.BaseLangSelectPicInterface;
import com.example.test.andlangtest.Presenter.MainPresenter;
import com.example.test.andlangtest.ViewModel.MainViewModel;
import com.example.test.andlangtest.R;

import java.util.Observable;

import butterknife.BindView;

public class MainActivity extends BaseLangActivity<MainPresenter> implements BlankFragment.BlackFragemtInterface{

    @BindView(R.id.tv_hello)
    TextView ywtDiscount;
    @BindView(R.id.tv_hello2)
    TextView tv_hello2;
    @BindView(R.id.tv_hello3)
    TextView tv_hello3;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.lv_hellow)
    ListView lv_hellow;

    private BaseLangAdapter adapter;
    private BlankFragment blankFragment;
    public FragmentManager fManager;

    // 所需的全部权限
    private  String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        //设置 TextView EditText ListView 之外的View
        initTitleBar(false,"标题");
        initLoading();
        fManager = getSupportFragmentManager();
        if (blankFragment == null) {
            blankFragment = new BlankFragment();
            fManager.beginTransaction().add(R.id.fl_layout, blankFragment, "BlankFragment")
                    .commitAllowingStateLoss();
        }
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_hello2.setText("点击按钮");
                presenter.addListValue(tv_hello3.getText().toString());
                Intent intent=new Intent(MainActivity.this,WebActivity.class);
                intent.putExtra("url","http://www.weinihaigou.com/m-html/index/index.html?title=妮素供应链");
                ActivityUtil.getInstance().start(MainActivity.this,intent);
            }
        });
        lv_hellow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_hello3.setText(presenter.getListValue().get(position));
            }
        });
//        BaseLangDialog dialog=new BaseLangDialog(MainActivity.this, "测试", new BaseLangDialogInterface() {
//            @Override
//            public void executeOk() {
//                ToastUtil.show(MainActivity.this,"测试");
//            }
//        });
        BaseLangSelectPicDialog selectPicDialog=new BaseLangSelectPicDialog(MainActivity.this, new BaseLangSelectPicInterface() {
            @Override
            public void executeBtn1() {

            }

            @Override
            public void executeBtn2() {

            }
        });
    }

    @Override
    public void initPresenter() {
        //绑定extView EditText ListView 到 model
        presenter=new MainPresenter(this,MainViewModel.class);
    }

    @Override
    public void initData() {
        if (PermissionsCheckerUtil.lacksPermissions(MainActivity.this,PERMISSIONS)) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS,
                    200);
        }
    }

    @Override
    public void setData(String value){
        //Fragment interface
    }

    @Override
    public void update(Observable o, Object arg) {
        MainViewModel model=(MainViewModel)presenter.model;
        if((int)arg==1){
            ywtDiscount.setText(model.getMessage());
            tv_hello2.setText(model.getTv_hello2());
            adapter=new BaseLangAdapter<String>(this, R.layout.listview_main_item, model.getLv_hellow()) {
                @Override
                public void convert(BaseLangViewHolder helper, int postion, String item) {
                    TextView tv_item_hello=(TextView) helper.getView(R.id.tv_item_hello);
                    tv_item_hello.setText(item);
                }
            };
            lv_hellow.setAdapter(adapter);
        }else if((int)arg==2){
            if(adapter!=null){
                adapter.notifyDataSetChanged();
            }
            ToastUtil.show(MainActivity.this,"asdfsfs");
        }
    }

    //用户选择允许或拒绝后，会回调onRequestPermissionsResult方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                ToastUtil.show(MainActivity.this, "权限open");
            } else {
                // Permission Denied
                ToastUtil.show(MainActivity.this, "请开启权限");
            }
        }
    }
}
