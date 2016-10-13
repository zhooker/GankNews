package com.example.ganknews.gank.presenter;

import com.example.ganknews.gank.model.GankInfo;

import java.util.List;

/**
 * Created by zhooker on 2016/10/11.
 */

public interface GankGirlContacts {
    interface IGankView {
        void refreshList(List<GankInfo> list);
    }

    interface IGankPresenter {
        void bindView(IGankView view);

        void loadData();

        void finish();
    }
}
