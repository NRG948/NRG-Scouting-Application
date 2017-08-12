package com.competitionapp.nrgscouting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.app.FragmentManager;
import static com.competitionapp.nrgscouting.R.id.toolbar;

/**
 * Created by nipunchhajed on 7/22/17.
 */

public class TeamSearchPop extends Activity {
    ListView lv;
    SearchView sv;
    ArrayAdapter<String> adapter;
    String[] teams = {"948", "492", "6905969"};
    Toolbar toolbar;

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
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        Toast.makeText(TeamSearchPop.this, "Its working...",
                                Toast.LENGTH_LONG).show();
                        MatchEntry fr = new MatchEntry();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.matchEntry, fr);
                        fragmentTransaction.commit();
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
