package com.example.ganknews.gank.model;

/**
 * Created by zhooker on 2017/1/15.
 */

public enum Tabs {
    Android("Android", "Android"),
    iOS("iOS", "iOS"),
    Web("Web", "前端"),
    Video("休息视频", "休息视频"),
    Expand("拓展资源", "拓展资源");

    final String display;
    final String param;

    Tabs(String display, String param) {
        this.display = display;
        this.param = param;
    }

    public String getDisplayName() {
        return this.display;
    }

    public String getParamString() {
        return this.param;
    }
}
