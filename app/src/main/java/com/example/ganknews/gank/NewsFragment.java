package com.example.ganknews.gank;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ganknews.R;
import com.example.ganknews.base.BaseRefreshFragment;
import com.example.ganknews.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuangsj on 16-10-10.
 */

public class NewsFragment extends BaseRefreshFragment {

    public static final String TAG = NewsFragment.class.getSimpleName();
    private HomeAdapter mAdapter;
    private int count = 0;

    @Override
    protected void initRecyclerView(RecyclerView recyclerView) {
        super.initRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter = new HomeAdapter(getData()));
    }

    protected List<String> getData() {
        List<String> mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'Z'; i++) {
            mDatas.add("" + (char) i);
        }
        return mDatas;
    }

    @Override
    protected void loadData(int page) {
        L.d(isRefreshing() +"---begin");
        if (!isRefreshing()) {
            setRefresh(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    L.d(isRefreshing()+"---end");
                    mAdapter.getDatas().add("" + (count++));
                    mAdapter.notifyDataSetChanged();
                    setRefresh(false);
                }
            }, 1000);
        }
    }

    static class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        private List<String> mDatas;

        public HomeAdapter(List<String> mDatas) {
            this.mDatas = mDatas;
        }

        public List<String> getDatas() {
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
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }
    }
}