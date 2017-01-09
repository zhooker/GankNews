package com.example.ganknews.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ganknews.R;
import com.example.ganknews.base.BaseFragment;

/**
 * Created by zhuangsj on 16-10-14.
 */

public class AboutFragment extends BaseFragment {

    public static final String TAG = AboutFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View base = inflater.inflate(R.layout.fragment_about, null);
        return base;
    }

    @Override
    protected void loadData() {

    }
}
