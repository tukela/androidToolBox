package com.honglei.toolbox.utils.ok;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Environment;
import com.honglei.toolbox.utils.ListUtils;
import com.honglei.toolbox.utils.ObjectUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * APP  应用工具类
 * 获取应用信息
 * 获得APP的名称
 * 应用进程名称是否为XX
 * 运行在后台？
 * 运行在前台？
 * 启动默认activity
 * 获取所以activity
 * 检查有没有应用程序来接受处理你发出的intent
 * 获取所有app的安装路径
 * 获取所有可启动app的包名
 * 清除本应用所有数据库，本应用数据库，sp，内外部缓存
 * 清除指定目录下文件
 * 获得文件大小和文件夹下所有文件的大小
 *
 *
 *
 *
 *
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
    public static void startApkActivity(final Context activity,
                                        String packageName) throws Exception {
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
    public static ArrayList<HashMap<String, String>> getAllInstelAppUrl(
            Context context) {

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
    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
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
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
     *
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     *
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 按名字清除本应用数据库
     *
     * @param context
     * @param dbName
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容
     *
     * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }


    /**
     * 清除本应用所有的数据
     *
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
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
     *
     * @param filePath
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     *
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
     *
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

}
