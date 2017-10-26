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

    public MatchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_match, container, false);

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

                                                    for (int i = pos + 1; i < matchEntries.size(); i++) {
                                                        editor.putInt(MatchEntry.getKeyName(matchEntries.get(i)) + ":index",
                                                                i - 1);
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
                        .setNeutralButton("Show QR Code", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /*
                                copyToClipboard(matchEntries.get(pos).toString());
                                Toast.makeText(getContext(), "Copied to Clipboard.", Toast.LENGTH_SHORT);
                                */

                                displayQRCode(matchEntries.get(pos));

                            }
                        })
                        .show();

                return false;
            }
        });

        refreshFragment();

        return rootView;
    }

    public void displayQRCode(Entry entry){
        String code=getCode(entry);
        AlertDialog.Builder alertadd = new AlertDialog.Builder(getContext());
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View view = factory.inflate(R.layout.qr, null);
        ((ImageView)(view.findViewById(R.id.qrCodeImageView))).setImageBitmap(QRCodeGenerator.qrCodeMapFor(code));
        alertadd.setView(view);
        alertadd.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertadd.show();
    }

    public String getCode(Entry a){
        return (a.position)+teamAndMatchNumber(a.teamName.substring(0,a.teamName.indexOf("-")-1))+teamAndMatchNumber(Integer.toString(a.matchNumber).substring(0,Integer.toString(a.matchNumber).length()))
                +twoDigitization(a.gearsRetrieved)+twoDigitization(a.ballsShot)+twoDigitization(a.autoGearsRetrieved)+twoDigitization(a.autoBallsShot)+(a.crossedBaseline?"T":"F")+(a.climbsRope?"T":"F")
                +(a.death?"T":"F")+(a.yellowCard?"T":"F")+(a.redCard?"T":"F")+(a.chainProblems?"T":"F")+(a.disconnectivity?"T":"F")+(a.otherProblems?"T":"F")+twoDigitization(a.defensiveStrategy);
    }

    public String twoDigitization(int number){
        return (Integer.toString(number).length()==2)?(Integer.toString(number)):("0"+number);
    }


    public String teamAndMatchNumber(String number){
        int zeroesToAdd=5-(number.length());
        String team="";
        for(int i=0;i<zeroesToAdd;i++){
            team+="0";
        }
        return team+=number;
    }

    public static String entryToReadableString(Entry entry) {

        //
        //First big chance for a setting! Either as printed (see here) or as source
        //

        return "Team: " + entry.teamName +
                "\nMatch: " + entry.matchNumber +
                "\nAuto Gears Retrieved : " + entry.autoGearsRetrieved +
                "\nAuto Balls Scored: " + entry.autoBallsShot +
                "\nTele-Op Gears Retrieved: " + entry.gearsRetrieved +
                "\nTele-Op Balls Scored: " + entry.ballsShot +
                "\nGracious Playstyle: " + entry.rating + "/5.0" +
                "\nDefensive Strategy: " + MatchEntry.strategyStrengthFromInt(entry.defensiveStrategy) +
                "\nDeath: " + boolToString(entry.death) +
                "\nCrossed Baseline: " + boolToString(entry.crossedBaseline) +
                "\nClimbs Rope: " + boolToString(entry.climbsRope)+
                "\nYellow Card: "+ boolToString(entry.yellowCard) +
                "\nRed Card: " + boolToString(entry.redCard) +
                "\nChain Problems: " + boolToString(entry.chainProblems) +
                "\nDisconnectivity: " + boolToString(entry.disconnectivity) +
                "\nOther Problems: " + boolToString(entry.otherProblems);
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

        matchEntries = sortEntries(MatchEntry.getAllEntriesInFileIntoObjectForm(matchString));

        if(matchEntries.size()>0) {
            matchTeams = new String[matchEntries.size()];

            for (int i = 0; i < matchTeams.length; i++) {
                matchTeams[i] =  matchEntries.get(i).teamName + "\nMatch " + matchEntries.get(i).matchNumber;
            }

            teamAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, matchTeams);
            listView.setAdapter(teamAdapter);
        }
    }


    public ArrayList<Entry> sortEntries(ArrayList<Entry> originalEntries) {
        ArrayList<Entry> sortedEntries = new ArrayList<Entry>();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        ArrayList<Entry> indexlessEntries = new ArrayList<Entry>();

        for(int i = originalEntries.size(); i > 0; i--) {
            sortedEntries.add(null);
        }

        for (Entry x : originalEntries) {
            if(sharedPref.contains(MatchEntry.getKeyName(x) + ":index")) {
                int index = sharedPref.getInt(MatchEntry.getKeyName(x) + ":index", 0);
                if(index < sortedEntries.size() && index >= 0 && sortedEntries.get(index) == null) {
                    sortedEntries.set(index, x);
                } else {
                    indexlessEntries.add(x);
                }
            } else {
                indexlessEntries.add(x);
            }
        }

        if(!indexlessEntries.isEmpty()) {
            SharedPreferences.Editor editor = sharedPref.edit();
            for(int i = sortedEntries.size() - 1; i >= 0; i--) {
                if(!indexlessEntries.isEmpty() && sortedEntries.get(i) == null) {
                    int unsortedIndex = indexlessEntries.size() - 1;
                    sortedEntries.set(i, indexlessEntries.get(unsortedIndex));
                    editor.putInt(MatchEntry.getKeyName(indexlessEntries.get(unsortedIndex)) + ":index", i);

                    indexlessEntries.remove(unsortedIndex);
                }
            }
            editor.apply();
        }
        Collections.reverse(sortedEntries);
        return sortedEntries;
    }
}