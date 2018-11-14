package com.danieltatarkin.weatherat;

import android.os.Bundle;

import com.danieltatarkin.weatherat.ui.adapters.PagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBottomToolbar();
    }

    private void setupBottomToolbar() {
        // Find tab_layout, add 3 tabs to it
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label_week));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label_now));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label_hourly));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Create new Pager Adapter for toolbar
        final ViewPager viewPager = findViewById(R.id.pager_main);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        // Setup OnTabListeners
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Set middle tab as our selected tab on run
        Objects.requireNonNull(tabLayout.getTabAt(1)).select();
    }
}
