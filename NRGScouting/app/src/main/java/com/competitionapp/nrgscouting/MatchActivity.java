package com.competitionapp.nrgscouting;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MatchActivity extends MainActivity {
    ListView listView;
    ArrayAdapter<String> teamAdapter;
    String[] matchTeams = {"948", "492", "6905969"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        listView = (ListView)findViewById(R.id.list_team);
        teamAdapter = new ArrayAdapter<String>(MatchActivity.this, android.R.layout.simple_list_item_1, matchTeams);
        listView.setAdapter(teamAdapter);
    }
}


