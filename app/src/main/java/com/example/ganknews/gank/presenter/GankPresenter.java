package com.example.ganknews.gank.presenter;

import com.example.ganknews.gank.http.HttpHelper;
import com.example.ganknews.gank.model.GankInfo;
import com.example.ganknews.gank.model.GankInfoList;
import com.example.ganknews.util.L;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhooker on 2016/10/11.
 */

public class GankPresenter implements GankContacts.IGankPresenter {

    private GankContacts.IGankView viewModle;

    @Override
    public void bindView(GankContacts.IGankView view) {
        viewModle = view;
    }

    @Override
    public void loadData() {
        Call<GankInfoList> call = HttpHelper.getInstance(null).getGankInfoListCall("Android", 10, 1);
        call.enqueue(new Callback<GankInfoList>() {
            @Override
            public void onResponse(Call<GankInfoList> call, Response<GankInfoList> response) {
                viewModle.refreshList(response.body() != null ? response.body().getResults() : null);
            }

            @Override
            public void onFailure(Call<GankInfoList> call, Throwable t) {
                L.d(t);
            }
        });
    }


    @Override
    public void loadMoreData() {

    }
}
