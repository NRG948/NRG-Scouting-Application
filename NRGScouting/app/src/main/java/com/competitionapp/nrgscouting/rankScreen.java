package com.competitionapp.nrgscouting;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class rankScreen extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_screen);
    }

    @Override
    protected void onStart() {
        super.onStart();
        textView=(TextView)findViewById(R.id.text_view);
    }

    public void rank(View view) {
        textView=(TextView)findViewById(R.id.text_view);
        EntryToTeam.teams=new ArrayList<>();
        //EntryToTeam.combineTeams();
        Algorithm ranker = new Algorithm();
        for(Team a:EntryToTeam.teams){
            a.rankScore = ranker.rankScore(ranker.teleopScore(),ranker.autonomousScore());
        }
        Collections.sort(EntryToTeam.teams);
        String toDisplay="";
        for(Team a:EntryToTeam.teams){
            toDisplay+="Team:"+a.teamName+" Score:"+a.rankScore+"\n";
        }
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setLines(100);
        textView.setText(toDisplay);
        System.out.print(EntryToTeam.teams);
    }
}
