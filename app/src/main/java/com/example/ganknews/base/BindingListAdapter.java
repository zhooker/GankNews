package com.example.ganknews.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuangsj on 16-10-12.
 */

public abstract class BindingListAdapter<M, VB extends ViewDataBinding>
        extends RecyclerView.Adapter<BindingListAdapter.BindingHolder<VB>> {

    private List<M> mDatas;

    public BindingListAdapter() {
        this(new ArrayList<M>());
    }

    public BindingListAdapter(List<M> list) {
        if (list == null)
            throw new IllegalArgumentException("List could not be NULL!");
        this.mDatas = list;
    }

    public void addAll(List<M> list) {
        if (mDatas != null)
            mDatas.addAll(list);
    }

    public void addLast(List<M> list) {
        if (mDatas != null) {
            mDatas.addAll(mDatas.size(), list);
        }
    }

    public void add(M item) {
        if (mDatas != null)
            mDatas.add(item);
    }

    public void clear() {
        if (mDatas != null)
            mDatas.clear();
    }

    public M getItem(int position) {
        if (mDatas != null) {
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public BindingHolder<VB> onCreateViewHolder(ViewGroup parent, int viewType) {
        VB binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                getLayoutRes(),
                parent,
                false);
        return new BindingHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder<VB> holder, int position) {
        onBindViewHolder(holder.getBinding(), mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    protected abstract int getLayoutRes();

    protected abstract void onBindViewHolder(VB binding, M data);


    public static class BindingHolder<T extends ViewDataBinding>
            extends RecyclerView.ViewHolder {
        private T binding;

        public BindingHolder(T binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public T getBinding() {
            return binding;
        }
    }

}
