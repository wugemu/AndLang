package com.example.test.andlang.andlangutil;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public BaseLangViewHolder setText(int viewId, String text)
    {
        TextView view = getView(viewId);
        if(text == null) {
            text = "";
        }
        view.setText(text);
        return this;
    }
    /**
     * 为TextView设置字体颜色
     *
     * @param viewId
     * @param color
     * @return
     */
    public BaseLangViewHolder setTextColor(int viewId, int color)
    {
        TextView view = getView(viewId);
        view.setTextColor(color);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public BaseLangViewHolder setImageResource(int viewId, int drawableId)
    {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }
}
