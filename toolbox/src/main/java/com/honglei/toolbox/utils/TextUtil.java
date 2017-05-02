package com.honglei.toolbox.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * Created by hl on 2017/4/27.
 */
public class TextUtil {

    /**
     * 设置文本前景色
     */
    public static SpannableString getSpanText(String text,int start,int end)
    {
        return getSpanText(text,start,end,"#67CABF");
    }
    /**
     * 设置文本前景色
     */
    public static SpannableString getSpanText(String text,int start,int end,String color)
    {
        SpannableString spannableString = new SpannableString(text);
     //   ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF8892"));
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor(color));
        spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
       return spannableString;
    }
}
