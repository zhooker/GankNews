package com.example.ganknews.setting;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.View;
import android.widget.ListView;

import com.example.ganknews.R;
import com.example.ganknews.util.Constant;
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
            FileUtil.deleteFiles(Constant.CACHE_DIR + File.separator + Constant.IMAGE_CACHE);
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            this.preference.setSummary("0kb");
        }
    }
}