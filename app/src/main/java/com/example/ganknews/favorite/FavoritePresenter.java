package com.example.ganknews.favorite;

import android.content.Context;

import com.example.ganknews.base.BasePresenter;
import com.example.ganknews.db.DaoManager;
import com.example.ganknews.db.DaoSession;
import com.example.ganknews.db.GankInfoDao;
import com.example.ganknews.favorite.presenter.FavoriteContacts;
import com.example.ganknews.gank.model.GankInfo;

import java.util.List;

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
