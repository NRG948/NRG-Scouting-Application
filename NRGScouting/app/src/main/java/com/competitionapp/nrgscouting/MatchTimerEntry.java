package com.competitionapp.nrgscouting;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Timer;
import android.widget.Button;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Handler;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;




/**
 * Created by Peyton Lee on 2/9/2018.
 */

public class MatchTimerEntry extends Fragment {
    private String[] ListElements = new String[] {  };
    private List <String> ListElementsArrayList ;
    private Chronometer timer;
    private Button allySwitch;
    private Button oppSwitch;
    private Button scale;
    private Button boost;
    private Button cubeDrop;
    private Button forceUsed;
    private Button climbStart;
    private Button start;
    private Thread thread;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        return inflater.inflate(R.layout.match_timer_entry, container, false);

}
    @Override
    public void onStart() {
        timer = (Chronometer) (getView()).findViewById(R.id.mainTimer);
        allySwitch = (Button)(getView().findViewById(R.id.ally_switch));
        oppSwitch = (Button)(getView().findViewById(R.id.opp_switch));
        scale = (Button)(getView().findViewById(R.id.claimed_scale));
        boost = (Button)(getView().findViewById(R.id.boost_used));
        cubeDrop = (Button)(getView().findViewById(R.id.cube_dropped));
        forceUsed = (Button)(getView().findViewById(R.id.force_used));
        climbStart = (Button)(getView().findViewById(R.id.climb_start));
        start = (Button)(getView().findViewById(R.id.time_start));

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.setBase(SystemClock.elapsedRealtime());
                timer.start();
                start.setEnabled(true);
            }
        });

        allySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListElementsArrayList.add(timer.getText().toString());

            }
        });

        oppSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListElementsArrayList.add(timer.getText().toString());

            }
        });

        scale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListElementsArrayList.add(timer.getText().toString());

            }
        });

        boost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListElementsArrayList.add(timer.getText().toString());

            }
        });

        cubeDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListElementsArrayList.add(timer.getText().toString());

            }
        });

        forceUsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListElementsArrayList.add(timer.getText().toString());

            }
        });
        
        climbStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListElementsArrayList.add(timer.getText().toString());

            }
        });
        super.onStart();
    }
}