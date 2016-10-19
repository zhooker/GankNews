package com.example.ganknews.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
        recyclerView.setLayoutManager(getLayoutManager(getActivity()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean isScrollingEnd = false;

            /**
             * loadMore的两个重点：
             * 1. 如何检测到已经滑到最后一项
             * 2. 如何不要重复加载
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (isRefreshing())
                    isScrollingEnd = false;
                else {
                    int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    int lastVisibleItemPosition = totalItemCount;
                    if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                        lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                                .findLastVisibleItemPosition();
                    } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                        int[] array = ((StaggeredGridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPositions(null);
                        if (array != null)
                            lastVisibleItemPosition = array[array.length - 1];
                    }

                    if (lastVisibleItemPosition >= totalItemCount - 1) {
                        if (newState == RecyclerView.SCROLL_STATE_SETTLING)
                            isScrollingEnd = true;
                        if (newState == RecyclerView.SCROLL_STATE_IDLE && isScrollingEnd) {
                            isScrollingEnd = false;
                            if (lastVisibleItemPosition >= totalItemCount - 1) {
                                setRefresh(true);
                                loadMoreData();
                            }
                        }
                    }
                }
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
//        mSwitcher.setDisplayedChild(1);
    }

    protected void showContent() {
//        if (mSwitcher.getDisplayedChild() != 0)
//            mSwitcher.setDisplayedChild(0);
    }

    protected void loadData() {

    }

    protected void loadMoreData() {

    }

    protected RecyclerView.LayoutManager getLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    protected boolean isRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
    }

    protected void setRefresh(boolean refresh) {
        mSwipeRefreshLayout.setRefreshing(refresh);
    }

    protected abstract T initPresenter();
}
