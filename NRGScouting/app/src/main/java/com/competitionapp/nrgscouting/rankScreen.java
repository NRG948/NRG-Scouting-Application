package com.competitionapp.nrgscouting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class RankScreen extends Fragment implements RefreshableFragment{
    
    ArrayList<Entry> entryList=new ArrayList<Entry>();
    static ArrayList<Team> teams = new ArrayList<Team>();//List of summed up team data
    ListView listView;
    TeamScoreAdapter teamAdapter;
    View rootView;
    LinearLayout emptyView;
    Algorithm ranker = new SwitchAlgorithm();
    TextView rankType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_rank_screen, container, false);
        listView = (ListView)rootView.findViewById(R.id.teams);
        emptyView = (LinearLayout)rootView.findViewById(R.id.emptyView);
        teamAdapter = new TeamScoreAdapter(getContext(), teams);
        listView.setAdapter(teamAdapter);
        rankType = (TextView)rootView.findViewById(R.id.sortingTypeText);

        int i = ((MainActivity) this.getActivity()).algorithmPos;
        Menu menu = ((MainActivity) this.getActivity()).menu;

        switch (i) {
            case 0:
                //switch ranker
                //menu.getItem(R.id.sort_switch).setChecked(true);
                ranker = new SwitchAlgorithm();
                break;
            case 1:
                //menu.getItem(R.id.sort_scale).setChecked(true);
                ranker = new ScaleAlgorithm();
                break;
            case 2:
                //menu.getItem(R.id.sort_defense).setChecked(true);
                ranker = new DefensiveAlgorithm();
                break;
            case 3:
                //\menu.getItem(R.id.sort_climb).setChecked(true);
                ranker = new ClimbAlgorithm();
                break;
        }

        refreshFragment();
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_algorithm, menu);
    }

    @Override
    public void refreshFragment() {
        teams=new ArrayList<>();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        if (!sharedPref.contains("MatchEntryList") || sharedPref.getStringSet("MatchEntryList", null) == null) {
            listView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            return;
        } else {
            listView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        String exportedData = MatchFragment.exportEntryData(this.getActivity());
        entryList = Entry.getEntriesFromString(exportedData);

        EntryToTeam.combineTeams(teams, entryList);
        for(Team a:teams){
            a.scoreEntries(ranker);
        }
        Collections.sort(teams);
        Collections.reverse(teams);
        emptyView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        teamAdapter = new TeamScoreAdapter(getContext(), teams);
        listView.setAdapter(teamAdapter);
        rankType.setText("Ranking Type: " + ranker.rankType());
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final AlertDialog.Builder alertadd = new AlertDialog.Builder(getActivity());
                LayoutInflater factory = LayoutInflater.from(getActivity());
                final View alertView = factory.inflate(R.layout.rank_entry_dialog, null);
                alertadd.setView(alertView);
                alertadd.setTitle(teams.get(position).teamName + " Entries");
                alertadd.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                ListView entryListView = (ListView) alertView.findViewById(R.id.rank_entry_listview);
                EntryAdapter entryAdapter = new EntryAdapter(getContext(), teams.get(position).teamEntryList);
                entryListView.setAdapter(entryAdapter);

                final AlertDialog alertDialog = alertadd.create();
                alertDialog.show();
                return false;
            }
        });
    }

    public String cutToSize(String input, int length) {
        if(input.length() <= length) {
            return input;
        } else {
            return input.substring(0, length);
        }
    }

    public class EntryAdapter extends ArrayAdapter<Entry> {

        ArrayList<Entry> entryAdapterList;

        public EntryAdapter(Context context, ArrayList<Entry> entries) {
            super(context, 0, entries);
            this.entryAdapterList = entries;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            if (entryAdapterList.size() <= position) {
                return null;
            }
            Entry entry = entryAdapterList.get(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_rank_entry, parent, false);
            }
            
            //Choose if robot climbed based on climbData
            String test = "fail";
            if(entry.soloClimb==true||entry.astClimb==true){
                test=convertBoolToText(entry.soloClimb);
            else {
                test = "No";
            }
       
            ((TextView) convertView.findViewById(R.id.rankingEntryMatchNumber)).setText("Match Number: " + String.valueOf(entry.matchNumber) + ", " + Entry.positionToString(entry.position));
            ((TextView) convertView.findViewById(R.id.rankingEntryDefenseScore)).setText(String.valueOf(entry.rate) + "/5");
            ((TextView) convertView.findViewById(R.id.rankingEntryClimbData)).setText(
                    "Crossed Baseline: " + convertBoolToText(entry.baseline) + "\n"
                    + "Switch Autnomous: " + convertBoolToText(entry.autoSwitch) + "\n"
                            + "Scale Autnomous: " + convertBoolToText(entry.autoScale) + "\n"
                            + "Far Switch Autnomous: " + convertBoolToText(entry.autoFarSwitch) + "\n"
                            + "Far Scale Autnomous: " + convertBoolToText(entry.autoFarScale) + "\n" + "\n"
                    + "Death: " + convertBoolToText(entry.death) + "\n"
                    + "Solo Climb: " + convertBoolToText(entry.soloClimb) + "\n"
                    + "Assisted Climb: " + convertBoolToText(entry.astClimb) + "\n"
                    + "Needed Assisted Climb: " + convertBoolToText(entry.needAstClimb) + "\n"
                    + "On Platform: " + convertBoolToText(entry.platform) + "\n"
                    + "Climb: " + test);

            ;
            ((TextView) convertView.findViewById(R.id.rankingEntryFouls)).setText(String.valueOf(entry.penalties));
            ((TextView) convertView.findViewById(R.id.rankingEntryPenaltyCard)).setText(
                    "Received Red Card: " + convertBoolToText(entry.cardRed) + "\n"
                    + "Received Yellow Card: " + convertBoolToText(entry.cardYellow)
            );
            ((TextView) convertView.findViewById(R.id.rankingEntryComments)).setText(entry.comments);

            ArrayList<ArrayList<Integer>> eventTimings = Entry.getTimeEvents(entry);
            Float climbTime = 0f;
            if(eventTimings.get(4) != null && !eventTimings.get(4).isEmpty()) {
                climbTime = (Float) (eventTimings.get(4).get(0)/1000f);
            }
            String climbString = "Avg. Unused Cube Time: " + getAverageValue(eventTimings.get(0)) + " sec." +
                    "\nAvg. Cube To Switch Time: " + getAverageValue(eventTimings.get(1)) + " sec." +
                    "\nAvg. Cube To Scale Time: " + getAverageValue(eventTimings.get(2)) + " sec." +
                    "\nAvg. Cube To Exchange Time: " + getAverageValue(eventTimings.get(3)) + " sec.";
            if(Math.abs(climbTime - 0f) <= 0.0001f || !entry.soloClimb) {
                climbString += "\nClimb Time: " + "N/A";
            } else {
                climbString += "\nClimb Time: " + cutToSize(String.valueOf(climbTime), 8) + " sec.";
            }
            ((TextView) convertView.findViewById(R.id.rankingEntryAverageTimings)).setText(climbString);


            return convertView;
        }

        public String getAverageValue(ArrayList<Integer> input) {
            if(input == null|| input.isEmpty()) {
                return "N/A";
            }
            float output = 0;
            for(int x : input) {
                output += x;
            }
            output = output/1000f;
            return cutToSize(String.valueOf((Float) (output/input.size())), 8);
        }

        public String convertBoolToText(Boolean input) {
            if(input) {
                return "Yes";
            } else {
                return "No";
            }
        }
    }

    public class TeamScoreAdapter extends ArrayAdapter<Team> {

        ArrayList<Team> teamList;

        public TeamScoreAdapter(Context context, ArrayList<Team> teams) {
            super(context, 0, teams);
            this.teamList = teams;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            if (teamList.size() <= position) {
                return null;
            }
            Team team = teamList.get(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_ranking, parent, false);
            }

            ((TextView) convertView.findViewById(R.id.rankingScoreText)).setText(cutToSize(String.valueOf(team.getRankScore()), 8));
            ((TextView) convertView.findViewById(R.id.rankingTeamNameText)).setText(team.teamName);
            ((TextView) convertView.findViewById(R.id.rankingNumberText)).setText(String.valueOf(position + 1));

            return convertView;
        }
    }
}
