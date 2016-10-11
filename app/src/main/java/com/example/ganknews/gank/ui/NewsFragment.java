package com.example.ganknews.gank.ui;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ganknews.R;
import com.example.ganknews.base.BaseRefreshFragment;
import com.example.ganknews.gank.model.GankInfo;
import com.example.ganknews.gank.presenter.GankContacts;
import com.example.ganknews.gank.presenter.GankPresenter;
import com.example.ganknews.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuangsj on 16-10-10.
 */

public class NewsFragment extends BaseRefreshFragment implements GankContacts.IGankView {

    public static final String TAG = NewsFragment.class.getSimpleName();
    private GankPresenter mGankPresenter;
    private HomeAdapter mAdapter;
    private int count = 0;

    @Override
    protected void initRecyclerView(RecyclerView recyclerView) {
        super.initRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter = new HomeAdapter());
        mGankPresenter = new GankPresenter();
        mGankPresenter.bindView(this);
        mGankPresenter.loadData();
    }

    @Override
    protected void loadData(int page) {
        L.d(isRefreshing() + "---begin");
//        if (!isRefreshing()) {
//            setRefresh(true);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    L.d(isRefreshing() + "---end");
//                    mAdapter.getDatas().add("" + (count++));
//                    mAdapter.notifyDataSetChanged();
//                    setRefresh(false);
//                }
//            }, 1000);
//        }
    }

    @Override
    public void refreshList(List<GankInfo> list) {
        mAdapter.getDatas().clear();
        for (GankInfo info : list) {
            mAdapter.getDatas().add(info);
        }
        mAdapter.notifyDataSetChanged();
    }

    static class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        private List<GankInfo> mDatas;

        public HomeAdapter() {
            this(new ArrayList<GankInfo>());
        }

        public HomeAdapter(List<GankInfo> mDatas) {
            this.mDatas = mDatas;
        }

        public List<GankInfo> getDatas() {
            return mDatas;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.author.setText(mDatas.get(position).getAuthor());
            holder.describtion.setText(mDatas.get(position).getDescription());
            holder.date.setText(mDatas.get(position).getDate());
        }

        @Override
        public int getItemCount() {
            return mDatas == null ? 0 : mDatas.size();
        }

        static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView author;
            TextView date;
            TextView describtion;

            public MyViewHolder(View view) {
                super(view);
                author = (TextView) view.findViewById(R.id.author);
                date = (TextView) view.findViewById(R.id.date);
                describtion = (TextView) view.findViewById(R.id.describtion);
            }
        }
    }
}
