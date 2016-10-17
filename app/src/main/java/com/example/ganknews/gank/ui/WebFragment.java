package com.example.ganknews.gank.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ganknews.R;
import com.example.ganknews.base.BaseRefreshFragment;
import com.example.ganknews.base.BindingListAdapter;
import com.example.ganknews.databinding.FragmentListItemBinding;
import com.example.ganknews.gank.model.GankInfo;
import com.example.ganknews.gank.presenter.GankContacts;
import com.example.ganknews.gank.presenter.GankPresenter;
import com.example.ganknews.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuangsj on 16-10-10.
 */

public class WebFragment extends BaseRefreshFragment<GankPresenter>
        implements GankContacts.IGankView {

    public static final String TAG = WebFragment.class.getSimpleName();
    private AndroidFragment.HomeAdapter mAdapter;

    @Override
    protected void initRecyclerView(RecyclerView recyclerView) {
        super.initRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter = new AndroidFragment.HomeAdapter());
    }

    @Override
    protected GankPresenter initPresenter() {
        return new GankPresenter();
    }

    @Override
    public void refreshList(List<GankInfo> list) {
        mAdapter.clear();
        mAdapter.addAll(list);
        mAdapter.notifyDataSetChanged();
        setRefresh(false);
    }

    @Override
    public void refreshMoreList(List<GankInfo> list) {
        mAdapter.addLast(list);
        mAdapter.notifyDataSetChanged();
        setRefresh(false);
    }

    @Override
    protected void loadData() {
        L.d();
        mPresenter.loadData();
    }

    @Override
    protected void loadMoreData() {
        L.d();
        mPresenter.loadMoreData();
    }

    static class HomeAdapter extends BindingListAdapter<GankInfo, FragmentListItemBinding> {

        @Override
        protected int getLayoutRes() {
            return R.layout.fragment_list_item;
        }

        @Override
        protected void onBindViewHolder(FragmentListItemBinding binding, GankInfo data) {
            binding.setInfo(data);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        L.d();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        L.d();
    }

    @Override
    public void onDestroy() {
        L.d();
        super.onDestroy();
    }
}
