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
    public static boolean openAnim=false;
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

    //退出顶部activity
    public void  exitTopActivity(){
        //退出顶部Activity
        Activity activity=getLastActivity();
        if(activity!=null){
            popOneActivity(activity);
        }
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
    //判断某个activity是否在栈顶
    public boolean isTopActivity(Class activityClass){
        if(activityStack!=null){
            Activity activity=getLastActivity();
            if(activity.getClass() == activityClass){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    // 移除一个activity
    public void popOneActivity(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null) {
                exit(activity);
                removeActivity(activity);
            }
        }
    }
    public void removeActivity(Activity activity){
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null) {
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
        if(openAnim) {
            start(activity, i, R.anim.in_from_right, R.anim.out_to_left);
        }else {
            activity.startActivity(i);
        }
    }

    public void start(Activity activity, Intent i,int startAnim,int endAnim) {
        activity.startActivity(i);
        activity.overridePendingTransition(startAnim,
                endAnim);
    }

    public void startResult(Activity activity, Intent i, int requestCode) {
        activity.startActivityForResult(i, requestCode);
        if(openAnim) {
            activity.overridePendingTransition(R.anim.in_from_right,
                    R.anim.out_to_left);
        }
    }

    public void exit(Activity activity) {
        exit(activity,R.anim.push_right_in,R.anim.push_right_out);
    }
    public void exit(Activity activity,int startAnim,int endAnim) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(),
                    0);
        }
        activity.finish();
        if(openAnim) {
            activity.overridePendingTransition(startAnim,
                    endAnim);
        }
    }


    public void exitResult(Activity activity, Intent i, int resultCode) {
        activity.setResult(resultCode, i);
        activity.finish();
        if(openAnim) {
            activity.overridePendingTransition(R.anim.push_right_in,
                    R.anim.push_right_out);
        }
    }

    // 退出before所有activity
    public void  finishBeforActivity(){
        if (activityStack != null) {
            while (activityStack.size() > 1) {
                Activity activity = activityStack.get(0);
                LogUtil.d("kill "+activity.getClass().getSimpleName());
                popOneActivity(activity);
            }
        }
    }

    //退出到某个Activity
    public void exitToActivity(Activity act,Class activityClass){
        if (activityStack != null) {
            while (activityStack.size() > 0) {
                Activity activity=getLastActivity();
                if (activity.getClass()!=activityClass) {
                    activityStack.remove(activity);
                    activity.finish();
                }else {
                    break;
                }
            }
            if(activityStack.size()==0){
                //栈中不存在 重新创建此Activity
                Intent intent=new Intent(act,activityClass);
                start(act,intent);
            }
        }
    }

    //杀死某个acitivty
    public void exitActivity(Class activityClass){
        if (activityStack != null) {
            for (Activity activity : activityStack) {
                if(activity.getClass()==activityClass ){
                    if (activity != null) {
                        activityStack.remove(activity);
                        activity.finish();
                        break;
                    }
                }
            }
        }
    }

    //杀死所有的这种类型acitivty
    public void exitTypeActivityFromTop(Class activityClass){
        if (activityStack != null) {
            while (activityStack.size() > 0) {
                Activity activity=getLastActivity();
                if (activity.getClass()==activityClass) {
                    activityStack.remove(activity);
                    activity.finish();
                }else {
                    break;
                }
            }
        }
    }

    public boolean isFirstActivity(){
        if (activityStack != null && activityStack.size()>1) {
            return false;
        }else {
            return true;
        }
    }
}
