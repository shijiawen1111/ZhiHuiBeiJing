package com.ttit.zhihuibeijing.utils.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.ttit.zhihuibeijing.utils.MyLogger;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JW.S on 2020/10/11 9:10 AM.
 */
public class NetCacheUtils {
    private final static String TAG = "NetCacheUtils";
    private Context context;

    //从网络获取图片
    public AsyncTask<Object, Void, Bitmap> getBitmapFromNet(Context context, ImageView iv, String url) {
        this.context = context;
        //异步操作
        return new BitmapTask().execute(iv, url);
    }

    //异步任务
    private class BitmapTask extends AsyncTask<Object, Void, Bitmap> {

        private ImageView iv;
        private String url;

        //在主线程中执行,第一步执行
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * 下载图片  在子线程中执行 第二步执行;
         * 第四步是在方法里回调当在 doInBackground()方法中调用 publishProgress()方法时，
         * 就会调用 onProgressUpdate()方法
         */
        @Override
        protected Bitmap doInBackground(Object... objects) {
            //获取参数
            iv = (ImageView) objects[0];
            url = (String) objects[1];
            //下载图片
            Bitmap bitmap = downLoadBitmap(url);
            MyLogger.i(TAG, "从网络上加载图片!");
             MemoryCacheUtils.saveCache(bitmap, url);
            return bitmap;
        }

        //在主线程中执行,第三步执行
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //获取ImageView对应的url
            String url = (String) iv.getTag();
            if (bitmap != null && this.url.equals(url)) {
                iv.setImageBitmap(bitmap);
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    //下载图片
    private Bitmap downLoadBitmap(String url) {
        HttpURLConnection conn = null;
        InputStream is;
        Bitmap bitmap = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(6000);
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                is = conn.getInputStream();
                //把流转换成Bitmap对象
                bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null){
                conn.disconnect();
            }
        }
        return bitmap;
    }
}
