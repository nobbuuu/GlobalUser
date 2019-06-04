package com.sy.globletake_user.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.widget.ImageView;

import com.sy.globletake_user.R;

import org.xutils.image.ImageOptions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
public class BitmapUtil {
    //压缩尺寸
    public static Bitmap compressSize(Bitmap source,int targetSize){
        int sWidth = source.getWidth();
        int sHeight = source.getHeight();
        //计算压缩比例
        int wScale = sWidth/targetSize;
        int hScale = sHeight/targetSize;
        //获取原图片的最小边长
        int sSize = Math.min(sWidth,sHeight);
        //int sSize = sWidth/targetSize;

        int sampleSize = sSize/targetSize;
        Bitmap bitmap = Bitmap.createBitmap(source,(sWidth-sSize)/2,(sHeight-sSize)/2,sSize,sSize);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        Bitmap targetBitmap = BitmapFactory.decodeStream(inputStream,null,options);
        source.recycle();
        bitmap.recycle();
        return targetBitmap;
    }
    //对图片的质量进行压缩
    public static Bitmap compressQuality(Bitmap source){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int quality = 100;
        int length = outputStream.toByteArray().length;
        //source.compress(Bitmap.CompressFormat.JPEG,80,outputStream);
        do{
            quality-=10;
            //每次压缩之前要清空ByteArrayOutputStream
            outputStream.reset();
            source.compress(Bitmap.CompressFormat.JPEG,quality,outputStream);
        }
        while (length>1024*20);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        Bitmap targetbitmap = BitmapFactory.decodeStream(inputStream,null,null);
        source.recycle();
        return  targetbitmap;

    }
    //把图片加工成圆形图片
    public static Bitmap createCircleBitmap(Bitmap source){
        int sWidth = source.getHeight();
        int sHeight = source.getHeight();
        int size = Math.min(sWidth, sHeight);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap bitmap = Bitmap.createBitmap(source, (sWidth - size) / 2, (sHeight - size) / 2, size, size);
        //创建一张空图片
        Bitmap target = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(size/2,size/2,size/2,paint);
        //设置两张图片重合时取交集
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //加载完后的图片画上去
        canvas.drawBitmap(bitmap,0,0,paint);
        source.recycle();
        bitmap.recycle();
        return target;
    }
    //把图片加工成圆形图片
    public static ImageOptions getCircleBitmapOption(){
        ImageOptions options = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setUseMemCache(true)
                .setCircular(true)
                .setFailureDrawableId(R.mipmap.tanchuang_touxiang)
                .build();
        return options;
    }
}
