/**
 * THIS CLASS WILL NOT BE USED AS WE HAVE DECIDED TO SWITCH TO ACTIVITIES INSTEAD OF FRAGMENTS
 */
package com.competitionapp.nrgscouting;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import java.io.File;
import android.os.Environment;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchFragment extends Fragment {
    ListView listView;
    ArrayAdapter<String> teamAdapter;
    String[] matchTeams={"98","948"};

    public MatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_specialist, container, false);

        //List initializations
        listView= (ListView)rootView.findViewById(R.id.teams);


        //Memory card code
        //if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //File externalStoreDir = Environment.getExternalStorageDirectory();
            //File entries = new File(externalStoreDir,"Entries.txt");
            //ArrayList<Entry> list=MatchEntry.getAllEntriesInFileIntoObjectForm(entries);
            //matchTeams=new String[list.size()];

            //for(int i=0;i<=matchTeams.length;i++){
            //    matchTeams[i]="Match:"+list.get(i).matchNumber+"   Team:"+list.get(i).teamName;
          //  }
        //}
        //else{
          //  System.out.println("File not found...");
        //}

        //End of memory card code


        teamAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, matchTeams);
        listView.setAdapter(teamAdapter);
        return rootView;
    }
}