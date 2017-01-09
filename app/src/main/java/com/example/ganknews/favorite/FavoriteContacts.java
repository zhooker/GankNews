package com.example.ganknews.favorite;

import com.example.ganknews.base.BasePresenter;
import com.example.ganknews.base.BaseView;
import com.example.ganknews.gank.model.GankInfo;

import java.util.List;

/**
 * Created by zhooker on 2016/10/11.
 */

public interface FavoriteContacts {
    interface IFavoriteView extends BaseView {
        void refreshList(List<GankInfo> list);
    }

    interface IFavoritePresenter {
        void loadData();
    }
}
