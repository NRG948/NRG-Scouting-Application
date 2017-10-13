/**
 * THIS CLASS WILL NOT BE USED AS WE HAVE DECIDED TO SWITCH TO ACTIVITIES INSTEAD OF FRAGMENTS
 */
package com.competitionapp.nrgscouting;

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
import android.widget.ImageView;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import java.io.File;
import android.os.Environment;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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

    public MatchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_match, container, false);

        //List initializations
        listView= (ListView)rootView.findViewById(R.id.teams);
        //File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"/NRGScouting/");
        //File entryFile = new File(dir,"Entries.txt");
        //ArrayList<Entry> list = new ArrayList<Entry>();
        //try {
        //    String fileText=MatchEntry.getFileContent(entryFile);
        //    list = MatchEntry.getAllEntriesInFileIntoObjectForm(entryFile,fileText);
        //}
        //catch(FileNotFoundException e){
            //Do nothing
        //}
        //End of memory card code

        teamAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, matchTeams);
        listView.setEmptyView(rootView.findViewById(R.id.emptyView));
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int pos = position;

                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle(matchTeams[position])
                        .setMessage(entryToReadableString(matchEntries.get(position)))
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
                                                    String keyName = MatchEntry.getKeyName(entry);

                                                    if(entryList.contains(keyName)) {
                                                        entryList.remove(keyName);
                                                    }

                                                    if(entryList.isEmpty()) {
                                                        editor.remove("MatchEntryList");
                                                    } else {
                                                        editor.putStringSet("MatchEntryList", entryList);
                                                    }

                                                    editor.commit();
                                                    Toast.makeText(getActivity(), (String) "Deleted entry '" + MatchEntry.getKeyName(entry)+"'.", Toast.LENGTH_LONG).show();
                                                    refreshFragment();
                                                    dialog.dismiss();
                                                }
                                            }
                                        }).show();
                            }
                        })
                        .setNeutralButton("Copy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                copyToClipboard(matchEntries.get(pos).toString());
                                Toast.makeText(getContext(), "Copied to Clipboard.", Toast.LENGTH_SHORT);
                            }
                        })
                        .show();

                return false;
            }
        });

        refreshFragment();

        return rootView;
    }

    public static String entryToReadableString(Entry entry) {

        //
        //First big chance for a setting! Either as printed (see here) or as source
        //

        return "Team: " + entry.teamName +
                "\nMatch: " + entry.matchNumber +
                "\nGears Retrieved: " + entry.gearsRetrieved +
                "\nAuto Gears Retrieved : " + entry.autoGearsRetrieved +
                "\nBalls Shot: " + entry.ballsShot +
                "\nAuto Balls Shot: " + entry.autoBallsShot +
                "\nSportsmanship Rating: " + entry.rating + "/5.0" +
                "\nDeath: " + boolToString(entry.death) +
                "\nCrossed Baseline: " + boolToString(entry.crossedBaseline) +
                "\nClimbs Rope: " + boolToString(entry.climbsRope);
    }

    public static String boolToString(boolean bool) {
        if(bool) { return "Yes"; }
        else { return "No"; }
    }

    public void copyToClipboard(String copy) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(copy);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("?", copy);
            clipboard.setPrimaryClip(clip);
        }
    }

    public void refreshFragment () {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        if(!sharedPref.contains("MatchEntryList") || sharedPref.getStringSet("MatchEntryList", null) == null) {
            listView.setVisibility(View.GONE);
            rootView.findViewById(R.id.emptyView).setVisibility(View.VISIBLE);
            return;
        }

        listView.setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.emptyView).setVisibility(View.GONE);

        String matchString = "";

        for(String x : sharedPref.getStringSet("MatchEntryList", null)) {
            if(sharedPref.contains(x)) {
                matchString += sharedPref.getString(x, "");
            }
        }
        try {
            MainActivity.cacheSaver(matchString);
        }
        catch(FileNotFoundException e){
            //Do nothing
        }
        matchEntries = MatchEntry.getAllEntriesInFileIntoObjectForm(matchString);

        if(matchEntries.size()>0) {
            matchTeams = new String[matchEntries.size()];

            for (int i = 0; i < matchTeams.length; i++) {
                matchTeams[i] =  matchEntries.get(i).teamName + "\nMatch " + matchEntries.get(i).matchNumber;
            }

            teamAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, matchTeams);
            listView.setAdapter(teamAdapter);
        }
    }
}