package com.example.test.andlang.andlangutil;

import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test.andlang.util.imageload.ImageLoadUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Created by root on 18-3-7.
 */

public  class BaseLangViewModel extends Observable{


    public void notifyData(Object tag){
        setChanged();    //改变通知者的状态
        notifyObservers(tag);
    }
}
