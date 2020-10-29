package com.ttit.zhihuibeijing.utils.bitmap;

import android.content.Context;

/**
 * Created by JW.S on 2020/10/14 11:27 AM.
 * 像素(px)和设备独立像素(dip<=>dp)的类：
 * 1.求像素的公式为:
 * px = dp * 屏幕像素(density) + 0.5f;
 * dp = px(像素)/屏幕密度(density) + 0.5f;
 */
public class Dp2PxUtils {
    //求像素的方法
    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dipValue + 0.5f);
    }
    //求设备独立像素的方法
    public static int px2dip(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue / scale + 0.5f);
    }
}
