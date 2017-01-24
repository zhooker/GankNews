package com.example.ganknews.favorite;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.ganknews.R;
import com.example.ganknews.base.BaseRefreshFragment;
import com.example.ganknews.base.BindingListAdapter;
import com.example.ganknews.base.ItemClickSupport;
import com.example.ganknews.databinding.FragmentListItemBinding;
import com.example.ganknews.favorite.presenter.FavoriteContacts;
import com.example.ganknews.gank.model.GankInfo;
import com.example.ganknews.gank.ui.GankDetailActivity;
import com.example.ganknews.util.L;

import java.util.List;

/**
 * Created by zhuangsj on 17-1-9.
 */

public class FavoriteFragment extends BaseRefreshFragment<FavoritePresenter>
        implements FavoriteContacts.IFavoriteView {

    private HomeAdapter mAdapter;

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
    protected FavoritePresenter initPresenter() {
        return new FavoritePresenter(getActivity());
    }

    @Override
    public void refreshList(List<GankInfo> list) {
        mAdapter.clear();
        mAdapter.addAll(list);
        mAdapter.notifyDataSetChanged();
        loadDataResult(true);
    }

    @Override
    protected void loadData() {
        mPresenter.loadData();
    }

    @Override
    protected void loadMoreData() {
        setRefresh(false);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            loadData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isVisible())
            loadData();
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