package com.honglei.toolbox.utils.test;

import android.widget.Toast;

import cn.adinnet.jjshipping.MyApplication;


/**
 * Created by lxj on 2016/5/23.
 */
public class ToastUtil {
    private static Toast toast;

    private static boolean isDebug = true;

    /**
     * 显示短时间toast
     * @param text
     */
    public static void showShortToast(String text) {
        if (toast == null) {
            //创建toast
            toast = Toast.makeText(MyApplication.mContext, text, Toast.LENGTH_SHORT);
        }
        //如果吐司已经创建，那么直接更改吐司的文本即可
        toast.setText(text);
        //最后显示
        toast.show();
    }

    /**
     * 显示短时间toast
     * @param text
     */
    public static void showShortToastDebug(String text) {
        if (isDebug) {
            if (toast == null) {
                //创建toast
                toast = Toast.makeText(MyApplication.mContext, text, Toast.LENGTH_SHORT);
            }
            //如果吐司已经创建，那么直接更改吐司的文本即可
            toast.setText(text);
            //最后显示
            toast.show();
        }
    }

    /**
     * 显示长时间toast
     * @param text
     */
    public static void showLongToast(String text) {
        if (toast == null) {
            //创建toast
            toast = Toast.makeText(MyApplication.mContext, text, Toast.LENGTH_LONG);
        }
        //如果吐司已经创建，那么直接更改吐司的文本即可
        toast.setText(text);
        //最后显示
        toast.show();
    }


    /**
     * 显示自定义长度的toast
     * @param text
     * @param duration
     */
    public static void showToast(String text, int duration) {
        if (toast == null) {
            //创建toast
            toast = Toast.makeText(MyApplication.mContext, text, duration);
        }
        //如果吐司已经创建，那么直接更改吐司的文本即可
        toast.setText(text);
        //最后显示
        toast.show();
    }
}
