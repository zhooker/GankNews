package com.example.ganknews.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDelegate;

import com.example.ganknews.R;

/**
 * 夜景模式相关操作函数
 * Created by zhuangsj on 17-1-4.
 */

public class DayNightHelper {

    public static boolean checkIfSystemNightMode(Context context) {
        int mode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return mode == Configuration.UI_MODE_NIGHT_YES;
    }

    public static boolean checkIfNightMode(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(
                context.getResources().getString(R.string.setting_key_day_night), false);
    }

    public static void setNightMode(boolean isNight) {
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public static void setNightModeBySetting(Context context) {
        boolean isCheck = DayNightHelper.checkIfNightMode(context);
        if (checkIfSystemNightMode(context) != isCheck)
            DayNightHelper.setNightMode(isCheck);
    }
}
