package com.example.ganknews.gank.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.example.ganknews.R;
import com.example.ganknews.base.BaseRefreshFragment;
import com.example.ganknews.base.BindingListAdapter;
import com.example.ganknews.base.ItemClickSupport;
import com.example.ganknews.databinding.FragmentListItemBinding;
import com.example.ganknews.gank.model.GankInfo;
import com.example.ganknews.gank.presenter.GankContacts;
import com.example.ganknews.gank.presenter.GankPresenter;
import com.example.ganknews.util.L;

import java.util.List;

/**
 * Created by zhuangsj on 16-10-10.
 */

public class AndroidFragment extends BaseRefreshFragment<GankPresenter>
        implements GankContacts.IGankView {

    public static final String TAG = AndroidFragment.class.getSimpleName();
    public static final String PARAM = "PARAMS";
    private HomeAdapter mAdapter;

    public static AndroidFragment newInstance(String args) {
        AndroidFragment result  = new AndroidFragment();
        if(!TextUtils.isEmpty(args)) {
            Bundle bundle = new Bundle();
            bundle.putString(PARAM, args);
            result.setArguments(bundle);
        }
        return result;
    }

    @Override
    protected GankPresenter initPresenter() {
        if (getArguments() != null) {
            return new GankPresenter(getActivity(),getArguments().getString(PARAM));
        }
        return new GankPresenter(getActivity());
    }

    @Override
    protected void initRecyclerView(RecyclerView recyclerView) {
        super.initRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter = new HomeAdapter());
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                GankInfo info = ((HomeAdapter) recyclerView.getAdapter()).getItem(position);
                L.d(position);
                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(), new Pair<>(v, "shared_element"));

                Intent intent = new Intent();
                intent.putExtra(GankDetailActivity.LOAD_URL, info);
                intent.setClass(getActivity(), GankDetailActivity.class);
                startActivity(intent, transitionActivityOptions.toBundle());
            }
        });
    }

    @Override
    public void refreshList(List<GankInfo> list) {
        showContent();
        mAdapter.clear();
        mAdapter.addAll(list);
        mAdapter.notifyDataSetChanged();
        setRefresh(false);
    }

    @Override
    public void refreshMoreList(List<GankInfo> list) {
        showContent();
        mAdapter.addLast(list);
        mAdapter.notifyDataSetChanged();
        setRefresh(false);
    }

    @Override
    protected void loadData() {
        L.d();
        super.loadData();
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
}
