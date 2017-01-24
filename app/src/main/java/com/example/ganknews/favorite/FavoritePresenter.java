package com.example.ganknews.favorite;

import android.content.Context;

import com.example.ganknews.base.BasePresenter;
import com.example.ganknews.db.DaoManager;
import com.example.ganknews.db.DaoSession;
import com.example.ganknews.db.GankInfoDao;
import com.example.ganknews.favorite.presenter.FavoriteContacts;
import com.example.ganknews.gank.model.GankInfo;

import java.util.List;
import java.util.Observable;

import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhooker on 2016/10/11.
 */

public class FavoritePresenter extends BasePresenter<FavoriteContacts.IFavoriteView>
        implements FavoriteContacts.IFavoritePresenter {

    public FavoritePresenter(Context context) {
        super(context);
    }

    @Override
    public void loadData() {
        rx.Observable.create(new rx.Observable.OnSubscribe<List<GankInfo>>() {

            @Override
            public void call(Subscriber<? super List<GankInfo>> subscriber) {
                DaoSession daoSession = DaoManager.getInstance().getDaoSession();
                GankInfoDao noteDao = daoSession.getGankInfoDao();
                try {
                    List<GankInfo> users = noteDao.queryBuilder().orderDesc(GankInfoDao.Properties.Id).list();//.loadAll();
                    subscriber.onNext(users);
                } catch (Exception e) {
                    subscriber.onError(e);
                } finally {
                    subscriber.onCompleted();
                }
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<GankInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<GankInfo> gankInfos) {
                mView.refreshList(gankInfos);
            }
        });
    }
}
