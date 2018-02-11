package com.competitionapp.nrgscouting;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Peyton Lee on 2/9/2018.
 */

public class MatchTimerEntry extends Fragment {
    private Button eatPowerCube;
    private Button allySwitch;
    private Button oppSwitch;
    private Button scale;
    private Button boost;
    private Button cubeDrop;
    private Button forceUsed;
    private Button climbStart;
    private Button timer;
    private Thread thread;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.match_timer_entry, container, false);

    }
    @Override
    public void onStart() {
        eatPowerCube = (Button)(getView().findViewById(R.id.eat_powercube));
        allySwitch = (Button)(getView().findViewById(R.id.ally_switch));
        oppSwitch = (Button)(getView().findViewById(R.id.opp_switch));
        scale = (Button)(getView().findViewById(R.id.claimed_scale));
        boost = (Button)(getView().findViewById(R.id.boost_used));
        cubeDrop = (Button)(getView().findViewById(R.id.cube_dropped));
        forceUsed = (Button)(getView().findViewById(R.id.force_used));
        climbStart = (Button)(getView().findViewById(R.id.climb_start));
        timer = (Button)(getView().findViewById(R.id.time_start));
        timer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                thread = new Thread() {
                    @Override
                    public void run() {
                        while(!isInterrupted()) {
                            try {
                                thread.sleep(150000);
                                if(isInterrupted()) {
                                    
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
            }
        });
        super.onStart();
    }
}