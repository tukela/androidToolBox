package com.honglei.toolbox.utils.ok;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
/*******
 * 屏幕 工具类
 * <ul>
 * <li>{@link #printDisplayInfo(Context context)  ｝</li>显示信息
 * <li>{@link #getScreenWidth(Context context)  ｝</li>获得屏幕宽度
 * <li>{@link #getScreenHeight(Context context)  ｝</li>获得屏幕高度
 * <li>{@link #getStatusHeight(Context context)  ｝</li>获得状态栏的高度
 * <li>{@link #snapShotWithStatusBar(Activity activity)  ｝</li>当前屏幕截图，包含状态栏
 * <li>{@link #snapShotWithoutStatusBar(Activity activity)  ｝</li>当前屏幕截图，不包含状态栏
 * <li>{@link #getScreenPhysicalSize(Activity activity)  ｝</li>获取屏幕尺寸（例如：3.5、4.0、5.0寸屏幕）(貌似不太准)
 * <li>{@link #isTablet(Context context)  ｝</li>一般是7寸以上是平板 ,判断是否是平板（官方用法）
 * <li>{@link #getScreenRatio(Context context)  ｝</li>获取屏幕分辨率
 * <li>{@link #getScreenDensity(Context context)  ｝</li>获取屏幕密度
 * <li>{@link #dp2Px(Context context, float dp)  ｝</li>
 * <li>{@link #px2Dp(Context context, float px)   ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * </ul>
 ****/
public class ScreenUtils {


	/**
	 * 打印 显示信息
	 */
	public static DisplayMetrics printDisplayInfo(Context context) {
		DisplayMetrics dm =  context.getResources().getDisplayMetrics();
//        if (Log.isPrint) {
		StringBuilder sb = new StringBuilder();
		sb.append("_______  显示信息:  ");
		sb.append("\ndensity         :").append(dm.density);
		sb.append("\ndensityDpi      :").append(dm.densityDpi);
		sb.append("\nheightPixels    :").append(dm.heightPixels);
		sb.append("\nwidthPixels     :").append(dm.widthPixels);
		sb.append("\nscaledDensity   :").append(dm.scaledDensity);
		sb.append("\nxdpi            :").append(dm.xdpi);
		sb.append("\nydpi            :").append(dm.ydpi);
//            Log.i(TAG, sb.toString());
//        }
		return dm;
	}

	/**
	 * 获得屏幕宽度
	 * 
	 */
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		int width = outMetrics.widthPixels;
		return width;
	}

	/**
	 * 获得屏幕高度
	 */
	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		int height = outMetrics.heightPixels;
		return height;
	}

	/**
	 * 获得状态栏的高度
	 */
	public static int getStatusHeight(Context context) {
		int statusHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;
	}

	/**
	 * 精确获取屏幕尺寸（例如：3.5、4.0、5.0寸屏幕）(貌似不太准)
	 * 
	 * @param //ctx
	 * @return
	 */
	public static double getScreenPhysicalSize(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2)
				+ Math.pow(dm.heightPixels, 2));
		return diagonalPixels / (160 * dm.density);
	}

	/**
	 * 一般是7寸以上是平板 ,判断是否是平板（官方用法）
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
	
	
	 /**
     * 获取屏幕分辨率
     *
     * @return
     */
    public static String getScreenRatio(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels + "X" + displayMetrics.heightPixels;
    }

    /**
     * 获取屏幕密度
     *
     * @return
     */
    public static String getScreenDensity(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.densityDpi + "DPI";
    }

	public static float dp2Px(Context context, float dp) {
		if (context == null) {
			return -1;
		}
		return dp * context.getResources().getDisplayMetrics().density;
	}

	public static float px2Dp(Context context, float px) {
		if (context == null) {
			return -1;
		}
		return px / context.getResources().getDisplayMetrics().density;
	}



}
