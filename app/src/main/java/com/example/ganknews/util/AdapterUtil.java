package com.example.ganknews.util;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.ganknews.R;

/**
 * Created by zhuangsj on 16-10-13.
 */

public class AdapterUtil {


    /**
     * × 异步加载图片，算是一个中间类，方便以后修改图片加载库。
     *
     * @param btn
     * @param url
     */
    @BindingAdapter("update")
    public static void updateText(final ImageView btn, String url) {
        Glide.with(btn.getContext()).load(url)
                .fitCenter()
                .error(R.mipmap.ic_launcher)
                .into(btn);
    }

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }
}
