package com.competitionapp.nrgscouting;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import android.os.Environment;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchFragment extends Fragment implements RefreshableFragment{
    ListView listView;
    ArrayAdapter<String> teamAdapter;
    String[] matchTeams = new String[0];
    ArrayList<Entry> matchEntries;
    View rootView;

    static String SPLITKEY = "######";

    public MatchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_match, container, false);

        setHasOptionsMenu(true);

        //List initializations
        listView= (ListView)rootView.findViewById(R.id.teams);
        teamAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, matchTeams);
        listView.setEmptyView(rootView.findViewById(R.id.emptyView));
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int pos = position;

                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle(matchTeams[position])
                        .setMessage(matchEntries.get(pos).toString())
                        .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                AlertDialog adBuilder = new AlertDialog.Builder(getActivity())
                                        .setCancelable(true)
                                        .setTitle("Confirm Deletion")
                                        .setMessage("Are you sure you want to delete this entry?")
                                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Entry entry = matchEntries.get(pos);
                                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                                                if(sharedPreferences.contains("MatchEntryList")) {
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    Set<String> entryList = sharedPreferences.getStringSet("MatchEntryList",null);
                                                    String keyName = TabbedActivity.getKeyName(entry);

                                                    if(entryList.contains(keyName)) {
                                                        entryList.remove(keyName);
                                                    }

                                                    if(entryList.isEmpty()) {
                                                        editor.remove("MatchEntryList");
                                                    } else {
                                                        editor.putStringSet("MatchEntryList", entryList);
                                                    }

                                                    for (int i = pos + 1; i < matchEntries.size(); i++) {
                                                        editor.putInt(TabbedActivity.getKeyName(matchEntries.get(i)) + ":index",
                                                                i - 1);
                                                    }

                                                    editor.commit();
                                                    Toast.makeText(getActivity(), "Deleted entry '" + TabbedActivity.getKeyName(entry)+"'.", Toast.LENGTH_LONG).show();
                                                    refreshFragment();
                                                    dialog.dismiss();
                                                }
                                            }
                                        }).show();
                            }
                        })
                        .setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /*
                                copyToClipboard(matchEntries.get(pos).toString());
                                Toast.makeText(getContext(), "Copied to Clipboard.", Toast.LENGTH_SHORT);
                                */

                                Intent intent = new Intent(getActivity(), TeamSearchPop.class);
                                intent.putExtra("isEdit", true);
                                Entry entry = matchEntries.get(pos);
                                intent.putExtra("retrieveFrom", TabbedActivity.getKeyName(entry));
                                startActivityForResult(intent, 0);

                            }
                        })
                        .show();

                return false;
            }
        });

        refreshFragment();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        inflater.inflate(R.menu.main, menu);
    }

    public void refreshFragment() {
        refreshFragment(false);
    }

    public void refreshFragment (Boolean forceRefresh) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        if(!sharedPref.contains("SAVED_VERSION") || (sharedPref.contains("SAVED_VERSION") && !sharedPref.getString("SAVED_VERSION", null).equals(MainActivity.CURRENT_VERSION))) {
            if(!forceRefresh && sharedPref.contains("MatchEntryList") && sharedPref.getStringSet("MatchEntryList", null) != null) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Uh Oh!")
                        .setMessage("Your entries are saved in a format from an older version, and loading them may crash the app. Clear entries?")
                        .setPositiveButton("Clear", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.clear();
                                editor.commit();
                                editor.putString("SAVED_VERSION", MainActivity.CURRENT_VERSION);
                                editor.commit();
                            }
                        })
                        .setNeutralButton("Ignore", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                refreshFragment(true);
                            }
                        })
                        .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                            }
                        });
                dialog.show();
                return;
            }
        }

        if (!sharedPref.contains("MatchEntryList") || sharedPref.getStringSet("MatchEntryList", null) == null) {
            listView.setVisibility(View.GONE);
            rootView.findViewById(R.id.emptyView).setVisibility(View.VISIBLE);
            return;
        }

        listView.setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.emptyView).setVisibility(View.GONE);

        String exportedData = exportEntryData(getActivity());
        ArrayList<Entry> entryList = Entry.getEntriesFromString(exportedData);
        matchEntries = sortEntries(entryList);

        if(matchEntries.size()>0) {
            matchTeams = new String[matchEntries.size()];

            for (int i = 0; i < matchTeams.length; i++) {
                matchTeams[i] =  matchEntries.get(i).teamName + "\nMatch " + matchEntries.get(i).matchNumber;
            }

            teamAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, matchTeams);
            listView.setAdapter(teamAdapter);
        }

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("SAVED_VERSION", MainActivity.CURRENT_VERSION);
        editor.commit();
    }

    public static String exportEntryData(Activity context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String matchString = "";

        for(String x : sharedPref.getStringSet("MatchEntryList", null)) {
            if(sharedPref.contains(x)) {
                matchString += sharedPref.getString(x, "") + SPLITKEY;
            }
        }
        try {
            MainActivity.cacheSaver(matchString);
        }
        catch(FileNotFoundException e){
            //Do nothing
        }
        //Remove last " ||||| "
        if(matchString.length() > SPLITKEY.length()) {
            return matchString.substring(0, matchString.length() - SPLITKEY.length());
        } else {
            return null;
        }
    }

    public ArrayList<Entry> sortEntries(ArrayList<Entry> originalEntries) {
        ArrayList<Entry> sortedEntries = new ArrayList<Entry>();
        /*
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        ArrayList<Entry> indexlessEntries = new ArrayList<Entry>();

        for(int i = originalEntries.size(); i > 0; i--) {
            sortedEntries.add(null);
        }

        for (Entry x : originalEntries) {
            if(sharedPref.contains(TabbedActivity.getKeyName(x) + ":index")) {
                int index = sharedPref.getInt(TabbedActivity.getKeyName(x) + ":index", 0);
                if(index < sortedEntries.size() && index >= 0 && sortedEntries.get(index) == null) {
                    sortedEntries.set(index, x);
                } else {
                    indexlessEntries.add(x);
                }
            } else {
                indexlessEntries.add(x);
            }
        }
        */

        /*
        if(!indexlessEntries.isEmpty()) {
            SharedPreferences.Editor editor = sharedPref.edit();
            for(int i = sortedEntries.size() - 1; i >= 0; i--) {
                if(!indexlessEntries.isEmpty() && sortedEntries.get(i) == null) {
                    int unsortedIndex = indexlessEntries.size() - 1;
                    sortedEntries.set(i, indexlessEntries.get(unsortedIndex));
                    editor.putInt(TabbedActivity.getKeyName(indexlessEntries.get(unsortedIndex)) + ":index", i);

                    indexlessEntries.remove(unsortedIndex);
                }
            }
            editor.apply();
        }
        Collections.reverse(sortedEntries);
        */

        //Remove this line to stop sorting by match number
        sortedEntries = originalEntries;
        Collections.sort(sortedEntries);
        Collections.reverse(sortedEntries);

        return sortedEntries;
    }

}
