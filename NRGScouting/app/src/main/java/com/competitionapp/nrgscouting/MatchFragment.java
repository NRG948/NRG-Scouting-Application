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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File externalStoreDir = Environment.getExternalStorageDirectory();
            File entries = new File(externalStoreDir,"Entries.txt");
            ArrayList<Entry> list=MatchEntry.getAllEntriesInFileIntoObjectForm(entries);
            matchTeams=new String[list.size()];
            teamAdapter = new ArrayAdapter<String>(MatchActivity.this, android.R.layout.simple_list_item_1, matchTeams);
            for(int i=0;i<=matchTeams.length;i++){
                matchTeams[i]="Match:"+list.get(i).matchNumber+"   Team:"+list.get(i).teamName;
            }
        }
        else{
            System.out.println("File not found...");
        }
        listView = (ListView)findViewById(R.id.list_team);
        listView.setAdapter(teamAdapter);


        return rootView;
    }


}
