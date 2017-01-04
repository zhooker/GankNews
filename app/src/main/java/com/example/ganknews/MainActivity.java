package com.example.ganknews;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ganknews.about.AboutFragment;
import com.example.ganknews.gank.girl.GirlFragment;
import com.example.ganknews.gank.ui.GankFragment;
import com.example.ganknews.setting.PrefsFragement;
import com.example.ganknews.util.L;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "FRAGMENT_ID";

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

        if (savedInstanceState != null) {
            L.d("last index = "+checkID(savedInstanceState.getInt(TAG)));
            navigationView.setCheckedItem(savedInstanceState.getInt(TAG));
            onNavigationItemSelected(navigationView.getMenu().findItem(savedInstanceState.getInt(TAG)));
        } else {
            L.d("last index = "+null);
            navigationView.setCheckedItem(R.id.nav_news);
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_news));
        }
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
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        L.d(currFragmentID);
        //clearFragment();
        outState.putInt(TAG, currFragmentID);
        super.onSaveInstanceState(outState);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        setTitle(item.getTitle());

        changeFragment(id);
        return true;
    }


    private Fragment createBaseFragment(int id) {
        if (id == R.id.nav_news) {
            return new GankFragment();
        } else if (id == R.id.nav_gallery) {
            return new GirlFragment();
        } else if (id == R.id.nav_setting) {
            return new PrefsFragement();
        } else if (id == R.id.nav_about) {
            return new AboutFragment();
        }
        return null;
    }

    private void changeFragment(int id) {
        Fragment fragment = getFragmentManager().findFragmentByTag(String.valueOf(id));
        if (fragment == null)
            fragment = createBaseFragment(id);
        if (!fragment.isAdded())
            getFragmentManager().beginTransaction().add(R.id.content_main, fragment, String.valueOf(id)).commitAllowingStateLoss();
        if (id != currFragmentID) {
            replaceFragment(fragment);
            currFragmentID = id;
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment curr = getFragmentManager().findFragmentByTag(String.valueOf(currFragmentID));
        if (curr != null) {
            transaction.hide(curr);
            curr.setExitTransition(new Explode());

        }
        if (fragment != null) {
            fragment.setEnterTransition(new Explode());
            transaction.show(fragment);
        }
        transaction.commitAllowingStateLoss();
    }

    private int checkID(int id) {
        int[] ids = new int[]{
                R.id.nav_news,
                R.id.nav_gallery,
                R.id.nav_setting,
                R.id.nav_about
        };
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == id)
                return i + 1;
        }
        return -1;
    }

    private void clearFragment() {
        int[] ids = new int[]{
                R.id.nav_news,
                R.id.nav_gallery,
                R.id.nav_setting,
                R.id.nav_about
        };
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        for (int i = 0; i < ids.length; i++) {
            Fragment fragment = getFragmentManager().findFragmentByTag(String.valueOf(ids[i]));
            if (fragment != null)
                transaction.remove(fragment);
        }
        transaction.commitAllowingStateLoss();
    }

    private int currFragmentID;
}
