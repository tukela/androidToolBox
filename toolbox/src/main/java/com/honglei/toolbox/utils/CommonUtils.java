package com.honglei.toolbox.utils;

import android.graphics.Color;
import android.os.Handler;
import android.util.Base64;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 公用工具类
 */

public class CommonUtils {
    private static final Handler HANDLER = new Handler();

    private CommonUtils() {
    }

    public static void wait(int millis, Runnable callback){
        HANDLER.postDelayed(callback, millis);
    }

    public static boolean isBrightColor(int color) {
        if(android.R.color.transparent == color) {
            return true;
        }
        int [] rgb = {Color.red(color), Color.green(color), Color.blue(color)};
        int brightness = (int) Math.sqrt(
                rgb[0] * rgb[0] * 0.241 +
                        rgb[1] * rgb[1] * 0.691 +
                        rgb[2] * rgb[2] * 0.068);
        return brightness >= 200;
    }

    public static int getDarkerColor(int color) {
        float factor = 0.8f;
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.argb(a,
                Math.max((int) (r * factor), 0),
                Math.max((int) (g * factor), 0),
                Math.max((int) (b * factor), 0));
    }

    public static String formatSeconds(int seconds) {
        return getTwoDecimalsValue(seconds / 3600) + ":"
                + getTwoDecimalsValue(seconds / 60) + ":"
                + getTwoDecimalsValue(seconds % 60);
    }

    private static String getTwoDecimalsValue(int value) {
        if (value >= 0 && value <= 9) {
            return "0" + value;
        } else {
            return value + "";
        }
    }

    /**
     * 是否是base64编码
     * @param str
     * @return
     */
//    public static String convertBase64(String str){
//        String result=GApp.Empty;
//        try {
//            result=new String(android.util.Base64.decode(str, Base64.DEFAULT));
//        } catch (Exception e) {
//            return result;
//        }
//        return result;
//    }

    /**
     *Date转String
     * @param currentTime
     * @return
     */
    public static String getStringDate(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    /**
     *String转Date
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date parse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(strDate);
    }

    /**
     * base
     * @param data
     * @return
     */
    public static String convertBase64(String data) {

        data=replaceBlank(data);
        String srcText="";
        try {
            srcText=new String(Base64.decode(data, Base64.DEFAULT));
        } catch (Exception e) {
            srcText="";
        }
        // 调试发现再次编译会有换行符，过滤换行符比较
        String base64Text = Base64.encodeToString(srcText.getBytes(), Base64.DEFAULT).replace("\r\n", "").replace("\n", "");
        return data.equals(base64Text) ? srcText : data;
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");

        }
        return dest;
    }

    public static String getDateToString(long time) {
        String strResult="";
        try {
            Date d = new Date(time);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            strResult=sf.format(d);
        }catch (Exception ex) {}

        return strResult;
    }
}
