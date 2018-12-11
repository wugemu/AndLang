package com.example.test.andlang.andlangutil;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.test.andlang.util.imageload.ImageLoadUtils;

import java.util.List;

/**
 * Created by root on 18-3-8.
 */

public abstract class BaseLangAdapter<T> extends BaseAdapter{
    private LayoutInflater inflater;
    public Activity context;
    private int layoutId;
    public List<T> data;
    public BaseLangAdapter(Activity context, int layoutId,List<T> data){
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.layoutId=layoutId;
        setData(data);
    }
    public void setData(List<T> data){
        this.data=data;
    }
    @Override
    public int getCount() {
        if(data==null){
            return 0;
        }
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public abstract void convert(BaseLangViewHolder helper, int postion, T item);
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final BaseLangViewHolder holder = BaseLangViewHolder.getViewHolder(context,position,convertView,parent,layoutId);
        convert(holder,position,getItem(position));
        return holder.getConvertView();
    }
    public void doLoadImage(ImageView imageView,String url){
        ImageLoadUtils.doLoadImageUrl(imageView,url);
    }
}
