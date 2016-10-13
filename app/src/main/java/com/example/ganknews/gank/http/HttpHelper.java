package com.example.ganknews.gank.http;

import android.content.Context;

import com.example.ganknews.gank.model.GankInfoList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by zhooker on 2016/10/12.
 */

public class HttpHelper {

    private static final String BASE_URL = "http://gank.io/api/";
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

    public Observable<GankInfoList> getGankInfoListCall(String category, int size, int page) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // See http://square.github.io/okhttp/3.x/logging-interceptor/
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        GankApiInterface apiInterface = retrofit.create(GankApiInterface.class);
        return apiInterface.getCatagoryNews(category, size, page);
    }
}
