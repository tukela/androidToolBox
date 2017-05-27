package com.honglei.toolbox.utils.ok;

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
 * <ul>
 * <li>{@link #getScreenWidth(Context context)  ｝</li>取屏幕宽度
 * <li>{@link #getScreenHeight(Context context)  ｝</li>
 * <li>{@link #getScreenHeightWithStatusBar(Context context)  ｝</li>取屏幕高度包含状态栏高度
 * <li>{@link #getNavigationBarHeight(Context context)  ｝</li>导航栏高度
 * <li>{@link #getStatusBarHeight(Context context) ｝</li>
 * <li>{@link #getActionBarHeight(Context context)｝</li>
 * <li>{@link #dip2px(Context context, float dpValue)  ｝</li>根据手机的分辨率从 dip 的单位 转成为 px(像素)
 * <li>{@link #dp2px(Context context, float dpVal) ｝</li>
 * <li>{@link #px2dip(Context context, float pxValue)  ｝</li>
 * <li>{@link #px2dp(Context context, float pxVal)   ｝</li>
 * <li>{@link #px2sp(Context context, float pxValue)  ｝</li>
 * <li>{@link #sp2px(Context context, float spValue)  ｝</li>
 * <li>{@link #isBackground(Context context)  ｝</li>判断应用是否处于后台状态
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * </ul>
 ****/

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
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * dp转px
     */
    public static int dp2px(Context context, float dpVal) {
        int result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources()
                        .getDisplayMetrics());
        //LogUtils.i("dp-->px：" + result);
        return result;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics()
                .scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics()
                .scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
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

}
