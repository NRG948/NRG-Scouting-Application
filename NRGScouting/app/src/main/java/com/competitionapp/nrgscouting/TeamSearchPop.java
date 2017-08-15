package com.competitionapp.nrgscouting;

import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;


/**
 * Created by nipunchhajed NO on 7/22/17.
 */

public class TeamSearchPop extends AppCompatActivity {
    ListView lv;
    SearchView sv;
    ArrayAdapter<String> adapter;
    String[] teams = {"948", "492", "6905969"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_team_search);

        lv = (ListView)findViewById(R.id.teams_list);
        sv = (SearchView)findViewById(R.id.searchView);


        adapter = new ArrayAdapter<String>(TeamSearchPop.this, android.R.layout.simple_list_item_1, teams);
        lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MatchEntry matchEntry = new MatchEntry();
                        FragmentTransaction fragmentTransaction =
                                getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.special_container, matchEntry);
                        fragmentTransaction.commit();
                        lv.setVisibility(View.GONE);
                        sv.setVisibility(View.GONE);
                    }
                });

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
