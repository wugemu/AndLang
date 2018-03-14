package com.example.test.andlang.andlangutil;

import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 18-3-7.
 */

public abstract class BaseLangViewModel<T extends BaseLangViewModel> {
    private Map<String,View> bindmap=new HashMap<String,View>();
    private Object obj;

    //设置字段值同时绑定View的值
    public void setValue(String key, Object value) {
        if(obj==null){
            try {
                obj = this;
            }catch (Exception e){
                Log.e("0.0",e.getMessage());
            }
        }
        try {
            //获取指定名称的参数字段
            Field keyfield = getClass().getDeclaredField(key);
            //设置不检测字段权限类型，设置可访问
            keyfield.setAccessible(true);
            //给参数字段赋值
            keyfield.set(obj, value);
        }catch (Exception e){
            Log.e("0.0",e.getMessage());
        }
        bindView(key);
    }

    //通过字段key获取值
    public Object getValueFromKey(String key) {
        Object value=null;
        if(obj==null){
            try {
                obj = this;
            }catch (Exception e){
                Log.e("0.0",e.getMessage());
            }
        }
        try {
            //获取指定名称的参数字段
            Field keyfield = getClass().getDeclaredField(key);
            //设置不检测字段权限类型，设置可访问
            keyfield.setAccessible(true);
            //给参数字段赋值
            value=keyfield.get(obj);
        }catch (Exception e){
            Log.e("0.0",e.getMessage());
        }
        return value;
    }

    //绑定View的值
    public void bindView(String key) {
        View view=bindmap.get(key);
        if(view==null){
            return;
        }
        if(view instanceof TextView){
            if(getValueFromKey(key) instanceof String){
                ((TextView) view).setText((String)getValueFromKey(key));
            }
        }else if(view instanceof EditText){
            if(getValueFromKey(key) instanceof String){
                ((TextView) view).setText((String)getValueFromKey(key));
            }
        }else if(view instanceof ImageView){
            if(getValueFromKey(key) instanceof String){
                ImageUtils.doLoadImageUrl((ImageView) view,(String)getValueFromKey(key));
            }
        }
    }

    //获取View的值
    public String getValueFromView(String key) {
        String value="";
        View view=bindmap.get(key);
        if(view==null){
            return "";
        }
        if(view instanceof TextView){
            value= ((TextView) view).getText().toString();
        }else if(view instanceof EditText){
            value= ((TextView) view).getText().toString();
        }

        if(obj==null){
            try {
                obj = this;
            }catch (Exception e){
                Log.e("0.0",e.getMessage());
            }
        }
        try {
            //获取指定名称的参数字段
            Field keyfield = getClass().getDeclaredField(key);
            //设置不检测字段权限类型，设置可访问
            keyfield.setAccessible(true);
            //给参数字段赋值
            keyfield.set(obj, value);
        }catch (Exception e){
            Log.e("0.0",e.getMessage());
        }

        return value;
    }

    //注册view绑定model
    public void registView(String key,View view) { //注册View到Model
        bindmap.put(key,view);
    }

    //注册Activity的View到Model
    public void registView(BaseLangActivity activity) {
        try {
            //获取所有的参数字段
            Field[] fields = activity.getClass().getDeclaredFields();
            for (Field field : fields){
                field.setAccessible(true);
                if(isSupportView(field.getType())){
                    //判断是否属于View 参数字段
                    registView(field.getName(),(View) field.get(activity));
                }
            }
        }catch (Exception e){
            Log.e("0.0",e.getMessage());
        }
    }
    //注册Fragment的View到Model
    public void registView(BaseLangFragment fragment) {
        try {
            //获取所有的参数字段
            Field[] fields = fragment.getClass().getDeclaredFields();
            for (Field field : fields){
                field.setAccessible(true);
                if(isSupportView(field.getType())){
                    //判断是否属于View 参数字段
                    registView(field.getName(),(View) field.get(fragment));
                }
            }
        }catch (Exception e){
            Log.e("0.0",e.getMessage());
        }
    }

    //设置List类型字段值同时绑定ListView的值
    public void setListValue(String key, Object value,BaseLangAdapter adapter) {
        if(obj==null){
            try {
                obj = this;
            }catch (Exception e){
                Log.e("0.0",e.getMessage());
            }
        }
        try {
            //获取指定名称的参数字段
            Field keyfield = getClass().getDeclaredField(key);
            //设置不检测字段权限类型，设置可访问
            keyfield.setAccessible(true);
            //给参数字段赋值
            keyfield.set(obj, value);
        }catch (Exception e){
            Log.e("0.0",e.getMessage());
        }
        if(adapter==null){
            return ;
        }
        if(value instanceof List){
            adapter.setData((List)value);
            bindListView(key,adapter);
        }
    }

    //绑定ListView的值
    public void bindListView(String key,BaseLangAdapter adapter){
        if(adapter==null){
            return;
        }
        View view=bindmap.get(key);
        if(view==null){
            return;
        }
        if(view instanceof ListView){
            ((ListView) view).setAdapter(adapter);
        }
    }

    public boolean isSupportView(Class fieldclass){
        boolean issupport=false;
        if(TextView.class.isAssignableFrom(fieldclass)||
                EditText.class.isAssignableFrom(fieldclass)||
                ListView.class.isAssignableFrom(fieldclass)||
                ImageView.class.isAssignableFrom(fieldclass)){
            //判断是否属于View 参数字段
            issupport=true;
        }
        return issupport;
    }

    public void notifyView(){
        if(obj==null){
            try {
                obj = this;
            }catch (Exception e){
                Log.e("0.0",e.getMessage());
            }
        }
        try {
            //获取所有的参数字段
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields){
                field.setAccessible(true);
                if(bindmap.get(field.getName())!=null){
                    View view=bindmap.get(field.getName());
                    if(view instanceof TextView
                            || view instanceof EditText
                            || view instanceof ImageView){
                        bindView(field.getName());
                    }
                }
            }
        }catch (Exception e){
            Log.e("0.0",e.getMessage());
        }
    }

    abstract public void updateModel(T model);
}
