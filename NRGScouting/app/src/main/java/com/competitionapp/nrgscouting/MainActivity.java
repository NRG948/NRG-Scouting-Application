package com.competitionapp.nrgscouting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ActivityUtility{
    Toolbar toolbar = null;
    FloatingActionButton fab;
    RefreshableFragment currentFragment;

    public static String CURRENT_VERSION = "3.5_0";
    public static int EDITING_ENTRY = 1;
    public static int FINISHED_ENTRY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton)findViewById(R.id.fab);

        //Set about frag initially when app opens
        final MatchFragment matchFragment = new MatchFragment();

        currentFragment = matchFragment;

        final android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, matchFragment, "mat");
        fragmentTransaction.commit();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*MatchFragment mat = (MatchFragment) getSupportFragmentManager().findFragmentByTag("mat");
        mat.refreshEntryList();*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TeamSearchPop.class);
                intent.putExtra("isEdit", false);
                startActivityForResult(intent, 0);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setActionBarTitle("Match Scouting");

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        if(sharedPref.getInt("lastState", 0) == EDITING_ENTRY) {
            Intent intent = new Intent(MainActivity.this, TabbedActivity.class);
            intent.putExtra("isEdit", true);
            intent.putExtra("retrieveFrom", "cachedEntry");
            startActivityForResult(intent, 0);
        }
    }

    public static void cacheSaver(String fileString) throws FileNotFoundException{
        File cacheDir=new File("/storage/emulated/0/Hi");
        cacheDir.mkdirs();
        File cacheFile=new File(cacheDir.getPath()+File.separator+"CLASSIFIED.txt");
        try {
            if(!cacheFile.exists()) {
                cacheFile.createNewFile();
            }
            PrintStream printer=new PrintStream(cacheFile);
            printer.print(fileString);
        }
        catch(IOException IO){
            //oops
        }
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
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
        //automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_export:
                //Toast.makeText(MainActivity.this, (String) "help", Toast.LENGTH_SHORT).show();
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Export Entries")
                        .setMessage(RetrieveDataFromPrefs())
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(R.drawable.ic_download_yellow)
                        .setNegativeButton("Copy to Clipboard", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                copyToClipboard(RetrieveDataFromPrefs());

                                Toast.makeText(MainActivity.this, "Entries copied to clipboard.", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        })
                        .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                textView.setScroller(new Scroller(this));
                textView.setVerticalScrollBarEnabled(true);
                textView.setTextIsSelectable(true);
                textView.setMovementMethod(new ScrollingMovementMethod());
                return true;
            case R.id.action_import:
                final AlertDialog.Builder alertadd = new AlertDialog.Builder(this);
                LayoutInflater factory = LayoutInflater.from(this);
                final View alertView = factory.inflate(R.layout.import_dialog, null);
                alertadd.setView(alertView);
                alertadd.setTitle("Import Entries");
                alertadd.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertadd.setPositiveButton("Import", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText importText = (EditText) alertView.findViewById(R.id.import_text);
                        String input = String.valueOf(importText.getText());
                        if(input.equals("") || Entry.getEntriesFromString(input).isEmpty()) {
                            Toast.makeText(MainActivity.this, "No entries added.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        ArrayList<Entry> importedEntries = Entry.getEntriesFromString(input);

                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPref.edit();
                        Set<String> entryList;
                        int countReplaced = 0;
                        int countSame = 0;

                        if (sharedPref.contains("MatchEntryList") && sharedPref.getStringSet("MatchEntryList", null) != null) {
                            entryList = sharedPref.getStringSet("MatchEntryList", null);
                        } else {
                            entryList = new HashSet<String>();
                        }
                        //ADD FANCY STUFF HERE LATER!!!!!!!!!
                        for(Entry x : importedEntries) {
                            String keyName = TabbedActivity.getKeyName(x);
                            if(!entryList.contains(String.valueOf(keyName))) {
                                entryList.add(keyName);
                            } else {
                                if(sharedPref.contains(keyName) && x.toString().equals(Entry.retrieveFromString(sharedPref.getString(keyName, "")).toString())) {
                                    countSame++;
                                } else {
                                    countReplaced++;
                                }
                            }
                            editor.putString(keyName, x.toString());
                            editor.putInt(keyName + ":index", entryList.size() - 1);
                        }
                        editor.putString("SAVED_VERSION", MainActivity.CURRENT_VERSION);
                        editor.putStringSet("MatchEntryList", entryList);
                        editor.apply();

                        MainActivity.this.currentFragment.refreshFragment();

                        if(countSame == 0) {
                            Toast.makeText(MainActivity.this,
                                    "Added " + String.valueOf(importedEntries.size() - countReplaced) + " new entries, replaced " + countReplaced + " entries.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "Added " + String.valueOf(importedEntries.size() - countReplaced - countSame) + " new entries, replaced " + countReplaced + " entries.\n(" + countSame +" entries were the same.)", Toast.LENGTH_LONG).show();
                        }

                    }
                });
                final AlertDialog alertDialog = alertadd.create();
                alertDialog.show();
                return true;
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, "No settings yet.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_clearMemory:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete ALL stored match entries?");
                builder.setMessage("Warning: This cannot be undone!");
                builder.setCancelable(true);
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        if (sharedPreferences.contains("MatchEntryList") && sharedPreferences.getStringSet("MatchEntryList", null) != null) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            for(String x : sharedPreferences.getStringSet("MatchEntryList", null)) {
                                if(sharedPreferences.contains(x)) {
                                    editor.remove(x);
                                }
                            }
                            editor.putStringSet("MatchEntryList", null);
                            editor.commit();
                            Toast.makeText(MainActivity.this, "Cleared all stored match entries.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "No stored match entries found.", Toast.LENGTH_LONG).show();
                        }

                        currentFragment.refreshFragment();
                    }
                });
                builder.show();
                return true;
            case R.id.sort_switch:
                ((RankScreen) currentFragment).ranker = new SwitchAlgorithm();
                currentFragment.refreshFragment();
                item.setChecked(true);
                return true;
            case R.id.sort_climb:
                ((RankScreen) currentFragment).ranker = new ClimbAlgorithm();
                currentFragment.refreshFragment();
                item.setChecked(true);
                return true;
            case R.id.sort_defense:
                ((RankScreen) currentFragment).ranker = new DefensiveAlgorithm();
                currentFragment.refreshFragment();
                item.setChecked(true);
                return true;
            case R.id.sort_scale:
                ((RankScreen) currentFragment).ranker = new ScaleAlgorithm();
                item.setChecked(true);
                currentFragment.refreshFragment();
                return true;
        }

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public String RetrieveDataFromPrefs() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String matchString = "";
        if(!sharedPref.contains("MatchEntryList")) {
            return "";
        }

        for(String x : sharedPref.getStringSet("MatchEntryList", null)) {
            if(sharedPref.contains(x)) {
                matchString += sharedPref.getString(x, "") + MatchFragment.SPLITKEY;
            }
        }
        try {
            MainActivity.cacheSaver(matchString);
        }
        catch(FileNotFoundException e){
            //Do nothing
        }
        //Remove last " ||||| "
        return matchString.substring(0, matchString.length() - MatchFragment.SPLITKEY.length());
    }

    public void copyToClipboard(String copy) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(copy);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("?", copy);
            clipboard.setPrimaryClip(clip);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(currentFragment != null) {
            currentFragment.refreshFragment();
        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_match) {
            MatchFragment matchFragment = new MatchFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();

            currentFragment = matchFragment;

            fragmentTransaction.replace(R.id.fragment_container, matchFragment, "mat");
            fragmentTransaction.commit();
            toolbar = (Toolbar)findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            fab.show();

            setActionBarTitle("Scouting");
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();


        } else if (id == R.id.nav_leader){
            fab.hide();
            RankScreen rankScreen = new RankScreen();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();

            currentFragment = rankScreen;

            fragmentTransaction.replace(R.id.fragment_container, rankScreen, "mat");
            fragmentTransaction.commit();
            //toolbar = (Toolbar)findViewById(R.id.toolbar);
            //setSupportActionBar(toolbar);

            setActionBarTitle("Rankings");
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
