package com.ttit.zhihuibeijing.utils.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.ttit.zhihuibeijing.utils.MyLogger;

/**
 * Created by JW.S on 2020/10/11 10:53 AM.
 */
public class BitmapUtils {
    private static final String TAG = "BitmapUtils";

    static {
        netCacheUtils = new NetCacheUtils();
        localCacheUtils = new LocalCacheUtils();
        memoryCacheUtils = new MemoryCacheUtils();
    }

    private static NetCacheUtils netCacheUtils;
    private static LocalCacheUtils localCacheUtils;
    private static MemoryCacheUtils memoryCacheUtils;

    //显示图片
    public static void disPlay(Context context, ImageView iv, String url) {
        //内存缓存
        Bitmap bitmap = memoryCacheUtils.readCache(url);
        if (bitmap != null) {
            iv.setImageBitmap(bitmap);
            MyLogger.i(TAG, "从内存获取了图片");
            return;
        }
        //磁盘缓存
        bitmap = localCacheUtils.readCache(context, url);
        if (bitmap != null) {
            iv.setImageBitmap(bitmap);
            MyLogger.i(TAG, "从磁盘获取了图片");
            return;
        }
        //网络缓存
        netCacheUtils.getBitmapFromNet(context, iv, url);
    }
}
