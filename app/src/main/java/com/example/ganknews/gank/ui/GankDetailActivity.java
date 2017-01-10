package com.example.ganknews.gank.ui;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.ganknews.App;
import com.example.ganknews.R;
import com.example.ganknews.db.DaoManager;
import com.example.ganknews.db.DaoSession;
import com.example.ganknews.db.GankInfoDao;
import com.example.ganknews.gank.model.GankInfo;
import com.example.ganknews.util.L;

import org.greenrobot.greendao.query.DeleteQuery;

import java.util.List;

public class GankDetailActivity extends AppCompatActivity {

    public static final String LOAD_URL = "web_load_url";
    private WebView mWebView;
    private GankInfo mGankInfo;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGankInfo = (GankInfo) getIntent().getParcelableExtra(LOAD_URL);

        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        final GankInfoDao noteDao = daoSession.getGankInfoDao();

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<GankInfo> infos = noteDao.queryBuilder().where(GankInfoDao.Properties.Guid.eq(mGankInfo.getGuid())).list();
                if(infos != null && infos.size() > 0) {
                    noteDao.queryBuilder().where(GankInfoDao.Properties.Guid.eq(mGankInfo.getGuid())).buildDelete().executeDeleteWithoutDetachingEntities();
                    Snackbar.make(view, "已删除～", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    changeFloatingActionButtonColor(false);
                } else {
                    noteDao.insertOrReplace(mGankInfo);
                    Snackbar.make(view, "已收藏～", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    changeFloatingActionButtonColor(true);
                }
            }
        });

        List<GankInfo> infos = noteDao.queryBuilder().where(GankInfoDao.Properties.Guid.eq(mGankInfo.getGuid())).list();
        changeFloatingActionButtonColor(infos != null && infos.size() > 0);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mWebView = (WebView) findViewById(R.id.webview);
        initWebView(mGankInfo);
    }

    private void changeFloatingActionButtonColor(boolean isSaved) {
        if(isSaved) {
            mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));
        } else {
            mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            L.d();
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initWebView(GankInfo info) {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        //webSettings.setUseWideViewPort(true);
        mWebView.setWebViewClient(mWebViewClientBase);
        mWebView.setWebChromeClient(mWebChromeClientBase);
        mWebView.loadUrl(this.mGankInfo.getUrl());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private WebViewClientBase mWebViewClientBase = new WebViewClientBase();

    private class WebViewClientBase extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            // TODO Auto-generated method stub
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url,
                                           boolean isReload) {
            // TODO Auto-generated method stub
            super.doUpdateVisitedHistory(view, url, isReload);
        }
    }

    private WebChromeClientBase mWebChromeClientBase = new WebChromeClientBase();

    private class WebChromeClientBase extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {

        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            // TODO Auto-generated method stub
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onReceivedTouchIconUrl(WebView view, String url,
                                           boolean precomposed) {
            // TODO Auto-generated method stub
            super.onReceivedTouchIconUrl(view, url, precomposed);
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog,
                                      boolean isUserGesture, Message resultMsg) {
            // TODO Auto-generated method stub
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }

    }
}
