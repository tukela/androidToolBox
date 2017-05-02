package com.honglei.toolbox.utils.record;


import java.text.SimpleDateFormat;


public class TimeUtils {

    //毫秒转秒
    public static String long2String(long time) {
        String timeStr = "00:";
        //毫秒转秒
        int sec = (int) time / 1000;
        int min = sec / 60;    //分钟
        sec = sec % 60;        //秒
        if (min < 10) {    //分钟补0
            if (sec < 10) {    //秒补0
                timeStr += "0" + min + ":0" + sec;
            } else {
                timeStr += "0" + min + ":" + sec;
            }
        } else {
            if (sec < 10) {    //秒补0
                timeStr += min + ":0" + sec;
            } else {
                timeStr += min + ":" + sec;
            }
        }
        return  timeStr;
    }

    /**
     * 返回当前时间的格式为 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(System.currentTimeMillis());
    }


}
