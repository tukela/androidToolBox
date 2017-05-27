package com.honglei.toolbox.utils.ok;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.honglei.toolbox.utils.ListUtils;
import com.honglei.toolbox.utils.ObjectUtils;
import com.honglei.toolbox.utils.ToastUtil;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*******
 * <ul>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * <li>{@link #  ｝</li>
 * </ul>
 ****/



/**
 * APP工具
 * * <ul>
 * <li>{@link #getPackageInfo(Context context)}</li> 获取应用信息
 * <li>{@link #getAppName(Context context)  ｝</li>获得APP的名称
 * <li>{@link #isNamedProcess(Context context, String processName)  ｝</li>应用进程名称是否为XX
 * <li>{@link #isApplicationInBackground  ｝</li>运行在后台？
 * <li>{@link #isRunningForeground  ｝</li>运行在前台？
 * <li>{@link #startApkActivity(Context activity, String packageName)  ｝</li>启动默认activity
 * <li>{@link #getActivities(Context activity)  ｝</li>获取所有activity
 * <li>{@link #isIntentAvailable(Context context, String action)  ｝</li>检查有没有应用程序来接受处理你发出的intent
 * <li>{@link #getAllInstelAppUrl( Context context)  ｝</li>获取所有app的安装路径
 * <li>{@link #getAllCanStartApp(Context context) ｝</li>获取所有可启动app的包名
 * <li>{@link #cleanAppCache(Context context)  ｝</li>清除应用缓存
 * <li>{@link #cleanApplicationData(Context context, String... filepath)   ｝</li>清除本应用所有的数据
 * <li>{@link #getInternalCacheSize(Context context)  ｝</li>
 * <li>{@link #getExternalCacheSize(Context context)  ｝</li>
 * <li>{@link #deleteFilesByDirectory(File directory)  ｝</li>清除指定目录下文件
 * <li>{@link #getFilesSize(File mfile)  ｝</li>获得文件大小和文件夹下所有文件的大小


 * <li>{@link #hideIME(Activity window)  ｝</li>
 * <li>{@link #isAppAlive(Context context)  ｝</li>
 * <li>{@link #isAppOnForeground(Context context) ｝</li>程序是否在前台运行
 * <li>{@link #getVersion(Context context)  ｝</li>
 * <li>{@link #hideSoftInput(Context context, View view)  ｝</li>隐藏软键盘  view 必须是EditText或其子类
 * <li>{@link #showSoftInput(Context context, View view)  ｝</li>示软键盘
 * <li>{@link #openUrl(Context context, String url)  ｝</li>使用浏览器打开指定url地址
 * <li>{@link #callPhone(Context context, String tel) ｝</li> callPhone(Context context, String tel)
 *
 * <li>{@link #isSDCardAvailable()  ｝</li>

 * </ul>
 */
public class AppUtil {



    /**
     * 获取App包 信息
     * @param context
     * @return
     */
    public  static PackageInfo getPackageInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }
    /**
     * 获得APP的名称
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        if (context == null) {
            return null;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            String appName = context.getResources().getString(labelRes);
            return appName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否运行在XX进程
     * whether this process is named with processName
     * @param context
     * @param processName
     */
    public static boolean isNamedProcess(Context context, String processName) {
        if (context == null) {
            return false;
        }
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfoList = manager.getRunningAppProcesses();
        if (ListUtils.isEmpty(processInfoList)) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {
            if (processInfo != null && processInfo.pid == pid
                    && ObjectUtils.isEquals(processName, processInfo.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 应用是否运行在后台
     * 需要权限 android.permission.GET_TASKS
     * @param context
     * @return if application is in background return true, otherwise return false
     */
    public static boolean isApplicationInBackground(Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName topActivity = taskList.get(0).topActivity;
            if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
    /**
     * 需要权限 android.permission.GET_TASKS
     * 判断是否前台运行
     */
    public static boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName componentName = taskList.get(0).topActivity;
            if (componentName != null && componentName.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
    /**
     * 启动APK的默认Activity
     *
     * @param activity
     * @param packageName
     */
    public static void startApkActivity(Context activity, String packageName) throws Exception {
        PackageManager pm = activity.getPackageManager();
        PackageInfo pi;

        pi = pm.getPackageInfo(packageName, 0);
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(pi.packageName);
        List<ResolveInfo> apps = pm.queryIntentActivities(intent, 0);
        ResolveInfo ri = apps.iterator().next();
        if (ri != null) {
            String className = ri.activityInfo.name;
            intent.setComponent(new ComponentName(packageName, className));
            activity.startActivity(intent);
        }
    }

    /**
     * 获取应用程序下所有Activity
     *
     * @param activity
     * @return
     */
    public static ArrayList<String> getActivities(Context activity) {
        ArrayList<String> result = new ArrayList<String>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.setPackage(activity.getPackageName());
        for (ResolveInfo info : activity.getPackageManager()
                .queryIntentActivities(intent, 0)) {
            result.add(info.activityInfo.name);
        }
        return result;
    }

    /**
     * 检查有没有应用程序来接受处理你发出的intent
     *
     * @param context
     * @param action
     * @return
     */
    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /**
     * 获取所有app的安装路径
     *
     * @param context
     * @return ArrayList<HashMap<String, String>> package:包名,url:地址
     */
    public static ArrayList<HashMap<String, String>> getAllInstelAppUrl( Context context) {
        ArrayList<HashMap<String, String>> appInfos = new ArrayList<HashMap<String, String>>();
        PackageManager pm = context.getPackageManager();
        for (ApplicationInfo app : pm.getInstalledApplications(0)) {
            HashMap<String, String> appInfo = new HashMap<String, String>();
            appInfo.put("package", app.packageName);
            appInfo.put("url", app.sourceDir);
            appInfos.add(appInfo);
        }
        return appInfos;
    }

    /**
     * 获取所有可启动app的包名
     *
     * @param context
     * @return ArrayList<String> 包名
     */
    public static ArrayList<String> getAllCanStartApp(Context context) {
        ArrayList<String> appInfos = new ArrayList<String>();
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        for (ResolveInfo app : pm.queryIntentActivities(intent, 0)) {
            appInfos.add(app.activityInfo.packageName);
        }
        return appInfos;
    }

    /********
     * 清除应用缓存
     * @param context
     * @return
     */
    public static long cleanAppCache(Context context)
    {
        long size=getInternalCacheSize(context)+getExternalCacheSize(context);
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanFiles(context);
        long size2=getInternalCacheSize(context)+getExternalCacheSize(context);
        return size-size2>0?size-size2:0;
    }

    /**
     * 清除本应用所有的数据
     * @param context
     * @param filepath
     */
    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }
    /**
     * 获取内部缓存大小
     * @param context
     * @return
     */
    public static long getInternalCacheSize(Context context){
        return getFilesSize(context.getCacheDir());
    }
    /**
     * 外部缓存大小
     * @param context
     * @return
     */
    public static long getExternalCacheSize(Context context){
        return getFilesSize(context.getExternalCacheDir());
    }
    /**
     * 清除/data/data/com.xxx.xxx/files下的内容
     * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }
    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }
    /**
     * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }
    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"+ context.getPackageName() + "/databases"));
    }

    /**
     * 清楚XX数据库
     * @param context
     * @param dbName
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }


    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
     *
     * @param filePath
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    /**
     * 获得文件大小和文件夹下所有文件的大小
     * @param mfile
     */
    private static long getFilesSize(File mfile) {
        if (mfile != null && mfile.exists()) {
            if (mfile.isDirectory()) {
                int sum = 0;
                for (File item : mfile.listFiles()) {
                    sum += getFilesSize(item);
                }
                return sum;
            } else {
                return mfile.length();
            }
        } else {
            return 0l;
        }
    }

    public static void hideIME(Activity window) {
        try {
            InputMethodManager imm = (InputMethodManager) window.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(window.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断app是否存活
     * @param context
     * @return
     */
    public static boolean isAppAlive(Context context){
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for(int i = 0; i < processInfos.size(); i++){
            if(processInfos.get(i).processName.equals(packageName)){
                Log.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        return false;
    }

    /**
     * 程序是否在前台运行
     * @return
     */
    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        //获取包管理者对象
        PackageManager pm = context.getPackageManager();
        try {
            //获取包的详细信息
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0.0";
        }
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
     * SD卡判断
     * @return
     */
    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


}
