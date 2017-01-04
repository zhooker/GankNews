package com.example.ganknews;

import android.app.Application;

import com.example.ganknews.util.DayNightHelper;


/**
 * Created by zhuangsj on 16-10-13.
 */


public class App extends Application {
    public void onCreate() {
        DayNightHelper.setNightModeBySetting(this);
        super.onCreate();
    }
}