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
import com.example.ganknews.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuangsj on 16-10-9.
 */

public class GankFragment extends BaseFragment {

    public static final String TAG = GankFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View base = inflater.inflate(R.layout.fragment_gank, null);
        return base;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new AndroidFragment(), "Android");
        adapter.addFragment(new IOSFragment(), "IOS");
        adapter.addFragment(new WebFragment(), "Web");
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    protected void loadData() {

    }

    //FragmentPagerAdapter
    static class Adapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
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
