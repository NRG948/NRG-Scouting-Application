package com.competitionapp.nrgscouting;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static com.competitionapp.nrgscouting.R.id.fab;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialistFragment extends Fragment {
    ListView listView;
    ArrayAdapter<String> teamAdapter;
    String[] matchTeams={"98","948"};
    public FloatingActionButton fab1;

    public SpecialistFragment() {
        // Required empty public constructor
    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_specialist, container, false);

            //List initializations
            listView = (ListView)rootView.findViewById(R.id.teams);
        
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
            listView.setEmptyView(rootView.findViewById(R.id.emptyView));

            return rootView;
    }

}
