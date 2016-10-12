package com.example.ganknews.gank.http;

import android.content.Context;

/**
 * Created by zhooker on 2016/10/12.
 */

public class HttpHelper {
    private static HttpHelper mHttpHelper;

    private HttpHelper() {

    }

    public static HttpHelper getInstance(Context context) {
        if (context == null) {
            synchronized (HttpHelper.class) {
                if (mHttpHelper == null) {
                    mHttpHelper = new HttpHelper();
                }
            }
        }
        return mHttpHelper;
    }
}
