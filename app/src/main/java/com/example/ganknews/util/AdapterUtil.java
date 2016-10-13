package com.example.ganknews.util;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.ganknews.R;

/**
 * Created by zhuangsj on 16-10-13.
 * 这里是公共函数，主要用来设置Buton在不同状态时的背景图片。
 * 使用BindingAdapter时，函数一定要是static,第一个参数是对应的View类
 */

public class AdapterUtil {

    @BindingAdapter("update")
    public static void updateText(final ImageView btn, String url) {
        Glide.with(btn.getContext()).load(url)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(btn);
    }
}
