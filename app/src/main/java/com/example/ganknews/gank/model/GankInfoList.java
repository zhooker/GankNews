package com.example.ganknews.gank.model;

import java.util.List;

/**
 * Created by zhooker on 2016/10/13.
 */

public class GankInfoList {
    private boolean error;
    private List<GankInfo> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<GankInfo> getResults() {
        return results;
    }

    public void setResults(List<GankInfo> results) {
        this.results = results;
    }
}
