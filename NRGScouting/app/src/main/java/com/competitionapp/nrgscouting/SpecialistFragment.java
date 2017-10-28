package com.competitionapp.nrgscouting;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import static com.competitionapp.nrgscouting.R.id.fab;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialistFragment extends Fragment implements RefreshableFragment{
    ListView listView;
    ArrayAdapter<String> teamAdapter;
    String[] specialistTeams={};
    ArrayList<SEntry> specialistEntries = new ArrayList<SEntry>();
    View rootView;

    public SpecialistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_specialist, container, false);

        //List initializations
        listView = (ListView)rootView.findViewById(R.id.teams);

        //Memory card code
        //if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        //File externalStoreDir = Environment.getExternalStorageDirectory();
        //File entries = new File(externalStoreDir,"Entries.txt");
        //ArrayList<Entry> list=MatchEntry.getAllEntriesInFileIntoObjectForm(entries);
        //matchTeams=new String[list.size()];

        //for(int i=0;i<=matchTeams.length;i++){
        //    matchTeams[i]="Match:"+list.get(i).matchNumber+"   Team:"+list.get(i).teamName;
        //  }
        //}
        //else{
        //  System.out.println("File not found...");
        //}

        //End of memory card code


        teamAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, specialistTeams);
        listView.setAdapter(teamAdapter);
        listView.setEmptyView(rootView.findViewById(R.id.emptyView));
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            final int pos = position;

            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(specialistTeams[position])
                .setMessage(entryToReadableString(specialistEntries.get(position)))
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
                            SEntry entry = specialistEntries.get(pos);
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                            if(sharedPreferences.contains("SpecialistEntryList")) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Set<String> entryList = sharedPreferences.getStringSet("SpecialistEntryList",null);
                                String keyName = SpecialistEntry.getKeyName(entry);

                                if(entryList.contains(keyName)) {
                                    entryList.remove(keyName);
                                }

                                if(entryList.isEmpty()) {
                                    editor.remove("SpecialistEntryList");
                                } else {
                                    editor.putStringSet("SpecialistEntryList", entryList);
                                }

                                for (int i = pos + 1; i < specialistEntries.size(); i++) {
                                    editor.putInt(SpecialistEntry.getKeyName(specialistEntries.get(i)) + ":index",
                                            i - 1);
                                }

                                editor.commit();
                                Toast.makeText(getActivity(), (String) "Deleted entry '" + SpecialistEntry.getKeyName(entry)+"'.", Toast.LENGTH_LONG).show();
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

                        copyToClipboard(specialistEntries.get(pos).toString());
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

    public static String entryToReadableString(SEntry entry) {

        return "Position: " + entry.position +
                "\nPilot Fouls: " + entry.pilotFouls +
                "\nFoul Points Awarded to Other Alliance: " + entry.intentFouls +
                "\nDriver Skill: " + entry.driverSkill + "/5.0" +
                "\nRetrieved Gear in Auto: " + entry.autoGear +
                "\nAuto Gear Comments: " + entry.autoGearComment +
                "\nGracious Professionalism Rating: " + entry.GPRating + "/5.0" +
                "\nReliability: "+ entry.reliability + "/5.0" +
                "\nAntagonism: " + entry.antagonism + "/5.0" +
                "\nRope Drop Time: " + entry.ropeDropTime +
                "\nAdditional Comments: " + entry.specComments +
                "\nReason for Yellow Card: " + entry.Yellow +
                "\nReason for Red Card: " + entry.Red +
                "\nReason for Death: " + entry.Death;
    }

    @Override
    public void refreshFragment() {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        if(!sharedPref.contains("SpecialistEntryList") || sharedPref.getStringSet("SpecialistEntryList", null) == null) {
            listView.setVisibility(View.GONE);
            rootView.findViewById(R.id.emptyView).setVisibility(View.VISIBLE);
            return;
        }

        listView.setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.emptyView).setVisibility(View.GONE);

        String specialistString = "";

        for(String x : sharedPref.getStringSet("SpecialistEntryList", null)) {
            if(sharedPref.contains(x)) {
                specialistString += sharedPref.getString(x, "");
            }
        }

        specialistEntries = sortEntries(SpecialistEntry.getAllEntriesInFileIntoObjectForm(specialistString));

        if(specialistEntries.size()>0) {
            specialistTeams = new String[specialistEntries.size()];

            for (int i = 0; i < specialistTeams.length; i++) {
                specialistTeams[i] =  specialistEntries.get(i).teamName + "\nMatch " + specialistEntries.get(i).matchNumber;
            }

            teamAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, specialistTeams);
            listView.setAdapter(teamAdapter);
        }

        return;
    }

    public ArrayList<SEntry> sortEntries(ArrayList<SEntry> originalEntries) {
        ArrayList<SEntry> sortedEntries = new ArrayList<SEntry>();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        ArrayList<SEntry> indexlessEntries = new ArrayList<SEntry>();

        for(int i = originalEntries.size(); i > 0; i--) {
            sortedEntries.add(null);
        }

        for (SEntry x : originalEntries) {
            if(sharedPref.contains(SpecialistEntry.getKeyName(x) + ":index")) {
                int index = sharedPref.getInt(SpecialistEntry.getKeyName(x) + ":index", 0);
                if(index < sortedEntries.size() && sortedEntries.get(index) == null) {
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
                    editor.putInt(SpecialistEntry.getKeyName(indexlessEntries.get(unsortedIndex)) + ":index", i);
                    indexlessEntries.remove(unsortedIndex);
                }
            }
            editor.apply();
        }
        Collections.reverse(sortedEntries);
        return sortedEntries;
    }

}
