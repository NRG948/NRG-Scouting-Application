/**
 * THIS CLASS WILL NOT BE USED AS WE HAVE DECIDED TO SWITCH TO ACTIVITIES INSTEAD OF FRAGMENTS
 */
package com.competitionapp.nrgscouting;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
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
    ArrayAdapter<String> adapter1;
    String[] entries = {"NRG entry", "Titans entry", "Totem Robotics entry"};

    public MatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_match, container, false);

        //List and Search view initializations
        lv= (ListView)rootView.findViewById(R.id.teams);

        //ListView set up
        adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, entries);
        lv.setAdapter(adapter1);


        return rootView;
    }


}
