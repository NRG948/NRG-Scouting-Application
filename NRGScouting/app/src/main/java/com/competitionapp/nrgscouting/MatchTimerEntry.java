package com.competitionapp.nrgscouting;

import android.graphics.Color;
import android.os.Build;
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
    boolean claimedAllySwitch = false;
    private Button oppSwitch;
    boolean claimedOppSwitch = false;
    private Button scale;
    boolean claimedScale = false;
    private Button cubeDrop;
    boolean hasCube = false;

    private Button forceUsed;
    boolean forcePressed = false;
    private Button boost;
    boolean boostPressed = false;

    private Button climbStart;
    boolean climbStartPressed = false;

    private Button start;
    boolean timerIsRunning = false;

    public long startTime = 0;
    public long savedTime = 0;
    Button[] buttonList;

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

        updateButtonEnabled();

        timer.setBase(SystemClock.elapsedRealtime() - 150000*0);

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!timerIsRunning) {
                    timer.setBase(SystemClock.elapsedRealtime() - (150*1000*0 - savedTime));
                    timer.start();
                    startTime = SystemClock.elapsedRealtime();
                    start.setText("Pause Timer");
                    timerIsRunning = true;
                    updateButtonEnabled();
                } else {
                    timer.stop();
                    timerIsRunning = false;
                    savedTime += SystemClock.elapsedRealtime() - startTime;
                    start.setText("Start Timer");
                    updateButtonEnabled();
                }
            }
        });

        allySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerIsRunning) {
                    if(!claimedAllySwitch) {
                        //Newly claimed Ally Switch
                        allySwitch.setText("Lost Ally Switch");
                        logTimerEvent(Entry.EventType.ALLY_START_2);
                        claimedAllySwitch = true;
                    } else {
                        allySwitch.setText("Claimed Ally Switch");
                        logTimerEvent(Entry.EventType.ALLY_END_3);
                        claimedAllySwitch = false;
                    }
                }
            }
        });

        oppSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerIsRunning) {
                    if(!claimedOppSwitch) {
                        //Newly claimed Ally Switch
                        oppSwitch.setText("Lost Opp. Switch");
                        logTimerEvent(Entry.EventType.OPP_START_4);
                        claimedOppSwitch = true;
                    } else {
                        oppSwitch.setText("Claimed Opp. Switch");
                        logTimerEvent(Entry.EventType.OPP_END_5);
                        claimedOppSwitch = false;
                    }
                }
            }
        });

        scale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerIsRunning) {
                    if(!claimedScale) {
                        //Newly claimed Ally Switch
                        scale.setText("Lost Scale");
                        logTimerEvent(Entry.EventType.SCALE_START_6);
                        claimedScale = true;
                    } else {
                        scale.setText("Claimed Scale");
                        logTimerEvent(Entry.EventType.SCALE_END_7);
                        claimedScale = false;
                    }
                }

            }
        });

        boost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerIsRunning && !boostPressed) {
                    logTimerEvent(Entry.EventType.BOOST_8);
                    boostPressed = true;
                    boost.setEnabled(false);
                }
            }
        });

        cubeDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerIsRunning) {
                    if (hasCube) {
                        long timePressed = SystemClock.elapsedRealtime();
                        hasCube = false;
                        //Show additional selection screen

                    } else {
                        hasCube = true;
                        logTimerEvent(Entry.EventType.PICKED_CUBE_0);
                    }
                    ListElementsArrayList.add(timer.getText().toString());
                }

            }
        });

        forceUsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerIsRunning && !forcePressed) {
                    logTimerEvent(Entry.EventType.FORCE_9);
                    forcePressed = true;
                    forceUsed.setEnabled(false);
                }
            }
        });
        
        climbStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerIsRunning && !climbStartPressed) {
                    logTimerEvent(Entry.EventType.CLIMB_START_10);
                    climbStartPressed = true;
                    climbStart.setEnabled(false);
                }
            }
        });
        super.onStart();
    }

    public void updateButtonEnabled() {
        if(timerIsRunning) {
            allySwitch.setEnabled(true);
            oppSwitch.setEnabled(true);
            scale.setEnabled(true);
            cubeDrop.setEnabled(true);
            if(forcePressed) {forceUsed.setEnabled(false);} else {forceUsed.setEnabled(true);}
            if(climbStartPressed) {climbStart.setEnabled(false);} else {climbStart.setEnabled(true);}
            if(boostPressed) {boost.setEnabled(false);} else {boost.setEnabled(true);}
        } else {
            allySwitch.setEnabled(false);
            oppSwitch.setEnabled(false);
            scale.setEnabled(false);
            cubeDrop.setEnabled(false);
            forceUsed.setEnabled(false);
            climbStart.setEnabled(false);
            boost.setEnabled(false);
        }
    }

    public void logTimerEvent(Entry.EventType type) {
        logTimerEvent(type, Entry.CubeDropType.NONE_0);
    }

    public void logTimerEvent(Entry.EventType type, Entry.CubeDropType cubeType) {
        ((TabbedActivity) getActivity()).newEntry.timeEvents.add(new Entry.TimeEvent(getCurrentTimeStamp(), type, cubeType));
    }

    public int getCurrentTimeStamp() {
        return (int) (SystemClock.elapsedRealtime() - startTime + savedTime);
    }

    public int getTimeStampFromInput(long input) {
        return (int) (input - startTime + savedTime);
    }

}