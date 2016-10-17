package com.example.ganknews.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhuangsj on 16-10-17.
 */

public abstract class BasePresenter<T extends BaseView> {

    protected T mView;
    private CompositeSubscription compositeSubscription;

    public void attachView(T view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
        unsubscribe();
    }

    private void assertCompositeSubscription() {
        if (compositeSubscription == null)
            compositeSubscription = new CompositeSubscription();
    }

    protected void addSubscriiption(Subscription subscription) {
        assertCompositeSubscription();
        compositeSubscription.add(subscription);
    }

    protected void unsubscribe() {
        assertCompositeSubscription();
        compositeSubscription.unsubscribe();
    }
}
