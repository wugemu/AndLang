package com.example.test.andlang.andlangutil;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by root on 18-3-8.
 */

public abstract class BaseLangAdapter<T> extends BaseAdapter{
    private LayoutInflater inflater;
    public Activity context;
    private int layoutId;
    private List<T> data;
    public BaseLangAdapter(Activity context, int layoutId){
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.layoutId=layoutId;
    }
    public void setData(List<T> data){
        this.data=data;
    }
    @Override
    public int getCount() {
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
}
