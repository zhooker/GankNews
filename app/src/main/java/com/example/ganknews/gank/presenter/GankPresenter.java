package com.example.ganknews.gank.presenter;

import com.example.ganknews.gank.model.GankInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhooker on 2016/10/11.
 */

public class GankPresenter implements GankContacts.IGankPresenter {

    private GankContacts.IGankView viewModle;

    @Override
    public void bindView(GankContacts.IGankView view) {
        viewModle = view;
    }

    @Override
    public void loadData() {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewModle.refreshList(getData());
            }
        }, 2000);
    }

    protected List<GankInfo> getData() {
        List<GankInfo> mDatas = new ArrayList<GankInfo>();
        for (int i = 'A'; i < 'Z'; i++) {
            GankInfo info = new GankInfo();
            info.setAuthor("" + (char) i);
            info.setDate("");
            StringBuilder sb = new StringBuilder();
            for (int j = 'A'; j < 'Z'; j++) {
                sb.append((char) j);
            }
            info.setDescription(sb.toString());
            mDatas.add(info);
        }
        return mDatas;
    }


    @Override
    public void loadMoreData() {

    }
}
