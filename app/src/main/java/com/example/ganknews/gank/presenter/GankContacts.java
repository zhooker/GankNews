package com.example.ganknews.gank.presenter;

import com.example.ganknews.base.BaseView;
import com.example.ganknews.gank.model.GankInfo;

import java.util.List;

/**
 * Created by zhooker on 2016/10/11.
 */

public interface GankContacts {
    interface IGankView extends BaseView {
        void refreshList(List<GankInfo> list);
    }

    interface IGankPresenter {
        void loadData();

        void loadMoreData();
    }
}
