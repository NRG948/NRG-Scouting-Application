package com.competitionapp.nrgscouting;

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
//REPLACEMENT OF MATCHFRAGMENT CLASS
public class MatchActivity extends AppCompatActivity {
    ListView lv;
    ArrayAdapter<String> adapter;
    String[] entries = {"NRG entry", "Titans entry", "Totem Robotics entry"};

    public MatchActivity() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    }

}
