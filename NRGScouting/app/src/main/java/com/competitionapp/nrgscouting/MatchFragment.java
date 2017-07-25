package com.competitionapp.nrgscouting;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
    ArrayAdapter<String> adapter;
    String[] entries = {"NRG entry", "Titans entry", "Totem Robotics entry"};

    public MatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_match, container, false);

        //List and Search view initializations
        lv= (ListView)rootView.findViewById(R.id.teams_list);

        //ListView set up
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, entries);
        lv.setAdapter(adapter);


        return rootView;
    }


}
