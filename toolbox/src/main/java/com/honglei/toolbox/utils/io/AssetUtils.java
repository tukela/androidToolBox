package com.honglei.toolbox.utils.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;

/**
 * Asset 工具类<br>
 */
@SuppressWarnings("deprecation")
public class AssetUtils {
    public static String getStringFromAssets(String fileName,Context context){
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Uri getUriFromRes(int id,Context context){
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + context.getResources().getResourcePackageName(id) + "/"
                + context.getResources().getResourceTypeName(id) + "/"
                + context.getResources().getResourceEntryName(id));
    }
    /**
     * 读取Assets目录下面指定文件并返回String数据
     * @param context
     * @param fileName
     * @return
     */
    public static String getJsonDataFromAssets(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = context.getClass().getClassLoader().getResourceAsStream("assets/" + fileName);
        try {
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            String json = new String(buffer, "utf-8");
            stringBuilder = stringBuilder.append(json);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
    /**
     * 打开Asset下的文件
     *
     * @param fileName 文件名
     * @return
     */
    public static InputStream openAssetFile(Context context, String fileName) {
        AssetManager am = context.getAssets();
        InputStream is = null;
        try {
            is = am.open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    /**
     * 从assets 文件夹中读取文本数据
     */
    public static String getTextFromAssets(final Context context,
                                           String fileName) {
        String result = "";
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            // 获取文件的字节数
            int lenght = in.available();
            // 创建byte数组
            byte[] buffer = new byte[lenght];
            // 将文件中的数据读到byte数组中
            in.read(buffer);
            result = new String(buffer, "UTF-8");
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 从assets 文件夹中读取图片
     */
    public static Drawable loadImageFromAsserts(final Context ctx,
                                                String fileName) {
        try {
            InputStream is = ctx.getResources().getAssets().open(fileName);
            return Drawable.createFromStream(is, null);
        } catch (IOException e) {
            if (e != null) {
                e.printStackTrace();
            }
        } catch (OutOfMemoryError e) {
            if (e != null) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
