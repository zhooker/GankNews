package com.example.ganknews.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ganknews.R;
import com.example.ganknews.util.L;

/**
 * Created by zhuangsj on 16-10-10.
 */

public class BaseRefreshFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected View mContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mContent == null) {
            mContent = inflater.inflate(R.layout.fragment_base, null);
            mSwipeRefreshLayout = (SwipeRefreshLayout) mContent.findViewById(R.id.id_refreshlayout);
            mRecyclerView = (RecyclerView) mContent.findViewById(R.id.id_recyclerview);
            mSwipeRefreshLayout.setOnRefreshListener(this);
            initRecyclerView(mRecyclerView);
        }
        return mContent;
    }

    protected void initRecyclerView(RecyclerView recyclerView) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadData(page);
            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setRefresh(false);
            }
        }, 0);
    }

    protected void loadData() {

    }

    protected void loadData(int page) {

    }

    protected boolean isRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
    }

    protected void setRefresh(boolean refresh) {
        mSwipeRefreshLayout.setRefreshing(refresh);
    }
}
