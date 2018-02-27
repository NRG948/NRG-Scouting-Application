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
                                                    Toast.makeText(getActivity(), (String) "Deleted entry '" + TabbedActivity.getKeyName(entry)+"'.", Toast.LENGTH_LONG).show();
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
        /*
        return (a.position)+teamAndMatchNumber(a.teamName.substring(0,a.teamName.indexOf("-")-1))+teamAndMatchNumber(Integer.toString(a.matchNumber).substring(0,Integer.toString(a.matchNumber).length()))
                +twoDigitization(a.gearsRetrieved)+twoDigitization(a.ballsShot)+twoDigitization(a.autoGearsRetrieved)+twoDigitization(a.autoBallsShot)+(a.crossedBaseline?"T":"F")+(a.climbsRope?"T":"F")
                +(a.death?"T":"F")+(a.yellowCard?"T":"F")+(a.chainProblems?"T":"F")+(a.disconnectivity?"T":"F")+(a.otherProblems?"T":"F")+twoDigitization(a.defensiveStrategy);
        */
        return "";
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

        if(!sharedPref.contains("SAVED_VERSION") || (sharedPref.contains("SAVED_VERSION") && !sharedPref.getString("SAVED_VERSION", null).equals(MainActivity.CURRENT_VERSION))) {
            if(sharedPref.contains("MatchEntryList") && sharedPref.getStringSet("MatchEntryList", null) != null) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Uh Oh!")
                        .setMessage("Your entries are saved in a format from an older version. Clear entries?")
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
        return matchString.substring(0, matchString.length() - SPLITKEY.length());
    }

    public ArrayList<Entry> sortEntries(ArrayList<Entry> originalEntries) {
        ArrayList<Entry> sortedEntries = new ArrayList<Entry>();
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
        return sortedEntries;
    }

    public void rank(View v){
//        textView=(TextView)findViewById(R.id.text_view);
//        EntryToTeam.teams=new ArrayList<>();
//        EntryToTeam.combineTeams();
//        Algorithm ranker = new Algorithm();
//        for(Team a:EntryToTeam.teams){
//            a.rankScore = ranker.rankScore(ranker.teleopScore(),ranker.autonomousScore());
//        }
//        Collections.sort(EntryToTeam.teams);
//        String toDisplay="";
//        for(Team a:EntryToTeam.teams){
//            toDisplay+="Team:"+a.teamName+" Score:"+a.rankScore+"\n";
//        }
//        textView.setMovementMethod(new ScrollingMovementMethod());
//        textView.setLines(100);
//        textView.setText(toDisplay);
//        System.out.print(EntryToTeam.teams);
    }
}
