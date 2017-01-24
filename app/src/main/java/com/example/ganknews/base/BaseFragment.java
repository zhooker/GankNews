package com.example.ganknews.base;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ganknews.util.L;

/**
 * Created by zhooker on 2016/10/8.
 */

public abstract class BaseFragment extends Fragment {

    public static final String VISIBILITY = "FRAGMENT_VISIBILITY";

    protected boolean isLoaded = false;
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
        L("" + savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        L("" + isVisibleToUser);
        if (!isLoaded && isVisibleToUser && isPrepraed) {
            readyToloadData();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isVisible = !hidden;
        L("" + hidden);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepraed = true;
        if (isVisible && !isLoaded) {
            readyToloadData();
        }
        L("");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        L("isVisible=" + isVisible + "，isDetached=" + isDetached());
        outState.putBoolean(VISIBILITY, isVisible && !isDetached());
    }

    @Override
    public void onPause() {
        super.onPause();
        L("");
    }

    @Override
    public void onStop() {
        super.onStop();
        L("");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        L("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L("");
    }

    @Override
    public void onResume() {
        super.onResume();
        L("");
    }

    @Override
    public void onStart() {
        super.onStart();
        L("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L("getParentFragment=" + getParentFragment());
        if (getParentFragment() == null) {
            isPrepraed = false;
            isLoaded = false;
            isVisible = false;
        }
    }

    protected void readyToloadData() {
        loadData();
    }

    protected void loadData() {
        loadDataResult(true);
    }

    protected void loadDataResult(boolean result) {
        isLoaded = result;
    }

    protected void L(String log) {
        final String tag = getClass().getSimpleName();
        Log.d(L.TAG, tag + "  :  " + log);
    }
}
