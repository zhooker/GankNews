package com.example.ganknews.base;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import com.example.ganknews.util.L;

/**
 * Created by zhooker on 2016/10/8.
 */

public abstract class BaseFragment extends Fragment {

    public static final String VISIBILITY = "FRAGMENT_VISIBILITY";

    protected boolean isLoaded1 = false;
    protected boolean isPrepraed = false;
    protected boolean isVisible = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            L.d(null);
        } else if (getParentFragment() == null) {
            L.d(savedInstanceState.getBoolean(VISIBILITY));
            if (!savedInstanceState.getBoolean(VISIBILITY))
                getFragmentManager().beginTransaction().hide(this).commitAllowingStateLoss();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        L.d("isLoaded1:" + isLoaded1 + ",isPrepraed:" + isPrepraed + ",isVisibleToUser=" + isVisibleToUser);
        if (!isLoaded1 && isVisibleToUser && isPrepraed) {
            loadData();
            isLoaded1 = true;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isVisible = !hidden;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepraed = true;
        L.d("isVisible:" + isVisible + ",isLoaded1:" + isLoaded1);
        if (isVisible && !isLoaded1) {
            loadData();
            isLoaded1 = true;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(VISIBILITY, isVisible && !isDetached());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getParentFragment() == null) {
            isLoaded1 = false;
            isVisible = false;
        }
    }

    public boolean isLoaded(){
        return isLoaded1;
    }

    protected void reset() {
        this.isLoaded1 = false;
    }

    protected abstract void loadData();
}
