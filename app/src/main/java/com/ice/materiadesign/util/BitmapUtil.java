package com.ice.materiadesign.util;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.ice.materiadesign.R;

/**
 * Created by asd on 1/8/2017.
 */

public class BitmapUtil {
    private LruCache<String, Bitmap> mMemoryCache;
    private Activity activity;

    public BitmapUtil(Activity activity){
        this.activity = activity;
        mMemoryCache = new LruCache<String,Bitmap>(1024*8*1024*18){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };
    }

    /**
     * 计算出缩放比例
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                              int reqWidth, int reqHeight){
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if(reqHeight<height || reqWidth < width){
            // 计算出实际宽高和目标宽高的比率  round是四舍五入取整函数
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,int reqWidth,int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(res,resId,options);

        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);

        options.inJustDecodeBounds= false;

        return BitmapFactory.decodeResource(res,resId,options);

    }
    //从缓存LruCache中取
    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }
    //添加到LruCache中
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * 调用此函数加载图片
     * @param resId
     * @param imageView
     */
    public void loadBitmap(int resId, ImageView imageView){
        final String imageKey = String.valueOf(resId);
        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.mipmap.ic_launcher);
            BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            task.execute(resId);
        }
    }

    /**
     * 后台加载图片
     */
    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        ImageView imageView;
        public BitmapWorkerTask(ImageView imageView) {
            this.imageView = imageView;
        }


        // 在后台加载图片。
        @Override
        protected Bitmap doInBackground(Integer... params) {
            final Bitmap bitmap = decodeSampledBitmapFromResource(
                    activity.getResources(), params[0], 100, 100);
            addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
