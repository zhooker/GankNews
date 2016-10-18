package com.example.ganknews.gank.presenter;

import android.content.Context;

import com.example.ganknews.base.BasePresenter;
import com.example.ganknews.gank.http.HttpHelper;
import com.example.ganknews.gank.model.GankInfoList;
import com.example.ganknews.util.L;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhooker on 2016/10/11.
 */

public class GankGirlPresenter extends BasePresenter<GankGirlContacts.IGankView>
        implements GankGirlContacts.IGankPresenter {

    private int currPage = 1;

    public GankGirlPresenter(Context context) {
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
        Observable<GankInfoList> call = HttpHelper.getInstance(null).getGankInfoListCall(mContext, "福利", 10, page);
        Subscription subscription = call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<GankInfoList>() {
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
                    public void onNext(GankInfoList user) {
                        if (addLast)
                            mView.refreshMoreList(user != null ? user.getResults() : null);
                        else
                            mView.refreshList(user != null ? user.getResults() : null);
                    }
                });
        addSubscriiption(subscription);
    }
}
