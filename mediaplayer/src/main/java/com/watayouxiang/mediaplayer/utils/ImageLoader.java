package com.watayouxiang.mediaplayer.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 简单的图片加载器
 */
public class ImageLoader {
    private LoadImgHandler mLoadImgHandler;

    public ImageLoader(ImageView imageView) {
        mLoadImgHandler = new LoadImgHandler(imageView);
    }

    /**
     * 异步加载图片
     *
     * @param url 图片地址
     */
    public void loadAsync(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = getBitmap(url);
                Message msg = mLoadImgHandler.obtainMessage();
                msg.obj = bitmap;
                mLoadImgHandler.sendMessage(msg);
            }
        }).start();
    }

    private Bitmap getBitmap(String url) {
        Bitmap bitmap = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            URL imgUrl = new URL(url);
            conn = (HttpURLConnection) imgUrl
                    .openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return bitmap;
    }

    private static class LoadImgHandler extends Handler {
        private WeakReference<ImageView> imageViewWeakReference;

        LoadImgHandler(ImageView imageView) {
            imageViewWeakReference = new WeakReference<>(imageView);
        }

        @Override
        public void handleMessage(Message msg) {
            ImageView imageView = imageViewWeakReference.get();
            if (imageView == null) {
                return;
            }
            Bitmap bitmap = (Bitmap) msg.obj;
            imageView.setImageBitmap(bitmap);
            super.handleMessage(msg);
        }
    }
}
