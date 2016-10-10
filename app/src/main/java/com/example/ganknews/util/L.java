package com.example.ganknews.util;

import android.util.Log;

/**
 * Created by zhooker on 2016/10/8.
 */

public class L {

    public static final String TAG = "zhuangsj";

    public static void d() {
        Log.d(TAG, getTraceInfo());
    }

    public static void d(Object message) {
        Log.d(TAG, getTraceInfo() + "=>" + message);
    }

    private static String getTraceInfo() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        String clazz = stacktrace[4].getClassName();
        return clazz.substring(clazz.lastIndexOf(".") + 1) + "->" + stacktrace[4].getMethodName();
    }

    private static String getTraceInfo(Exception e) {
        return line(e) + ":" + fun(e) + "->";
    }

    private static int line(Exception e) {
        StackTraceElement[] trace = e.getStackTrace();
        if (trace == null || trace.length == 0)
            return -1; //
        return trace[0].getLineNumber();
    }

    private static String fun(Exception e) {
        StackTraceElement[] trace = e.getStackTrace();
        if (trace == null)
            return ""; //
        return trace[0].getMethodName();
    }
}
