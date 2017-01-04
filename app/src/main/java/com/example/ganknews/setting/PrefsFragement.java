package com.example.ganknews.setting;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.View;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.ganknews.R;
import com.example.ganknews.base.BaseFragment;
import com.example.ganknews.util.Constant;
import com.example.ganknews.util.DayNightHelper;
import com.example.ganknews.util.FileUtil;
import com.example.ganknews.util.L;

import java.io.File;

/**
 * Created by zhuangsj on 16-10-14.
 */

public class PrefsFragement extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String TAG = PrefsFragement.class.getSimpleName();
    private Preference mClaeraCache;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            L.d(null);
        } else if (getParentFragment() == null) {
            L.d(savedInstanceState.getBoolean(BaseFragment.VISIBILITY));
            if (!savedInstanceState.getBoolean(BaseFragment.VISIBILITY))
                getFragmentManager().beginTransaction().hide(this).commitAllowingStateLoss();
        }

        addPreferencesFromResource(R.xml.preferences);

        mClaeraCache = getPreferenceScreen().findPreference("settings_clearcache");
        mClaeraCache.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                L.d(preference.getTitle());
                new ClearCacheTask(preference).execute();
                return false;
            }

        });

        Preference version = getPreferenceScreen().findPreference("settings_version");
        version.setSummary("1.0.0.0");
        version.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                L.d(preference.getTitle());
                return false;
            }

        });

        new CheckCacheTask(mClaeraCache).execute();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // remove dividers
        View rootView = getView();
        ListView list = (ListView) rootView.findViewById(android.R.id.list);
        list.setDivider(null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BaseFragment.VISIBILITY, isVisible());
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        L.d(key);
        if (key.equals(getString(R.string.setting_key_day_night))) {
            DayNightHelper.setNightMode(sharedPreferences.getBoolean(key, false));
            getActivity().recreate();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            new CheckCacheTask(mClaeraCache).execute();
        }
    }

    private class CheckCacheTask extends AsyncTask<Void,Void,String> {

        private Preference preference;

        public CheckCacheTask(Preference preference) {
            this.preference = preference;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.preference.setSummary("0kb");
        }

        @Override
        protected String doInBackground(Void... params) {
            long httpCache = FileUtil.getFileSize(
                    Constant.CACHE_DIR + File.separator + Constant.HTTP_CAHCE);
            long imageCache = FileUtil.getFileSize(
                    Constant.CACHE_DIR + File.separator + Constant.IMAGE_CACHE);
            return FileUtil.formatFileSize(httpCache + imageCache);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            this.preference.setSummary(s);
        }

    }

    private class ClearCacheTask extends AsyncTask<Void,Void,Void> {

        private Preference preference;

        public ClearCacheTask(Preference preference) {
            this.preference = preference;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            FileUtil.deleteFiles(Constant.CACHE_DIR + File.separator + Constant.HTTP_CAHCE);
            Glide.get(getActivity()).clearDiskCache();
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            this.preference.setSummary("0kb");
        }
    }
}