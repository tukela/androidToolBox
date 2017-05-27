package com.honglei.toolbox.utils.ok;

import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.widget.ImageView;
import com.honglei.toolbox.utils.secarity.Base64;

import java.io.*;

/*******
 * <ul>
 * <li>{@link #bitmap2Bytes(Bitmap bmp,boolean needRecycle)｝</li>
 * <li>{@link #bytes2Bitmap(byte[] b)  ｝</li>
 * <li>{@link #drawableToBitmap(Drawable d)  ｝</li>
 * <li>{@link #bitmapToDrawable(Bitmap bitmap)  ｝</li>
 * <li>{@link #printScreen(View v, int quality)   ｝</li>截图的控件
 * <li>{@link #printScreen(View v)   ｝</li>
 * <li>{@link #zoomBitmap(Bitmap bitmap, int newWidth, int newHeight, boolean sameSize)  ｝</li>
 * <li>{@link #compressedImage(String filename, int maxWidth, int maxHeight)  ｝</li>
 * <li>{@link #getRoundedCornerBitmap(Bitmap bitmap, float roundPx)  ｝</li>
 * <li>{@link #createReflectionImageWithOrigin(Bitmap bitmap)  ｝</li>倒影
 * <li>{@link #createBitmapForWatermark(Bitmap src, Bitmap watermark)  ｝</li>水印
 * <li>{@link #potoMix(int direction, Bitmap... bitmaps)  ｝</li>
 * <li>{@link #createBitmapForFotoMix(Bitmap first, Bitmap second,int direction)  ｝</li>
 * <li>{@link #getMarkBitmap(String num, Bitmap source)  ｝</li>
 * <li>{@link #getBitmapByNum(int num, Bitmap source)  ｝</li>
 * <li>{@link #toGrayscale(Bitmap bmpOriginal)  ｝</li>
 * <li>{@link #toRoundCorner(Bitmap bitmap, int pixels)  ｝</li>
 * <li>{@link #commpressImg(String picPath,int imgIdstaus)  ｝</li> 压缩
 * <li>{@link #getFileSizes(File f)  ｝</li>
 * <li>{@link #compressImageFromFile(String srcPath)  ｝</li>
 * <li>{@link #toturn(Bitmap img,int direc)  ｝</li>  图片
 * <li>{@link #getBytesFromFile(File f)  ｝</li>
 * <li>{@link #getBlurBitmap(Context context, Bitmap bitmap, int radius)  ｝</li>
 * <li>{@link #saveBitmap(View view, String filePath)  ｝</li>
 * <li>{@link #saveBitmap(Bitmap bitmap, File file)  ｝</li>
 * <li>{@link #scaleImage(Bitmap b, float x, float y)  ｝</li>
 * <li>{@link #bitmapToBase64(Bitmap bitmap)  ｝</li>
 * <li>{@link #toRoundCorner(Bitmap bitmap)  ｝</li>
 * <li>{@link #createBitmapThumbnail  ｝</li>
 * <li>{@link #buildImageGetIntent(Uri saveTo, int aspectX, int aspectY,int outputX, int outputY, boolean returnData)   ｝</li>
 * <li>{@link #buildImageCropIntent(Uri uriFrom, Uri uriTo, int aspectX, int aspectY, int outputX, int outputY, boolean returnData)   ｝</li>
 * <li>{@link #buildImageCaptureIntent(Uri uri) ｝</li>
 * <li>{@link #calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)  ｝</li>
 * <li>{@link #getSmallBitmap(String filePath, int reqWidth, int reqHeight)  ｝</li>
 * <li>{@link #compressBitmapToBytes(String filePath, int reqWidth, int reqHeight, int quality)  ｝</li>
 * <li>{@link #compressBitmapSmallTo(String filePath, int reqWidth, int reqHeight, int maxLenth)  ｝</li>
 * <li>{@link #setImageBitmap(Context context, ImageView imageView, Bitmap bitmap)  ｝</li>渐变显示图片
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

public class ImageUtil {



    private static final int SCALED_WIDTH = 100;
    private static final int SCALED_HEIGHT = 100;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 3;
    public static final int BOTTOM = 4;


    /**
     * 该方法用于将bitmap转换为字节数组，png格式质量为100的。
     * @param bmp 待转化的bitmap
     * @return 返回btmap的字节数组
     */
    public static byte[] bitmap2Bytes(Bitmap bmp,boolean needRecycle) {
        if(bmp == null){
            return new byte[0];
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 该方法用于将字节数组转化为bitmap图
     * @param b  字节数组
     * @return bitmap位图
     */
    public static Bitmap bytes2Bitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }


    /**
     * Drawable 转 Bitmap
     *
     * @param d
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable d) {
        return d == null ? null : ((BitmapDrawable)d).getBitmap();
    }
    /**
     * Bitmap 转 Drawable
     *
     * @param bitmap
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(bitmap);
    }
    /**
     * 对控件截图。
     * @param v
     *            需要进行截图的控件。
     * @param quality
     *            图片的质量
     * @return 该控件截图的byte数组对象。
     */
    public static byte[] printScreen(View v, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        Bitmap bitmap = v.getDrawingCache();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        return baos.toByteArray();
    }

    /**
     * 截图
     *
     * @param v
     *            需要进行截图的控件
     * @return 该控件截图的Bitmap对象。
     */
    public static Bitmap printScreen(View v) {
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        return v.getDrawingCache();
    }

    /**
     * 该方法用于任意缩放指定大小的图片
     *
     * @param bitmap
     *            待修改的图片
     * @param newWidth
     *            新图片的宽度
     * @param newHeight
     *            新图片的高度
     * @param sameSize
     * 			  是否需要等比缩放，true等比，false固定缩放
     * @return 缩放后的新图片
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int newWidth, int newHeight, boolean sameSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) newWidth / width);
        float scaleHeight = ((float) newHeight / height);
        if(sameSize){
            scaleWidth = Math.abs(scaleWidth);
            scaleHeight = Math.abs(scaleHeight);
            matrix.reset();
            if (scaleWidth > scaleHeight) {
                matrix.postScale(scaleWidth, scaleWidth);
            } else {
                matrix.postScale(scaleHeight, scaleHeight);
            }
        }else{
            matrix.postScale(scaleWidth, scaleHeight);
        }
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return resizedBitmap;
    }

    /**
     * 压缩大图片
     * @param filename 文件路径
     * @param maxWidth 压缩的宽度
     * @param maxHeight 压缩的高度
     * @return
     */
    public static Bitmap compressedImage(String filename, int maxWidth, int maxHeight) {
        Bitmap bitmap = null;
        Matrix mMatrix = new Matrix();
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filename, opts);
            opts.inJustDecodeBounds = false;
            int srcWidth = opts.outWidth;
            int srcHeight = opts.outHeight;
            int desWidth = 0;
            int desHeight = 0;
            //

//           int hRatio=(int)Math.ceil(opts.outHeight/(float)maxHeight); //图片是高度的几倍
//           int wRatio=(int)Math.ceil(opts.outWidth/(float)maxWidth); //图片是宽度的几倍
//           System.out.println("hRatio:"+hRatio+"  wRatio:"+wRatio);
//           //缩小到  1/ratio的尺寸和 1/ratio^2的像素
//           if(hRatio>1||wRatio>1){
//               if(hRatio>wRatio){
//            	   opts.inSampleSize=hRatio;
//               }
//               else
//            	  opts.inSampleSize=wRatio;
//           }

            //

            double ratio = 0.0;
            if (srcWidth > srcHeight) {
                ratio = srcWidth / maxWidth;
                desWidth = maxWidth;
                desHeight = (int) (srcHeight / ratio);
//               mMatrix.setRotate(90);
            } else {
                ratio = srcHeight / maxHeight;
                desHeight = maxHeight;
                desWidth = (int) (srcWidth / ratio);
            }
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            if((int) (ratio) < 1){
                newOpts.inSampleSize = 1;
            }else{
                newOpts.inSampleSize = (int) (ratio);
            }
            newOpts.inJustDecodeBounds = false;
            newOpts.outWidth = desWidth;
            newOpts.outHeight = desHeight;

            bitmap = BitmapFactory.decodeFile(filename, newOpts);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), mMatrix, false);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //------------------------------------------------------------------------------------------------

    /**
     * 该方法用于将一个矩形图片的边角钝化
     *
     * @param bitmap
     *            待修改的图片
     * @param roundPx
     *            边角的弧度
     * @return 返回修改过边角的新图片
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }



    /**
     * 该方法用于生成图片的下方倒影效果
     *
     * @param bitmap
     *            代修改的图片
     * @return 有倒影效果的新图片
     */
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
                width, height / 2, matrix, false);
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        //  Set the Transfer mode to be porter duff and destination in  
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //  Draw a rectangle using the paint with our linear gradient  
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);
        return bitmapWithReflection;
    }

    /**
     * 水印
     *
     * @param //bitmap
     * @return
     */
    public static Bitmap createBitmapForWatermark(Bitmap src, Bitmap watermark) {
        if (src == null) {
            return null;
        }
        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        // create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        // draw src into
        cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
        // draw watermark into
        cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);// 在src的右下角画入水印
        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        // store
        cv.restore();// 存储
        return newb;
    }

    /**
     * 图片合成
     *
     * @return
     */
    public static Bitmap potoMix(int direction, Bitmap... bitmaps) {
        if (bitmaps.length <= 0) {
            return null;
        }
        if (bitmaps.length == 1) {
            return bitmaps[0];
        }
        Bitmap newBitmap = bitmaps[0];
        // newBitmap = createBitmapForFotoMix(bitmaps[0],bitmaps[1],direction);
        for (int i = 1; i < bitmaps.length; i++) {
            newBitmap = createBitmapForFotoMix(newBitmap, bitmaps[i], direction);
        }
        return newBitmap;
    }



    private static Bitmap createBitmapForFotoMix(Bitmap first, Bitmap second,int direction) {
        if (first == null) {
            return null;
        }
        if (second == null) {
            return first;
        }
        int fw = first.getWidth();
        int fh = first.getHeight();
        int sw = second.getWidth();
        int sh = second.getHeight();
        Bitmap newBitmap = null;
        if (direction == LEFT) {
            newBitmap = Bitmap.createBitmap(fw + sw, fh > sh ? fh : sh,
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawBitmap(first, sw, 0, null);
            canvas.drawBitmap(second, 0, 0, null);
        } else if (direction == RIGHT) {
            newBitmap = Bitmap.createBitmap(fw + sw, fh > sh ? fh : sh,
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawBitmap(first, 0, 0, null);
            canvas.drawBitmap(second, fw, 0, null);
        } else if (direction == TOP) {
            newBitmap = Bitmap.createBitmap(sw > fw ? sw : fw, fh + sh,
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawBitmap(first, 0, sh, null);
            canvas.drawBitmap(second, 0, 0, null);
        } else if (direction == BOTTOM) {
            newBitmap = Bitmap.createBitmap(sw > fw ? sw : fw, fh + sh,
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawBitmap(first, 0, 0, null);
            canvas.drawBitmap(second, 0, fh, null);
        }
        return newBitmap;
    }


    /**
     * 根据数字创建图片
     *
     * @param num
     *            分数
     * @param source
     *            原图资源图片
     * @param
    //	 *            小图片数字的宽度
     * @return 分数图片
     */
    public static Bitmap getMarkBitmap(String num, Bitmap source) {
        if (num != null && !(num.trim().equals(""))) {
            // if(num.length() < 2)
            // num = "0" + num;
            Bitmap bitmap = Bitmap.createBitmap(
                    (num.trim().length() * (source.getWidth() / 10)),
                    source.getHeight(), Bitmap.Config.ARGB_8888);
            for (int i = 0; i < num.length(); i++) {
                // if(i == num.length())
                // break;
                String str = num.substring(i, i + 1);
                int number = Integer.parseInt(str);
                Canvas canvas = new Canvas(bitmap);
                Bitmap tempBitmap = getBitmapByNum(number, source);
                canvas.drawBitmap(tempBitmap, i * tempBitmap.getWidth(), 0,
                        null);
            }
            return bitmap;
        }
        return null;
    }

    /**
     * 根据数字创建图片
     *
     * @param num
     *            数字
     * @param source
     *            资源图片
     * @return
     */
    private static Bitmap getBitmapByNum(int num, Bitmap source) {
        Bitmap tempBitmap = null;
        if (source != null) {
            int width = source.getWidth() / 10;
            int height = source.getHeight();
            tempBitmap = Bitmap.createBitmap(source, num * width, 0, width,
                    height);
        }
        return tempBitmap;
    }

    /** */
    /**
     * 图片去色,返回灰度图片
     *
     * @param bmpOriginal
     *            传入的图片
     * @return 去色后的图片
     */
    public static Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }



    /** */
    /**
     * 把图片变成圆角
     *
     * @param bitmap
     *            需要修改的图片
     * @param pixels
     *            圆角的弧度
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


    /**
     * 获得文件大小
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSizes(File f) throws Exception {
        long s = 0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s = fis.available();
        } else {
            f.createNewFile();
            System.out.println("文件不存在");
        }
        return s;
    }


    private static Bitmap compressImageFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;// 只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//
        float ww = 480f;//
        int be = 2;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置采样率
        // newOpts.inPreferredConfig = Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        // return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        // 其实是无效的,大家尽管尝试
        return bitmap;
    }

    /**
     * 图片反转
     * @param img
     * @return
     */
    public static Bitmap toturn(Bitmap img,int direc){
        Matrix matrix = new Matrix();
        if(direc == 0){
            matrix.postRotate(-90); /*向左翻转90度*/
        }else if(direc == 1){
            matrix.postRotate(90); /*向右翻转90度*/
        }
        int width = img.getWidth();
        int height =img.getHeight();
        img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
        return img;
    }

    /***
     * 文件转化为字节数组
     */
    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到模糊后的bitmap
     * 建议模糊度(在0.0到25.0之间)
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

    /*********
     *  获取view 背景保存为图片
     * @param view
     * @param filePath
     * @return
     */
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
           // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
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
    public static boolean saveBitmap(Bitmap bitmap, File file) {
        if (bitmap == null)
            return false;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }





    /**********
     * 缩放 图片
     * @param b
     * @param x
     * @param y
     * @return
     */
    public static Bitmap scaleImage(Bitmap b, float x, float y)
    {
        if (b == null) {
            return null;
        }
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


    /**s
     * 把bitmap转换成Base64编码String
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        return Base64.encodeToString(bitmap2Bytes(bitmap,true), Base64.DEFAULT);
    }


    public static Bitmap toRoundCorner(Bitmap bitmap) {
        int height = bitmap.getHeight();
        int width = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        //paint.setColor(0xff424242);
        paint.setColor(Color.TRANSPARENT);
        canvas.drawCircle(width / 2, height / 2, width / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static Bitmap createBitmapThumbnail(Bitmap bitMap, boolean needRecycle, int newHeight, int newWidth) {
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newBitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);
        if (needRecycle)
            bitMap.recycle();
        return newBitMap;
    }



    public static Intent buildImageGetIntent(Uri saveTo, int aspectX, int aspectY,int outputX, int outputY, boolean returnData) {
      //  Log.i(TAG, "Build.VERSION.SDK_INT : " + Build.VERSION.SDK_INT);
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        intent.putExtra("output", saveTo);
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", returnData);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        return intent;
    }


    public static Intent buildImageCropIntent(Uri uriFrom, Uri uriTo, int aspectX, int aspectY, int outputX, int outputY, boolean returnData) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uriFrom, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("output", uriTo);
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", returnData);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        return intent;
    }

    public static Intent buildImageCaptureIntent(Uri uri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int h = options.outHeight;
        int w = options.outWidth;
        int inSampleSize = 0;
        if (h > reqHeight || w > reqWidth) {
            float ratioW = (float) w / reqWidth;
            float ratioH = (float) h / reqHeight;
            inSampleSize = (int) Math.min(ratioH, ratioW);
        }
        inSampleSize = Math.max(1, inSampleSize);
        return inSampleSize;
    }

    public static Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public byte[] compressBitmapToBytes(String filePath, int reqWidth, int reqHeight, int quality) {
        Bitmap bitmap = getSmallBitmap(filePath, reqWidth, reqHeight);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        bitmap.recycle();
        //Log.i(TAG, "Bitmap compressed success, size: " + bytes.length);
        return bytes;
    }

    public byte[] compressBitmapSmallTo(String filePath, int reqWidth, int reqHeight, int maxLenth) {
        int quality = 100;
        byte[] bytes = compressBitmapToBytes(filePath, reqWidth, reqHeight, quality);
        while (bytes.length > maxLenth && quality > 0) {
            quality = quality / 2;
            bytes = compressBitmapToBytes(filePath, reqWidth, reqHeight, quality);
        }
        return bytes;
    }

    /**
     * 渐变显示图片
     *
     * @param context
     * @param imageView
     * @param bitmap
     */
    @SuppressWarnings("deprecation")
    public static void setImageBitmap(Context context, ImageView imageView, Bitmap bitmap) {
        // Use TransitionDrawable to fade in.
        final TransitionDrawable td = new TransitionDrawable(new Drawable[]{
                new ColorDrawable(context.getResources().getColor(android.R.color.transparent)),
                new BitmapDrawable(context.getResources(), bitmap)});
        // noinspection deprecation
        // imageView.setBackgroundDrawable(imageView.getDrawable());
        imageView.setImageDrawable(td);
        td.startTransition(200);
    }




}
