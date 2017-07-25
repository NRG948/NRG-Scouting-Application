package com.competitionapp.nrgscouting;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

/**
 * Created by nipunchhajed on 7/22/17.
 */

public class TeamSearchPop extends Activity {
    ListView lv;
    SearchView sv;
    ArrayAdapter<String> adapter;
    String[] teams = {"948", "492", "6905969", "Nipun is Awesome", "Acchin is awesome", "Valliappan is awesome", "Peyton is Awesome", "Nelson is Awesome", "Adam is Awesome", "Justin - I don't know"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_team_search);

        lv = (ListView)findViewById(R.id.teams_list);
        sv = (SearchView)findViewById(R.id.searchView);

        adapter = new ArrayAdapter<String>(TeamSearchPop.this, android.R.layout.simple_list_item_1, teams);
        lv.setAdapter(adapter);

        //SearchView set up
        sv.setQueryHint("Search...");
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }
}
