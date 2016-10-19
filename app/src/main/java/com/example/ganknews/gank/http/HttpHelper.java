package com.example.ganknews.gank.http;

import android.content.Context;

import com.example.ganknews.App;
import com.example.ganknews.gank.model.GankInfoList;
import com.example.ganknews.util.AdapterUtil;
import com.example.ganknews.util.L;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

import static android.R.attr.path;

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

    public Observable<GankInfoList> getGankInfoListCall(final Context context, String category, int size, int page) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(initHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        GankApiInterface apiInterface = retrofit.create(GankApiInterface.class);
        return apiInterface.getCatagoryNews(category, size, page);
    }

    public OkHttpClient initHttpClient(final Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        File httpCacheDirectory = new File("/storage/emulated/0", "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        builder.cache(cache);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                L.d(request.url());
                if (!AdapterUtil.isNetworkReachable(context)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .url(request.url()).build();
                }

                Response response = chain.proceed(request);
                if (AdapterUtil.isNetworkReachable(context)) {
                    int maxAge = 60 * 60; // read from cache for 1 minute
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
                return response;
            }
        };
        builder.addInterceptor(interceptor);

        OkHttpClient client = builder.build();
        return client;
    }
}
