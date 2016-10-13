package com.example.ganknews.gank.presenter;

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

public class GankGirlPresenter implements GankGirlContacts.IGankPresenter {

    private GankGirlContacts.IGankView viewModle;
    private CompositeSubscription compositeSubscription;

    public GankGirlPresenter() {
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void bindView(GankGirlContacts.IGankView view) {
        viewModle = view;
    }

    @Override
    public void loadData() {
        Observable<GankInfoList> call = HttpHelper.getInstance(null).getGankInfoListCall("福利", 10, 1);
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
                        viewModle.refreshList(user != null ? user.getResults() : null);
                    }
                });
        compositeSubscription.add(subscription);
    }

    @Override
    public void finish() {
        compositeSubscription.unsubscribe();
    }
}
