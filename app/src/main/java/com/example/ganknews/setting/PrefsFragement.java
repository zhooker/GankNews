package com.example.ganknews.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;

import com.example.ganknews.R;
import com.example.ganknews.util.L;

/**
 * Created by zhuangsj on 16-10-14.
 */

public class PrefsFragement extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String TAG = PrefsFragement.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Preference claeraCache = getPreferenceScreen().findPreference("settings_clearcache");
        claeraCache.setSummary("300Kb");
        claeraCache.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                L.d(preference.getTitle());
                preference.setSummary("0Kb");
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
}