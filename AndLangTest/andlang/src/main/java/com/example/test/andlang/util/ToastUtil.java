/**
 * 
 */
package com.example.test.andlang.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

public class ToastUtil {
	private static Toast mToast;
	private static Context mContext;
	private static Handler handler = new Handler(Looper.getMainLooper()){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case 1:
					show(msg.obj.toString());
					break;
				case 2:
					show(msg.arg1);
					break;

				case 3:
					mToast.cancel();
					break;
				case 4:
					show(msg.obj.toString(),msg.arg2);
					break;
			}
		}
	};
	public static void show(Context context, String info) {
		mContext=context;
		Message msg=new Message();
		msg.what=1;
		msg.obj=info;
		handler.sendMessage(msg);
	}

	public static void show(Context context, String info, int duration) {
		mContext=context;
		Message msg=new Message();
		msg.what=4;
		msg.obj=info;
		msg.arg2=duration;
		handler.sendMessage(msg);
	}
	private static void show(String info){
		if(mToast==null)
			mToast= Toast.makeText(mContext, info, Toast.LENGTH_SHORT);
		mToast.setText(info);
		mToast.show();

	}

	private static void show(String info, int duration){
		if(mToast==null)
			mToast= Toast.makeText(mContext, info,duration);
		mToast.setText(info);
		mToast.show();

	}
	private static void show(int info){
		if(mToast==null)
			mToast= Toast.makeText(mContext, info, Toast.LENGTH_SHORT);
		mToast.setText(info);
		mToast.show();
	}

	public static void show(Context context, int info) {
		mContext=context;
		Message msg=new Message();
		msg.what=2;
		msg.arg1=info;
		handler.sendMessage(msg);
	}

	public static void cancel(){
		if(mToast!=null){
			handler.sendEmptyMessage(3);
		}
	}
//	public static void show(Context context, String message) {
//
//		mHandler.removeCallbacks(r);
//		if (mToast == null){//只有mToast==null时才重新创建，否则只需更改提示文字
//			mToast = new Toast(context);
//			mToast.setDuration(Toast.LENGTH_SHORT);
//			mToast.setText(message);
//		}
//		mHandler.postDelayed(r, 2000);//延迟1秒隐藏toast
//		mToast.show();
//	}
//	private static Handler mHandler = new Handler();
//	private static Runnable r = new Runnable() {
//		public void run() {
//			mToast.cancel();
//			mToast=null;//toast隐藏后，将其置为null
//		}
//	};
}
