package com.example.ganknews.gank.http;

import com.example.ganknews.gank.model.GankInfo;
import com.example.ganknews.gank.model.GankInfoList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zhuangsj on 16-10-13.
 */

public interface GankApiInterface {
    @GET("data/{category}/{size}/{page}")
    Observable<GankInfoList> getCatagoryNews(@Path("category") String category, @Path("size") int size, @Path("page") int page);
}
