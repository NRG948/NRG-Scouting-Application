package com.competitionapp.nrgscouting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
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

        public String cutToSize(String input, int length) {
            if(input.length() <= length) {
                return input;
            } else {
                return input.substring(0, length);
            }
        }
    }
}
