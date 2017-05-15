package com.honglei.toolbox.utils.test;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by ad2040 on 2017/1/5.
 */

public class CommonUtils {
    /**
     * 使用浏览器打开指定url地址
     *
     * @param context
     * @param url
     */
    public static void openUrl(Context context, String url) {
        if (TextUtils.isEmpty(url)) {
            ToastUtil.showShortToast("网址为空");
            return;
        }
        Uri uri;
        if (url.startsWith("http://") || url.startsWith("https://")) {
            uri = Uri.parse(url);
        } else {
            uri = Uri.parse("http://" + url);
        }
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(it);
    }

    /**
     * 拨打电话
     */
    public static void callPhone(Context context, String tel) {
        if (!TextUtils.isEmpty(tel)) {
            ToastUtil.showShortToast("号码为空");
        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + tel);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 隐藏软键盘
     * warrning : view 必须是EditText或其子类
     * @param context
     */
    public static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != imm)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示软键盘
     * imm.isActive() 返回true表示输入法打开
     * @param context
     */
    public static void showSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != imm && !imm.isActive()) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }

}
