package com.honglei.toolbox.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.widget.ImageView;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * ImageUtils
 * <ul>
 * convert between Bitmap, byte array, Drawable
 * <li>{@link #bitmapToByte(Bitmap)}</li>
 * <li>{@link #bitmapToDrawable(Bitmap)}</li>
 * <li>{@link #byteToBitmap(byte[])}</li>
 * <li>{@link #byteToDrawable(byte[])}</li>
 * <li>{@link #drawableToBitmap(Drawable)}</li>
 * <li>{@link #drawableToByte(Drawable)}</li>
 * </ul>
 * <ul>
 * get image
 * <li>{@link #getInputStreamFromUrl(String, int)}</li>
 * <li>{@link #getBitmapFromUrl(String, int)}</li>
 * <li>{@link #getDrawableFromUrl(String, int)}</li>
 * </ul>
 * <ul>
 * scale image
 * <li>{@link #scaleImageTo(Bitmap, int, int)}</li>
 * <li>{@link #scaleImage(Bitmap, float, float)}</li>
 * </ul>
 * 
 *  2012-6-27
 */
public class ImageUtils {
    /**
     * 建议模糊度(在0.0到25.0之间)
     */
    private static final int BLUR_RADIUS = 20;
    private static final int SCALED_WIDTH = 100;
    private static final int SCALED_HEIGHT = 100;

    public static void blur(ImageView imageView, Bitmap bitmap) {
        blur(imageView, bitmap, BLUR_RADIUS);
    }

    public static void blur(ImageView imageView, Bitmap bitmap, int radius) {
        imageView.setImageBitmap(getBlurBitmap(imageView.getContext(), bitmap, radius));
    }

    public static Bitmap getBlurBitmap(Context context, Bitmap bitmap) {
        return getBlurBitmap(context, bitmap, BLUR_RADIUS);
    }

    /**
     * 得到模糊后的bitmap
     * thanks http://wl9739.github.io/2016/07/14/教你一分钟实现模糊效果/
     *
     * @param context
     * @param bitmap
     * @param radius
     * @return
     */
    public static Bitmap getBlurBitmap(Context context, Bitmap bitmap, int radius) {
        // 将缩小后的图片做为预渲染的图片。
        Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, SCALED_WIDTH, SCALED_HEIGHT, false);
        // 创建一张渲染后的输出图片。
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        // 创建一个模糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间。
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去。
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

        // 设置渲染的模糊程度, 25f是最大模糊度
        blurScript.setRadius(radius);
        // 设置blurScript对象的输入内存
        blurScript.setInput(tmpIn);
        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);

        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

    public static String  saveBitmap(View view){
        String filePath= Environment.getExternalStorageDirectory().getPath()+"/"+System.currentTimeMillis()+".png";
        return saveBitmap(view,filePath);
    }
    public static String  saveBitmap(View view, String filePath){
        // 创建对应大小的bitmap
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        //存储
        FileOutputStream outStream = null;
        File file=new File(filePath);
        if(file.isDirectory()){//如果是目录不允许保存
            return null;
        }
        try {
            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            filePath=null;
        }finally {
            try {
                bitmap.recycle();
                if(outStream!=null){
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                filePath=null;
            }
        }
        return filePath;

    }
    private ImageUtils() {
        throw new AssertionError();
    }

    /**
     * convert Bitmap to byte array
     * 
     * @param b
     * @return
     */
    public static byte[] bitmapToByte(Bitmap b) {
        if (b == null) {
            return null;
        }

        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, o);
        return o.toByteArray();
    }
    public static Bitmap BitmapZoom(Bitmap b, float x, float y)
    {
        int w=b.getWidth();
        int h=b.getHeight();
        float sx=x/w;
        float sy=y/h;
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy);
        Bitmap resizeBmp = Bitmap.createBitmap(b, 0, 0, w,
                h, matrix, true);
        return resizeBmp;
    }
    /**
     * convert byte array to Bitmap
     * 
     * @param b
     * @return
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * convert Drawable to Bitmap
     * 
     * @param d
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable d) {
        return d == null ? null : ((BitmapDrawable)d).getBitmap();
    }

    /**
     * convert Bitmap to Drawable
     * 
     * @param b
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap b) {
        return b == null ? null : new BitmapDrawable(b);
    }

    /**
     * convert Drawable to byte array
     * 
     * @param d
     * @return
     */
    public static byte[] drawableToByte(Drawable d) {
        return bitmapToByte(drawableToBitmap(d));
    }

    /**
     * convert byte array to Drawable
     * 
     * @param b
     * @return
     */
    public static Drawable byteToDrawable(byte[] b) {
        return bitmapToDrawable(byteToBitmap(b));
    }

    /**
     * get input stream from network by imageurl, you need to close inputStream yourself
     * 
     * @param imageUrl
     * @param readTimeOutMillis
     * @return
     * @see ImageUtils#getInputStreamFromUrl(String, int, boolean)
     */
    public static InputStream getInputStreamFromUrl(String imageUrl, int readTimeOutMillis) {
        return getInputStreamFromUrl(imageUrl, readTimeOutMillis, null);
    }

    /**
     * get input stream from network by imageurl, you need to close inputStream yourself
     * 
     * @param imageUrl
     * @param readTimeOutMillis read time out, if less than 0, not set, in mills
     * @param requestProperties http request properties
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public static InputStream getInputStreamFromUrl(String imageUrl, int readTimeOutMillis,
                                                    Map<String, String> requestProperties) {
        InputStream stream = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            HttpUtils.setURLConnection(requestProperties, con);
            if (readTimeOutMillis > 0) {
                con.setReadTimeout(readTimeOutMillis);
            }
            stream = con.getInputStream();
        } catch (MalformedURLException e) {
            IOUtils.close(stream);
            throw new RuntimeException("MalformedURLException occurred. ", e);
        } catch (IOException e) {
            IOUtils.close(stream);
            throw new RuntimeException("IOException occurred. ", e);
        }
        return stream;
    }

    /**
     * get drawable by imageUrl
     * 
     * @param imageUrl
     * @param readTimeOutMillis
     * @return
     * @see ImageUtils#getDrawableFromUrl(String, int, boolean)
     */
    public static Drawable getDrawableFromUrl(String imageUrl, int readTimeOutMillis) {
        return getDrawableFromUrl(imageUrl, readTimeOutMillis, null);
    }

    /**
     * get drawable by imageUrl
     * 
     * @param imageUrl
     * @param readTimeOutMillis read time out, if less than 0, not set, in mills
     * @param requestProperties http request properties
     * @return
     */
    public static Drawable getDrawableFromUrl(String imageUrl, int readTimeOutMillis,
                                              Map<String, String> requestProperties) {
        InputStream stream = getInputStreamFromUrl(imageUrl, readTimeOutMillis, requestProperties);
        Drawable d = Drawable.createFromStream(stream, "src");
        IOUtils.close(stream);
        return d;
    }

    /**
     * get Bitmap by imageUrl
     * 
     * @param imageUrl
     * @param readTimeOut
     * @return
     * @see ImageUtils#getBitmapFromUrl(String, int, boolean)
     */
    public static Bitmap getBitmapFromUrl(String imageUrl, int readTimeOut) {
        return getBitmapFromUrl(imageUrl, readTimeOut, null);
    }

    /**
     * get Bitmap by imageUrl
     * 
     * @param imageUrl
     * @param requestProperties http request properties
     * @return
     */
    public static Bitmap getBitmapFromUrl(String imageUrl, int readTimeOut, Map<String, String> requestProperties) {
        InputStream stream = getInputStreamFromUrl(imageUrl, readTimeOut, requestProperties);
        Bitmap b = BitmapFactory.decodeStream(stream);
        IOUtils.close(stream);
        return b;
    }

    /**
     * scale image
     * 
     * @param org
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
        return scaleImage(org, (float) newWidth / org.getWidth(), (float) newHeight / org.getHeight());
    }

    /**
     * scale image
     * 
     * @param org
     * @param scaleWidth sacle of width
     * @param scaleHeight scale of height
     * @return
     */
    public static Bitmap scaleImage(Bitmap org, float scaleWidth, float scaleHeight) {
        if (org == null) {
            return null;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
    }

}
