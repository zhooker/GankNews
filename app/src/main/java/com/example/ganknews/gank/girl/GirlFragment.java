package com.example.ganknews.gank.girl;

import android.support.v7.widget.RecyclerView;

import com.example.ganknews.R;
import com.example.ganknews.base.BaseRefreshFragment;
import com.example.ganknews.base.BindingListAdapter;
import com.example.ganknews.databinding.FragmentGirlItemBinding;
import com.example.ganknews.gank.model.GankInfo;
import com.example.ganknews.gank.presenter.GankGirlContacts;
import com.example.ganknews.gank.presenter.GankGirlPresenter;

import java.util.List;

/**
 * Created by zhuangsj on 16-10-13.
 */

public class GirlFragment extends BaseRefreshFragment implements GankGirlContacts.IGankView {

    public static final String TAG = GirlFragment.class.getSimpleName();

    private GankGirlPresenter mGankGirlPresenter;
    private HomeAdapter mAdapter;

    @Override
    protected void initRecyclerView(RecyclerView recyclerView) {
        super.initRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter = new HomeAdapter());
        mGankGirlPresenter = new GankGirlPresenter();
        mGankGirlPresenter.bindView(this);
    }

    @Override
    protected void loadData() {
        mGankGirlPresenter.loadData();
    }

    @Override
    public void refreshList(List<GankInfo> list) {
        mAdapter.clear();
        for (GankInfo info : list) {
            mAdapter.add(info);
        }
        mAdapter.notifyDataSetChanged();
    }

    static class HomeAdapter extends BindingListAdapter<GankInfo, FragmentGirlItemBinding> {

        @Override
        protected int getLayoutRes() {
            return R.layout.fragment_girl_item;
        }

        @Override
        protected void onBindViewHolder(FragmentGirlItemBinding binding, GankInfo data) {
            binding.setInfo(data);
        }
    }

}
