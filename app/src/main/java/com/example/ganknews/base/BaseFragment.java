package com.example.ganknews.base;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import com.example.ganknews.util.L;

/**
 * Created by zhooker on 2016/10/8.
 */

public abstract class BaseFragment extends Fragment {

    private boolean isLoaded = false;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isLoaded) {
            loadData();
            isLoaded = true;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isLoaded && !hidden) {
            loadData();
            isLoaded = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //isLoaded = false;
    }

    protected abstract void loadData();
}
