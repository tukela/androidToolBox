package com.honglei.toolbox.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.List;


/**
 * 单位转换 工具类<br>
 */
public class DensityUtils {



    /**
     * 取屏幕宽度
     * @return
     */
    public static int getScreenWidth(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 取屏幕高度
     * @return
     */
    public static int getScreenHeight(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels-getStatusBarHeight(context);
    }

    /**
     * 取屏幕高度包含状态栏高度
     * @return
     */
    public static int getScreenHeightWithStatusBar(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 取导航栏高度
     * @return
     */
    public static int getNavigationBarHeight(Context context){
        int result = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 取状态栏高度
     * @return
     */
    public static int getStatusBarHeight(Context context){
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getActionBarHeight(Context context){
        int actionBarHeight = 0;

        final TypedValue tv = new TypedValue();
        if (context.getTheme()
                .resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(
                    tv.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }





    /**
     * 判断应用是否处于后台状态
     * @return
     */
    public static boolean isBackground(Context context){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }



    /**
     * dp转px
     */
    public static int dp2px(Context context, float dpVal) {
        int result = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources()
                        .getDisplayMetrics());
        //LogUtils.i("dp-->px：" + result);
        return result;
    }

    /**
     * sp转px
     */
    public static int sp2px(Context context, float spVal) {
        int result = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources()
                        .getDisplayMetrics());
        //LogUtils.i("sp-->px：" + result);
        return result;
    }

    /**
     * px转dp
     */
    public static int px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int result = (int) (pxVal / scale);
        //LogUtils.i("px-->dp：" + result);
        return result;
    }

    /**
     * px转sp
     */
    public static float px2sp(Context context, float pxVal) {
        int result = (int) (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
        //LogUtils.i("px-->sp：" + result);
        return result;
    }

}
