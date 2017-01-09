package com.example.ganknews.gank.presenter;

import android.content.Context;

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
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhooker on 2016/10/11.
 */

public class GankPresenter extends BasePresenter<GankContacts.IGankView> implements GankContacts.IGankPresenter {

    private int currPage = 1;

    public GankPresenter(Context context) {
        super(context);
    }

    @Override
    public void loadData() {
        currPage = 1;
        loadDataByPage(currPage, false);
    }


    @Override
    public void loadMoreData() {
        loadDataByPage(++currPage, true);
    }

    private void loadDataByPage(final int page, final boolean addLast) {
        Observable<GankInfoList> call = HttpHelper.getInstance(null).getGankInfoListCall(mContext, "Android", 10, page);
        Subscription subscription = call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<GankInfoList, Observable<List<GankInfo>>>() {
                    @Override
                    public Observable<List<GankInfo>> call(GankInfoList gankInfoList) {
                        if(gankInfoList.isError()) {

                        }
                        return Observable.just(gankInfoList.getResults());
                    }
                })
                .subscribe(new Subscriber<List<GankInfo>>() {
                    @Override
                    public void onCompleted() {
                        L.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.d(e);
                        mView.showError(-1);
                    }

                    @Override
                    public void onNext(List<GankInfo> user) {
                        if (addLast)
                            mView.refreshMoreList(user != null ? user: null);
                        else
                            mView.refreshList(user != null ? user: null);
                    }
                });
        addSubscriiption(subscription);
    }
}
