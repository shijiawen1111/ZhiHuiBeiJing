package com.ttit.zhihuibeijing.utils.bitmap;

import android.content.Context;

import com.ttit.zhihuibeijing.utils.MD5Utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by JW.S on 2020/10/10 1:01 AM.
 */
public final class CacheUtils {
    //缓存json
    public static void saveCache(Context context, String url, String json) throws Exception {
        //文件名
        String name = MD5Utils.encode(url);
        //输出流
        FileOutputStream fileOutputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
        //写数据
        fileOutputStream.write(json.getBytes());
        //关流
        fileOutputStream.close();
    }

    //读取json
    public static String readCache(Context context, String url) throws Exception {
        //文件名
        String name = MD5Utils.encode(url);
        //输入流
        FileInputStream fileInputStream = context.openFileInput(name);
        //字节数据输出流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        //循环读取
        while ((len = fileInputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        //关闭流
        String json = bos.toString();
        bos.close();
        fileInputStream.close();
        return json;
    }
}
