package com.example.ganknews.gank.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ganknews.R;
import com.example.ganknews.base.BaseFragment;
import com.example.ganknews.gank.model.Tabs;
import com.example.ganknews.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuangsj on 16-10-9.
 */

public class GankFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = GankFragment.class.getSimpleName();
    private ArrayList<Tabs> tabList = new ArrayList<>();
    private Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View base = inflater.inflate(R.layout.fragment_gank, null);
        base.findViewById(R.id.addTabs).setOnClickListener(this);
        return base;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mAdapter = new Adapter(getChildFragmentManager());;
        viewPager.setAdapter(mAdapter);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabList.add(Tabs.Android);
        tabList.add(Tabs.iOS);
        tabList.add(Tabs.Web);

        refreshTabs(tabList);
    }


    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.addTabs:
                // todo 添加tabs编辑界面
                if (v.getRotation() % 90 == 0) {
                    v.animate().rotationBy(45).start();
                } else {
                    v.animate().rotationBy(-45).start();
                    tabList.add(Tabs.Video);
                    refreshTabs(tabList);
                }
                break;
        }
    }

    private void refreshTabs(List<Tabs> list) {
        mAdapter.reset();
        for (Tabs tab : list) {
            mAdapter.addFragment(AndroidFragment.newInstance(tab.getParamString()), tab.getDisplayName());
        }
        mAdapter.notifyDataSetChanged();
    }

    //FragmentPagerAdapter
    static class Adapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void reset() {
            mFragments.clear();
            mFragmentTitles.clear();
        }

        public void addFragment(android.app.Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
