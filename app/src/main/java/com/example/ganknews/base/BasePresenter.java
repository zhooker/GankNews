package com.example.ganknews.base;

import android.content.Context;

import com.example.ganknews.util.L;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhuangsj on 16-10-17.
 */

public abstract class BasePresenter<T extends BaseView> {

    protected T mView;
    protected Context mContext;
    private CompositeSubscription compositeSubscription;

    public BasePresenter(Context context) {
        mContext = context;
    }

    public void attachView(T view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
        unsubscribe();
    }

    protected void addSubscriiption(Subscription subscription) {
        if (compositeSubscription == null)
            compositeSubscription = new CompositeSubscription();
        else
            compositeSubscription.clear();
        compositeSubscription.add(subscription);
    }

    protected void unsubscribe() {
        if (compositeSubscription != null) {
            //zhuangsj:unsubscribe之后，新add进来的Subscription会直接被unsubscribe
            compositeSubscription.unsubscribe();
            compositeSubscription = null;
        }
    }
}
