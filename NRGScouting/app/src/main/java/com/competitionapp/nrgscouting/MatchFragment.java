package com.competitionapp.nrgscouting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MatchFragment extends Fragment {

    ListView lv;
    SearchView sv;
    ArrayAdapter<String> adapter;
    String[] teams = {"948", "492", "6905969", "Nipun is Awesome", "Acchin is awesome", "Valliappan is awesome", "Peyton is Awesome", "Nelson is Awesome", "Adam is Awesome", "Justin - I don't know"};

    public MatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_match, container, false);

        //List and Search view initializations
        lv= (ListView)rootView.findViewById(R.id.teams_list);
        sv = (SearchView)rootView.findViewById(R.id.searchView);

        //ListView set up
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, teams);
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

        return rootView;
    }


}
