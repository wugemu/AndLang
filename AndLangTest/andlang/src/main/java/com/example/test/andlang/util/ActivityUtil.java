package com.example.test.andlang.util;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.example.test.andlang.R;
import com.example.test.andlang.andlangutil.BaseLangActivity;

import java.util.Stack;


/**
 * Created by Bill56 on 2017/9/5.
 */

public class ActivityUtil {

    /**
     * 构造方法私有化
     */
    private ActivityUtil() {

    }

    public static ActivityUtil getInstance() {
        return ActivityHolder.sInstance;
    }

    /**
     * 静态内部类
     */
    private static class ActivityHolder {
        private static final ActivityUtil sInstance = new ActivityUtil();
    }
    private Stack<Activity> activityStack;// activity栈
//    private List<BaseActivity> activityList;

    // 把一个activity压入栈中
    public void pushOneActivity(Activity actvity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(actvity);

        Log.d("ActivityManager ", "size = " + activityStack.size());
    }

// 获取栈顶的activity，先进后出原则
public Activity getLastActivity() {
    return activityStack.lastElement();
}
    // 退出所有activity
    public void finishAllActivity() {
        if (activityStack != null) {
            while (activityStack.size() > 0) {
                Activity activity = getLastActivity();
                if (activity == null)
                    break;
                popOneActivity(activity);
            }

        }
    }
    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls){
        Activity act=null;
        for (Activity activity : activityStack) {
            if(activity.getClass().equals(cls) ){
                if (activity != null) {
                    activity.finish();
                    act=activity;
                    activity = null;
                }
            }
        }
        activityStack.remove(act);
    }
    // 移除一个activity
    public void popOneActivity(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null) {
                activity.finish();
                activityStack.remove(activity);
                activity = null;
            }
        }
    }
    public void exitApplication() {
        for (Activity activity : activityStack) {
            if (!activity.isFinishing()) {
                activityStack.remove(activity);
                activity.finish();
            }
        }
        System.exit(0);
    }

    public int getActivitySize() {
        return activityStack.size();
    }

    public void start(Activity activity, Intent i) {
        activity.startActivity(i);
        activity.overridePendingTransition(R.anim.in_from_right,
                R.anim.out_to_left);
    }

    public  void start2(Activity activity, Intent i) {
        activity.startActivity(i);
        activity.overridePendingTransition(R.anim.alpha_in,
                R.anim.alpha_out);
    }

    public void startResult(Activity activity, Intent i, int requestCode) {
        activity.startActivityForResult(i, requestCode);
        activity.overridePendingTransition(R.anim.in_from_right,
                R.anim.out_to_left);
    }

    public void exit(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(),
                    0);
        }
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    public void exitResult(Activity activity, Intent i, int resultCode) {
        activity.setResult(resultCode, i);
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

}
