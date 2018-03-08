package com.competitionapp.nrgscouting;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Peyton Lee on 2/9/2018.
 */

public class TabbedActivity extends AppCompatActivity implements ActivityUtility{

    Entry newEntry = new Entry();
    Boolean isEdit = false;
    Boolean loadFromCache = false;

    ViewPager viewPager;
    MatchTabFragmentAdapter tabAdapter;

    MatchTimerEntry matchTimerEntry;
    EndgameEntry endgameEntry;
    EventListEntry eventListEntry;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabbed_view_layout);

        Intent loadIntent = getIntent();

        if(loadIntent.getBooleanExtra("isEdit", false) && loadIntent.hasExtra("retrieveFrom"))  {
            isEdit = true;
            //LOAD ENTRY FROM STORAGE
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
            if(sharedPref.contains(loadIntent.getStringExtra("retrieveFrom"))) {
                newEntry = Entry.retrieveFromString(sharedPref.getString(loadIntent.getStringExtra("retrieveFrom"), ""));
                loadFromCache = true;
            } else {
                Intent intent = new Intent();
                intent.putExtra("status", -1);
                setResult(RESULT_CANCELED, intent);
                finish();
                return;
            }
        } else {
            newEntry.teamName = getIntent().getStringExtra("teamName");
        }

        if(newEntry.teamName == null) {
            //RETURN TO PREVIOUS PAGE + PRINT ERROR MESSAGE

            Intent intent = new Intent();
            intent.putExtra("status", -1);
            setResult(RESULT_CANCELED, intent);
            finish();
            return;
        }

        setActionBarTitle("Match Entry - " + newEntry.teamName);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_timer));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_assignment2));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_edit));



        tabAdapter = new MatchTabFragmentAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.tabViewPager);
        viewPager.setAdapter(tabAdapter);
        viewPager.setOffscreenPageLimit(2);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                setActionBarTitle("Match Entry - " + newEntry.teamName);

                //Anytime a tab is switched, sync endgameEntry to object.
                if(endgameEntry != null) {
                    if(loadFromCache) {
                        endgameEntry.loadFromEntry(newEntry);
                        loadFromCache = false;
                    }
                    endgameEntry.saveToEntry(newEntry);
                }
                if(eventListEntry != null) {
                    eventListEntry.timeEventList = newEntry.timeEvents;
                    eventListEntry.UpdateView();
                }

                cacheEntry();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public boolean saveAndExit() {

        if(printErrors().equals("")) {
            saveEntryToPrefs(true);
            Intent intent = new Intent();
            intent.putExtra("status", 1);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }

        //Print error message

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Uh oh!");
        builder.setCancelable(true);
        builder.setMessage("There's some errors that need to be fixed before this entry can be saved:\n\n" + printErrors());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog ad = builder.create();
        ad.show();

        return false;
    }

    public String printErrors() {
        String error = "";

        if(endgameEntry == null || endgameEntry == null) {return "";}
        if(newEntry.matchNumber <= 0) { error += "-Invalid Match Number\n";}

        return error;
    }

    public void cacheEntry() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("cachedEntry", newEntry.toString());
        editor.putInt("lastState", MainActivity.EDITING_ENTRY);
        editor.apply();
    }

    public Entry formatEntry(Entry entry) {
        boolean hasCube = false;
        int lastTime = 0;
        boolean autoCheckpoint = false;
        Collections.sort(entry.timeEvents);
        for(int i = 0; i < entry.timeEvents.size(); i++) {
            Entry.TimeEvent x = entry.timeEvents.get(i);

            if(x.type.equals(Entry.EventType.DROPPED_CUBE_1)) {
                if (!hasCube) {
                    entry.timeEvents.remove(i);
                    i--;
                } else {
                    hasCube = false;
                }
            }else if(x.type.equals(Entry.EventType.PICKED_CUBE_0)) {
                if (hasCube) {
                    entry.timeEvents.remove(i);
                    i--;
                } else {
                    hasCube = true;
                }
            }
        }

        if(hasCube) {
            entry.addTimeEvent(150000, Entry.EventType.DROPPED_CUBE_1, Entry.CubeDropType.NONE_0);
        }

        return entry;
    }


    public void saveEntryToPrefs(boolean showMessage) {
        newEntry = formatEntry(newEntry);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        String keyName = getKeyName(newEntry);

        Set<String> entryList;

        if (sharedPref.contains("MatchEntryList") && sharedPref.getStringSet("MatchEntryList", null) != null) {
            entryList = sharedPref.getStringSet("MatchEntryList", null);
            entryList.add(keyName);
        } else {
            entryList = new HashSet<String>(Arrays.asList(new String[]{keyName}));
        }

        editor.putStringSet("MatchEntryList", entryList);
        editor.putString(keyName, newEntry.toString());
        editor.putInt(keyName + ":index", entryList.size() - 1);
        editor.putInt("DefaultTeamPosition", newEntry.position);
        editor.putString("SAVED_VERSION", MainActivity.CURRENT_VERSION);
        editor.putInt("lastState", MainActivity.FINISHED_ENTRY);

        //editor.putString("SAVED_VERSION", MainActivity.CURRENT_VERSION);
        editor.apply();

        if(showMessage) {
            Toast.makeText(this, "Saved entry for " + newEntry.teamName + ": Match " + newEntry.matchNumber, Toast.LENGTH_LONG).show();
        }
    }

    public static String getKeyName(Entry entry) {
        return "entry_" + entry.teamName + "_" + entry.matchNumber;
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit Without Saving?");
        builder.setCancelable(true);
        if(isEdit) {
            builder.setMessage("Are you sure you want to discard edits to this entry? No changes will be saved!");
        } else {
            builder.setMessage("Are you sure you want to discard this entry? All data may be lost!");
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cacheEntry();

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("lastState", MainActivity.FINISHED_ENTRY);
                editor.apply();

                Intent intent = new Intent();
                intent.putExtra("status", 1);
                setResult(TabbedActivity.RESULT_OK, intent);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog ad = builder.create();
        ad.show();
    }


    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public class MatchTabFragmentAdapter extends FragmentPagerAdapter {

        public MatchTabFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    matchTimerEntry = new MatchTimerEntry();
                    return matchTimerEntry;
                case 1:
                    endgameEntry = new EndgameEntry();
                    return endgameEntry;
                case 2:
                    eventListEntry = new EventListEntry();
                    return eventListEntry;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}


