package com.honglei.toolbox.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * 字符格式化
 */
public class CharFormat {

    private static CharFormat instance = null;

    public static CharFormat getInstance() {
        if (instance == null) {
            instance = new CharFormat();
        }
        return instance;
    }

    /*
     * 获取当前icon字体
     * @param context
     * @return
     */
    public Typeface getIconTypeface(Context context)
    {
        Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont/iconfont.ttf");
        return iconfont;
    }


    /**
     * 加省略号
     *
     * @param context  上下文
     * @param text     内容
     * @param textsize 字体大小
     * @param width    宽度
     * @return
     */
    public String getCharFormat(Context context, String text, float textsize, int width) {
        int textWidth = sp2px(context, textsize) * (getChineseNums(text) + (getNoChineseNums(text) + 1) / 2);
        if (textWidth > width) {
            int n = width / sp2px(context, textsize);
            if (n - 1 < text.length()) {
                text = text.substring(0, n - 1) + "...";
            }
        }
        return text;
    }

    /**
     * 字符串中，中文的字数
     *
     * @param str
     * @return
     */
    private int getChineseNums(String str) {
        int byteLength = str.getBytes().length;
        int strLength = str.length();
        return (byteLength - strLength) / 2;
    }

    /**
     * 字符串中，非中文的字数
     *
     * @param str
     * @return
     */
    private int getNoChineseNums(String str) {
        int byteLength = str.getBytes().length;
        int strLength = str.length();
        return strLength - (byteLength - strLength) / 2;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 过滤特殊字符
     *
     * @param s
     * @return
     */
    public static String formatFilter(String s) {
        String str = s.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
        return str;
    }
}
