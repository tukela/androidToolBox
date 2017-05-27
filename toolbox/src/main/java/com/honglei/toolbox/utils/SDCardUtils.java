package com.honglei.toolbox.utils;

import java.io.*;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;


/**
 * 内存卡 工具类<br>
 */
public class SDCardUtils {

    private static final String TAG = SdCardUtil.class.getSimpleName();

    /**
     * is sd card available.
     * @return true if available
     */
    public boolean isSdCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * Get {@link android.os.StatFs}.
     */
    public static StatFs getStatFs(String path) {
        return new StatFs(path);
    }

    /**
     * Get phone data path.
     */
    public static String getDataPath() {
        return Environment.getDataDirectory().getPath();

    }

    /**
     * Get SD card path.
     */
    public static String getNormalSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * Get SD card path by CMD.
     */
    public static String getSDCardPath() {
        String cmd = "cat /proc/mounts";
        String sdcard = null;
        Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
        BufferedReader bufferedReader = null;
        try {
            Process p = run.exec(cmd);// 启动另一个进程来执行命令
            bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getInputStream())));
            String lineStr;
            while ((lineStr = bufferedReader.readLine()) != null) {
                //Log.i(TAG, "proc/mounts:   " + lineStr);
                if (lineStr.contains("sdcard")
                        && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray.length >= 5) {
                        sdcard = strArray[1].replace("/.android_secure", "");
                        //Log.i(TAG, "find sd card path:   " + sdcard);
                        return sdcard;
                    }
                }
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    // p.exitValue()==0表示正常结束，1：非正常结束
                    //Log.e(TAG, cmd + " 命令执行失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sdcard = Environment.getExternalStorageDirectory().getPath();
        //Log.i(TAG, "not find sd card path return default:   " + sdcard);
        return sdcard;
    }

    /**
     * Get SD card path list.
     */
    public static ArrayList<String> getSDCardPathEx() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                //Log.i(TAG, "mount:  " + line);
                if (line.contains("secure")) {
                    continue;
                }
                if (line.contains("asec")) {
                    continue;
                }

                if (line.contains("fat")) {
                    String columns[] = line.split(" ");
                    if (columns.length > 1) {
                        list.add("*" + columns[1]);
                    }
                } else if (line.contains("fuse")) {
                    String columns[] = line.split(" ");
                    if (columns.length > 1) {
                        list.add(columns[1]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Get available size of SD card.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getAvailableSize(String path) {
        try {
            File base = new File(path);
            StatFs stat = new StatFs(base.getPath());
            return stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get SD card info detail.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static SDCardInfo getSDCardInfo() {
        SDCardInfo sd = new SDCardInfo();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            sd.isExist = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                File sdcardDir = Environment.getExternalStorageDirectory();
                StatFs sf = new StatFs(sdcardDir.getPath());

                sd.totalBlocks = sf.getBlockCountLong();
                sd.blockByteSize = sf.getBlockSizeLong();

                sd.availableBlocks = sf.getAvailableBlocksLong();
                sd.availableBytes = sf.getAvailableBytes();

                sd.freeBlocks = sf.getFreeBlocksLong();
                sd.freeBytes = sf.getFreeBytes();

                sd.totalBytes = sf.getTotalBytes();
            }
        }
//        if (Log.isPrint) {
//           //Log.i(TAG, sd.toString());
//        }
        return sd;
    }


    /**
     * see more {@link android.os.StatFs}
     */
    public static class SDCardInfo {
        public boolean isExist;
        public long totalBlocks;
        public long freeBlocks;
        public long availableBlocks;

        public long blockByteSize;

        public long totalBytes;
        public long freeBytes;
        public long availableBytes;

        @Override
        public String toString() {
            return "SDCardInfo{" +
                    "isExist=" + isExist +
                    ", totalBlocks=" + totalBlocks +
                    ", freeBlocks=" + freeBlocks +
                    ", availableBlocks=" + availableBlocks +
                    ", blockByteSize=" + blockByteSize +
                    ", totalBytes=" + totalBytes +
                    ", freeBytes=" + freeBytes +
                    ", availableBytes=" + availableBytes +
                    '}';
        }
    }

    /**
     * 判断SDCard是否可用
     */
    public static boolean isSDCardEnable() {
        boolean result = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
//        if (result)
            //LogUtils.i("当前内存卡有效");
     //   else
            //LogUtils.i("当前内存卡无效");

        return result;

    }


    /**
     * 获取SD卡路径
     */
    public static ArrayList<String> getSDCardPathEx() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure")) {
                    continue;
                }
                if (line.contains("asec")) {
                    continue;
                }

                if (line.contains("fat")) {
                    String columns[] = line.split(" ");
                    if (columns.length > 1) {
                        list.add("*" + columns[1]);
                    }
                } else if (line.contains("fuse")) {
                    String columns[] = line.split(" ");
                    if (columns.length > 1) {
                        list.add(columns[1]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取外部存储设备的总空间大小
     */
    public static long getSDCardTotalSize(Context context) {
        if (isSDCardEnable()) {
            // 得到一个外部存储设备的目录/通过getPath得到路径
            File path = Environment.getExternalStorageDirectory();
            // 文件系统的帮助类，传入一个路径可以得到路径的信息
            StatFs stat = new StatFs(path.getPath());
            // 得到该存储空间每一块存储空间的大小
            long blockSize = stat.getBlockSize();
            // 得到空间总个数
            long totalBlocks = stat.getBlockCount();

            // 得到总空间大小
            long totalSize = totalBlocks * blockSize;

            // String totalStr = Formatter.formatFileSize(context, totalSize);

            return totalSize;
        }
        return 0;
    }

    /**
     * 获取外部存储设备的剩余空间大小
     */
    public static long getSDCardAvailSize(Context context) {
        if (isSDCardEnable()) {
            // 得到一个外部存储设备的目录/通过getPath得到路径
            File path = Environment.getExternalStorageDirectory();
            // 文件系统的帮助类，传入一个路径可以得到路径的信息
            StatFs stat = new StatFs(path.getPath());
            // 得到该存储空间每一块存储空间的大小
            long blockSize = stat.getBlockSize();
            // 得到可用的空间个数
            long availableBlocks = stat.getAvailableBlocks();
            // 得到可用空间大小
            long availSize = availableBlocks * blockSize;
            // String availStr = Formatter.formatFileSize(context, availSize);
            return availSize;
        }
        return 0;
    }

    /**
     * 获取内部存储设备的总空间大小
     *
     * @param context
     * @return
     */
    public static long getRomSpaceTotalSize(Context context) {
        if (isSDCardEnable()) {
            // 得到一个内部存储设备的目录/通过getPath得到路径
            File path = Environment.getDataDirectory();
            // 文件系统的帮助类，传入一个路径可以得到路径的信息
            StatFs stat = new StatFs(path.getPath());
            // 得到该存储空间每一块存储空间的大小
            long blockSize = stat.getBlockSize();
            // 得到空间总个数
            long totalBlocks = stat.getBlockCount();
            // 得到总空间大小
            long totalSize = totalBlocks * blockSize;

            // String totalStr = Formatter.formatFileSize(context, totalSize);

            return totalSize;
        }
        return 0;
    }

    /**
     * 获取内部存储设备的剩余空间大小
     *
     * @param context
     * @return
     */
    public static long getRomSpaceAvailSize(Context context) {
        if (isSDCardEnable()) {
            // 得到一个内部存储设备的目录/通过getPath得到路径
            File path = Environment.getDataDirectory();
            // 文件系统的帮助类，传入一个路径可以得到路径的信息
            StatFs stat = new StatFs(path.getPath());
            // 得到该存储空间每一块存储空间的大小
            long blockSize = stat.getBlockSize();
            // 得到可用的空间个数
            long availableBlocks = stat.getAvailableBlocks();
            // 得到可用空间大小
            long availSize = availableBlocks * blockSize;

            // String availStr = Formatter.formatFileSize(context, availSize);
            return availSize;
        }
        return 0;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        String path = Environment.getRootDirectory().getAbsolutePath();
        //LogUtils.i("当前存储路径：" + path);
        return path;
    }

    /**
     * 判断SDCard是否已满
     *
     * @return
     */
    @SuppressWarnings({"deprecation", "unused"})
    public static boolean isSDCardSizeOverflow() {
        boolean result = false;
        // 取得SDCard当前的状态
        String sDcString = Environment.getExternalStorageState();

        if (sDcString.equals(Environment.MEDIA_MOUNTED)) {

            // 取得sdcard文件路径
            File pathFile = Environment
                    .getExternalStorageDirectory();
            StatFs statfs = new StatFs(pathFile.getPath());

            // 获取SDCard上BLOCK总数
            long nTotalBlocks = statfs.getBlockCount();

            // 获取SDCard上每个block的SIZE
            long nBlocSize = statfs.getBlockSize();

            // 获取可供程序使用的Block的数量
            long nAvailaBlock = statfs.getAvailableBlocks();

            // 获取剩下的所有Block的数量(包括预留的一般程序无法使用的块)
            long nFreeBlock = statfs.getFreeBlocks();

            // 计算SDCard 总容量大小MB
            long nSDTotalSize = nTotalBlocks * nBlocSize / 1024 / 1024;

            // 计算 SDCard 剩余大小MB
            long nSDFreeSize = nAvailaBlock * nBlocSize / 1024 / 1024;
            if (nSDFreeSize <= 1) {
                result = true;
            }
        }// end of if
        // end of func
        return result;
    }

}
