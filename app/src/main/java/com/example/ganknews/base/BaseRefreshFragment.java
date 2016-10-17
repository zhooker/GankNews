package com.example.ganknews.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

import com.example.ganknews.R;
import com.example.ganknews.util.L;

/**
 * Created by zhuangsj on 16-10-10.
 */

public abstract class BaseRefreshFragment<T extends BasePresenter> extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, BaseView {

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected ViewSwitcher mSwitcher;
    protected RecyclerView mRecyclerView;
    protected View mContent;
    protected T mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mPresenter == null)
            mPresenter = initPresenter();
        mPresenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mContent == null) {
            mContent = inflater.inflate(R.layout.fragment_base, null);
            mSwipeRefreshLayout = (SwipeRefreshLayout) mContent.findViewById(R.id.id_refreshlayout);
            mSwitcher = (ViewSwitcher) mContent.findViewById(R.id.switcher);
            mRecyclerView = (RecyclerView) mContent.findViewById(R.id.id_recyclerview);
            mSwipeRefreshLayout.setOnRefreshListener(this);
            initRecyclerView(mRecyclerView);
        }
        return mContent;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected void initRecyclerView(RecyclerView recyclerView) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                setRefresh(true);
                loadMoreData();
            }
        });
    }

    @Override
    public void onRefresh() {
        setRefresh(true);
        loadData();
    }

    @Override
    public void showError(int code) {
        setRefresh(false);
        mSwitcher.setDisplayedChild(1);
    }

    protected void loadData() {

    }

    protected void loadMoreData() {

    }

    protected boolean isRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
    }

    protected void setRefresh(boolean refresh) {
        mSwipeRefreshLayout.setRefreshing(refresh);
    }

    protected abstract T initPresenter();
}
