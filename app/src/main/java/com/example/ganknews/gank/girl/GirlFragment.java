package com.example.ganknews.gank.girl;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.ganknews.R;
import com.example.ganknews.base.BaseRefreshFragment;
import com.example.ganknews.base.BindingListAdapter;
import com.example.ganknews.base.ItemClickSupport;
import com.example.ganknews.databinding.FragmentGirlItemBinding;
import com.example.ganknews.gank.model.GankInfo;
import com.example.ganknews.gank.presenter.GankGirlContacts;
import com.example.ganknews.gank.presenter.GankGirlPresenter;
import com.example.ganknews.gank.presenter.GankPresenter;
import com.example.ganknews.gank.ui.AndroidFragment;
import com.example.ganknews.gank.ui.GankDetailActivity;
import com.example.ganknews.util.L;

import java.util.List;

/**
 * Created by zhuangsj on 16-10-13.
 */

public class GirlFragment extends BaseRefreshFragment<GankGirlPresenter> implements GankGirlContacts.IGankView {

    public static final String TAG = GirlFragment.class.getSimpleName();
    private HomeAdapter mAdapter;

    @Override
    protected void initRecyclerView(RecyclerView recyclerView) {
        super.initRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter = new HomeAdapter());
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                L.d(position);

                Intent intent = new Intent();
                intent.putExtra(GirlDetailActivity.LOAD_URL,
                        ((GirlFragment.HomeAdapter) recyclerView.getAdapter()).getItem(position).getUrl());
                intent.setClass(getActivity(), GirlDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager(Context context) {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    @Override
    protected void loadData() {
        mPresenter.loadData();
    }

    @Override
    protected void loadMoreData() {
        mPresenter.loadMoreData();
    }


    @Override
    protected GankGirlPresenter initPresenter() {
        return new GankGirlPresenter(getActivity());
    }

    @Override
    public void refreshList(List<GankInfo> list) {
        showContent();
        mAdapter.clear();
        for (GankInfo info : list) {
            mAdapter.add(info);
        }
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

    static class HomeAdapter extends BindingListAdapter<GankInfo, FragmentGirlItemBinding> {

        @Override
        protected int getLayoutRes() {
            return R.layout.fragment_girl_item;
        }

        @Override
        protected void onBindViewHolder(final FragmentGirlItemBinding binding, GankInfo data) {
            final ViewGroup.LayoutParams layoutParams = binding.itemContainer.getLayoutParams();
            Glide.with(binding.itemContainer.getContext()).load(data.getUrl())
                    .fitCenter()
                    .placeholder(R.mipmap.ic_launcher)
                    .crossFade()
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            ViewGroup parent = (ViewGroup) binding.itemContainer.getParent();
                            layoutParams.width = parent != null ? parent.getWidth() / 2 : 500;//esource.getIntrinsicWidth();
                            layoutParams.height = (int) (resource.getIntrinsicHeight() * layoutParams.width * 1.0f / resource.getIntrinsicWidth());
                            L.d(layoutParams.height + "," + resource.getIntrinsicHeight() + ",width=" + layoutParams.width);
                            binding.icon.setImageDrawable(resource);
                        }
                    });

            //binding.setInfo(data);
        }
    }

}
