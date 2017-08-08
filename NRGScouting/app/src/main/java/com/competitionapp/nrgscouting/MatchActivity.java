package com.competitionapp.nrgscouting;
import java.io.File;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MatchActivity extends MainActivity {
    ListView listView;
    ArrayAdapter<String> teamAdapter;
    String[] matchTeams;

    @Override
    protected void onCreate(Bundle savedInstanceState){
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
    }
}


