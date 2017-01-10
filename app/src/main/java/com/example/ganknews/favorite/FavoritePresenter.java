package com.example.ganknews.favorite;

import android.content.Context;

import com.example.ganknews.App;
import com.example.ganknews.base.BasePresenter;
import com.example.ganknews.db.DaoManager;
import com.example.ganknews.db.DaoSession;
import com.example.ganknews.db.GankInfoDao;
import com.example.ganknews.gank.http.HttpHelper;
import com.example.ganknews.gank.model.GankInfo;
import com.example.ganknews.gank.model.GankInfoList;
import com.example.ganknews.gank.presenter.GankGirlContacts;
import com.example.ganknews.util.L;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
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
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        GankInfoDao noteDao = daoSession.getGankInfoDao();
        List<GankInfo> users = noteDao.loadAll();
        mView.refreshList(users);
    }
}
