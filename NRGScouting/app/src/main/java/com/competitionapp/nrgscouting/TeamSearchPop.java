package com.competitionapp.nrgscouting;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

/**
 * Created by nipunchhajed on 7/22/17.
 */

public class TeamSearchPop extends Activity {
    Context c;
    ListView lv;
    SearchView sv;
    ArrayAdapter<String> adapter;
    String[] teams = {"948", "492", "6905969", "Nipun is Awesome", "Acchin is awesome", "Valliappan is awesome", "Peyton is Awesome", "Nelson is Awesome", "Adam is Awesome", "Justin - I don't know"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_team_search);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));
        
    }
}
