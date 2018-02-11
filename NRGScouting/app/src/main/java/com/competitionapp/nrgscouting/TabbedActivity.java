package com.competitionapp.nrgscouting;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;

/**
 * Created by Peyton Lee on 2/9/2018.
 */

public class TabbedActivity extends AppCompatActivity implements ActivityUtility{

    Entry newEntry = new Entry();

    ViewPager viewPager;
    MatchTabFragmentAdapter tabAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabbed_view_layout);

        setActionBarTitle("Match Entry");

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_timer));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_assignment2));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_edit));



        tabAdapter = new MatchTabFragmentAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.tabViewPager);
        viewPager.setAdapter(tabAdapter);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                setActionBarTitle("Match Entry");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public static class MatchTabFragmentAdapter extends FragmentPagerAdapter {

        public MatchTabFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    MatchTimerEntry matchTimerEntry0 = new MatchTimerEntry();
                    return matchTimerEntry0;
                case 1:
                    return new EndgameEntry();
                case 2:
                    MatchTimerEntry matchTimerEntry2 = new MatchTimerEntry();
                    return matchTimerEntry2;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}


