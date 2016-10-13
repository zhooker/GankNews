package com.example.ganknews.gank.http;

import android.content.Context;

import com.example.ganknews.gank.model.GankInfoList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhooker on 2016/10/12.
 */

public class HttpHelper {

    private static final String BASE_URL = "http://gank.io/api/";
    private static HttpHelper mHttpHelper;

    private Retrofit retrofit;

    private HttpHelper() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
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

    public Call<GankInfoList> getGankInfoListCall(String category, int size, int page) {
        GankApiInterface apiInterface = retrofit.create(GankApiInterface.class);
        return apiInterface.getCatagoryNews(category, size, page);
    }
}
