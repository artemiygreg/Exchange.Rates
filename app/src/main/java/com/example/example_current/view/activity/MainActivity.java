package com.example.example_current.view.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.example_current.R;
import com.example.example_current.android_service.DataBaseService;
import com.example.example_current.receiver.MyReceiver;
import com.example.example_current.view.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements MyReceiver.StatusValuteCallback {
    public final static String BROAD_CAST_ACTION = "com.example.example_current.broad_cast";
    private MyReceiver myReceiver;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = new Intent(this, DataBaseService.class);
        startService(intent);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        PagerTabStrip pagerTabStrip = (PagerTabStrip)findViewById(R.id.pagerTabStrip);
        pagerTabStrip.setBackgroundColor(getResources().getColor(R.color.appColor));
        pagerTabStrip.setTabIndicatorColorResource(R.color.colorWhite);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if(i == 1){
                    viewPagerAdapter.notifyDataSetChange(i);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, ClientPreferencesActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(BROAD_CAST_ACTION);
        myReceiver = new MyReceiver(this);
        registerReceiver(myReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    @Override
    public void onDownloaded() {

    }

    @Override
    public void inProcessing() {

    }

    @Override
    public void saved() {
        viewPagerAdapter.notifyDataSetChange(viewPager.getCurrentItem());
    }

    @Override
    public void notAccess() {

    }
}