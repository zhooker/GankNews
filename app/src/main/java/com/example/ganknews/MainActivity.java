package com.example.ganknews;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ganknews.about.AboutFragment;
import com.example.ganknews.gank.girl.GirlFragment;
import com.example.ganknews.gank.ui.GankFragment;
import com.example.ganknews.setting.PrefsFragement;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_news);
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_news));
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        setTitle(item.getTitle());

        if (id == R.id.nav_news) {
            changeFragment(GankFragment.TAG);
        } else if (id == R.id.nav_gallery) {
            changeFragment(GirlFragment.TAG);
        } else if (id == R.id.nav_setting) {
            changeFragment(PrefsFragement.TAG);
        } else if (id == R.id.nav_about) {
            changeFragment(AboutFragment.TAG);
        }
        return true;
    }

    private Fragment createBaseFragment(String tag) {
        if (GankFragment.TAG.equals(tag)) {
            return new GankFragment();
        } else if (GirlFragment.TAG.equals(tag)) {
            return new GirlFragment();
        } else if (AboutFragment.TAG.equals(tag)) {
            return new AboutFragment();
        } else if (PrefsFragement.TAG.equals(tag)) {
            return new PrefsFragement();
        }
        return null;
    }

    private void changeFragment(String tag) {
        Fragment fragment = fragments.get(tag);
        if (fragment == null) {
            fragment = createBaseFragment(tag);
            if (fragment == null)
                return;
            getFragmentManager().beginTransaction().add(R.id.content_main, fragment).commitAllowingStateLoss();
            fragments.put(tag, fragment);
        }
        if (currFragment != fragment)
            replaceFragment(fragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (currFragment != null) {
            transaction.hide(currFragment);
            currFragment.setExitTransition(new Explode());
            fragment.setEnterTransition(new Explode());
        }
        currFragment = fragment;
        transaction.show(currFragment);
        transaction.commitAllowingStateLoss();
    }

    private HashMap<String, Fragment> fragments = new HashMap<>(5);
    private Fragment currFragment;
}
