package com.example.ganknews.gank.presenter;

import com.example.ganknews.base.BasePresenter;
import com.example.ganknews.gank.http.HttpHelper;
import com.example.ganknews.gank.model.GankInfo;
import com.example.ganknews.gank.model.GankInfoList;
import com.example.ganknews.util.L;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhooker on 2016/10/11.
 */

public class GankPresenter extends BasePresenter<GankContacts.IGankView> implements GankContacts.IGankPresenter {

    @Override
    public void loadData() {
        Observable<GankInfoList> call = HttpHelper.getInstance(null).getGankInfoListCall("Android", 10, 1);
        Subscription subscription = call
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<GankInfoList>() {
                    @Override
                    public void onCompleted() {
                        L.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.d(e);
                    }

                    @Override
                    public void onNext(GankInfoList user) {
                        mView.refreshList(user != null ? user.getResults() : null);
                    }
                });
        addSubscriiption(subscription);
    }


    @Override
    public void loadMoreData() {

    }
}
