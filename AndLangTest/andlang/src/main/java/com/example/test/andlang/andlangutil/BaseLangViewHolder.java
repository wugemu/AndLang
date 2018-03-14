package com.example.test.andlang.andlangutil;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by root on 18-3-2.
 */

public class BaseLangViewHolder {
    private final SparseArray<View> mViews;
    private View mConvertView;
    private BaseLangViewHolder(Context context, int position , int layoutId){
        this.mViews=new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, null);
        mConvertView.setTag(this);

    }
    public static BaseLangViewHolder getViewHolder(Context context, int position, View convertView, ViewGroup parent, int layoutId){
        if(convertView==null){
            return new BaseLangViewHolder(context,position,layoutId);
        }
        return (BaseLangViewHolder)convertView.getTag();
    }
    public View getConvertView(){
        return mConvertView;
    }
    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}
