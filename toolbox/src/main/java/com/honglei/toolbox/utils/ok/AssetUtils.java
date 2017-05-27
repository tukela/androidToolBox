package com.honglei.toolbox.utils.ok;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;

/*******
 * Asset 工具类
 * <ul>
 * <li>{@link #getStringFromAssets(String fileName,Context context)  ｝</li>读取文件从 asset
 * <li>{@link #getUriFromRes(int id,Context context)  ｝</li>获取Uri
 * <li>{@link #openAssetFile(Context context, String fileName)  ｝</li>打开Asset下的文件
 * <li>{@link #getImageFromAsserts(Context ctx,String fileName) ｝</li>从assets 文件夹中读取图片
 * </ul>
 ****/
@SuppressWarnings("deprecation")
public class AssetUtils {
    /*****
     * 读取文件从 asset
     * @param fileName
     * @param context
     * @return
     */
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


    /******
     * 获取Uri
     * @param id
     * @param context
     * @return
     */
    public static Uri getUriFromRes(int id,Context context){
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + context.getResources().getResourcePackageName(id) + "/"
                + context.getResources().getResourceTypeName(id) + "/"
                + context.getResources().getResourceEntryName(id));
    }

    /**
     * 打开Asset下的文件
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
     * 从assets 文件夹中读取图片
     */
    public static Drawable getImageFromAsserts(Context ctx,String fileName) {
        try {
            InputStream is = ctx.getResources().getAssets().open(fileName);
            return Drawable.createFromStream(is, null);
        } catch (IOException e) {
                e.printStackTrace();
        } catch (OutOfMemoryError e) {
                e.printStackTrace();
        } catch (Exception e) {
                e.printStackTrace();
        }
        return null;
    }



}
