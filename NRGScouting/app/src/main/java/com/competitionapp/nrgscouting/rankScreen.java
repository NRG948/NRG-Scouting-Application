package com.competitionapp.nrgscouting;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class rankScreen extends AppCompatActivity {
    
    ArrayList<Entry> entryList=new ArrayList<Entry>();
    static ArrayList<Team> teams = new ArrayList<Team>();//List of summed up team data
    ListView listView;
    ArrayAdapter<String> teamAdapter;
    String[] teamScores = new String[0];
    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        listView = (ListView)findViewById(R.id.teams);
        teamAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teamScores);
        listView.setAdapter(teamAdapter);
        rank();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void rank() {

        teams=new ArrayList<>();

        String exportedData = MatchFragment.exportEntryData(this);
        entryList = Entry.getEntriesFromString(exportedData);


        EntryToTeam.combineTeams(teams, entryList);
        Algorithm ranker = new Algorithm();
        for(Team a:teams){
            a.scoreEntries(ranker);
        }
        Collections.sort(teams);

        teamScores = new String[teams.size()];

        String toDisplay="";
        for(int i = 0; i < teams.size(); i++){
            teamScores[i] = "Team:"+teams.get(i).teamName+" Score:"+teams.get(i).getRankScore()+"\n";
        }

        System.out.print(teams);
    }
}
