package com.example.ganknews.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ganknews.R;
import com.example.ganknews.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhooker on 2016/10/8.
 */

public class BaseFragment extends Fragment {

    public static final String GUID = "guid";
    private String guid;

    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;

    public static BaseFragment newInstance(String guid) {
        BaseFragment fragment = new BaseFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BaseFragment.GUID, guid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        guid = bundle.getString(GUID);
        L.d(guid);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.d(guid);
        View view = inflater.inflate(R.layout.fragment_base, null);
        //((TextView) view.findViewById(R.id.textView)).setText(guid);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initData();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());

    }

    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        L.d(guid + "," + hidden);
    }

    @Override
    public void onDetach() {
        L.d(guid);
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        L.d(guid);
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        L.d(guid);
        super.onAttach(context);
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

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

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }
    }
}
