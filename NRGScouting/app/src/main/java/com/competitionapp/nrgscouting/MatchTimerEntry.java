package com.competitionapp.nrgscouting;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.Handler;

import org.w3c.dom.Text;

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
    private TextView timer;
    private Button cubeDrop;
    boolean hasCube = false;
    private Button start;
    ProgressBar progressBar;
    boolean progressBarSwitched = false;
    boolean timerIsRunning = false;
    public long startTime = 0;
    public long savedTime = 0;

    Handler handler = new Handler();
    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            long currentTimeInMilliseconds = (SystemClock.elapsedRealtime() - startTime) + savedTime;
            timer.setText(EventListEntry.convertTimeToText((int) currentTimeInMilliseconds));

            progressBar.setProgress((int) currentTimeInMilliseconds);
            handler.postDelayed(updateTimerThread, 30);

            /*
            if(!progressBarSwitched && currentTimeInMilliseconds > 15000) {
                progressBarSwitched = true;
                progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorAccent),
                        android.graphics.PorterDuff.Mode.MULTIPLY);
            }*/

            if(currentTimeInMilliseconds >= 150000) {
                if(start != null) {
                    start.setEnabled(false);
                    start.setText("Start Timer");
                }
                timerIsRunning = false;
                timer.setText(EventListEntry.convertTimeToText(150000));
                savedTime = 150000;
                ((TabbedActivity) getActivity()).cacheEntry();
                handler.removeCallbacks(updateTimerThread);
            }
        }
    };

    public void loadFromEntry (Entry newEntry) {
        savedTime = newEntry.timestamp;
        if(savedTime >= 150000) {
            timer.setText(EventListEntry.convertTimeToText(150000));
            savedTime = 150000;
            ((TabbedActivity) getActivity()).newEntry.timestamp = 150000;
        } else {
            timer.setText(EventListEntry.convertTimeToText(newEntry.timestamp));
        }
        for(Entry.TimeEvent x : newEntry.timeEvents) {
            if(x.type.equals(Entry.EventType.DROPPED_CUBE_1)) {
                hasCube = false;
            }else if(x.type.equals(Entry.EventType.PICKED_CUBE_0)) {
                hasCube = true;
            }
        }
        if(hasCube) {
            cubeDrop.setText("Dropped Cube");
            cubeDrop.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_drop_cube ,0 ,0);
        }
    }


        @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        return inflater.inflate(R.layout.match_timer_entry, container, false);

    }

    @Override
    public void onStart() {
        timer = (TextView) (getView()).findViewById(R.id.mainTimer);
        cubeDrop = (Button)(getView().findViewById(R.id.cube_dropped));
        start = (Button)(getView().findViewById(R.id.time_start));
        progressBar = (ProgressBar) (getView().findViewById(R.id.progressbar));

        if(((TabbedActivity) getActivity()).isEdit) {
            loadFromEntry(((TabbedActivity) getActivity()).newEntry);
        }

        updateButtonEnabled();

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!timerIsRunning) {
                    //timer.setBase(SystemClock.elapsedRealtime() - (150*1000*0 - savedTime));
                    //timer.start();
                    handler.postDelayed(updateTimerThread, 0);
                    startTime = SystemClock.elapsedRealtime();
                    start.setText("Pause Timer");
                    timerIsRunning = true;
                    updateButtonEnabled();
                } else {
                    //timer.stop();
                    handler.removeCallbacks(updateTimerThread);
                    timerIsRunning = false;
                    savedTime += SystemClock.elapsedRealtime() - startTime;
                    start.setText("Start Timer");
                    updateButtonEnabled();
                }
            }
        });

        cubeDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerIsRunning) {
                    if (hasCube) {
                        final long timePressed = SystemClock.elapsedRealtime();
                        hasCube = false;
                        cubeDrop.setText("Picked Up Cube");
                        cubeDrop.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_picked_cube,0 ,0);
                        //Show additional selection screen

                        final AlertDialog.Builder alertadd = new AlertDialog.Builder(getContext());
                        LayoutInflater factory = LayoutInflater.from(getContext());
                        final View alertView = factory.inflate(R.layout.cube_drop_select, null);
                        alertadd.setView(alertView);
                        alertadd.setTitle("Cube Drop Location");
                        alertadd.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int nothing) {
                                //CANCEL DIALOG
                                dlg.cancel();
                            }
                        });
                        alertadd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                //On cancelling, reset as if button was not pressed.
                                hasCube = true;
                                cubeDrop.setText("Dropped Cube");
                                cubeDrop.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_drop_cube ,0 ,0);
                            }
                        });
                        final AlertDialog alertDialog = alertadd.create();
                        alertDialog.show();

                        Button dropAllySwitch = (Button) alertView.findViewById(R.id.cubeDrop_ally_switch);
                        Button dropNone = (Button) alertView.findViewById(R.id.cubeDrop_none);
                        Button dropOppSwitch = (Button) alertView.findViewById(R.id.cubeDrop_opp_switch);
                        Button dropExchange = (Button) alertView.findViewById(R.id.cubeDrop_exchange);
                        Button dropScale = (Button) alertView.findViewById(R.id.cubeDrop_scale);

                        dropNone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                logTimerEvent(Entry.EventType.DROPPED_CUBE_1,
                                        Entry.CubeDropType.NONE_0, getTimeStampFromInput(timePressed));
                                alertDialog.dismiss();
                            }
                        });
                        dropAllySwitch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                logTimerEvent(Entry.EventType.DROPPED_CUBE_1,
                                        Entry.CubeDropType.ALLY_SWITCH_1, getTimeStampFromInput(timePressed));
                                alertDialog.dismiss();
                            }
                        });
                        dropOppSwitch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                logTimerEvent(Entry.EventType.DROPPED_CUBE_1,
                                        Entry.CubeDropType.OPP_SWITCH_2, getTimeStampFromInput(timePressed));
                                alertDialog.dismiss();
                            }
                        });
                        dropScale.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                logTimerEvent(Entry.EventType.DROPPED_CUBE_1,
                                        Entry.CubeDropType.SCALE_3, getTimeStampFromInput(timePressed));
                                alertDialog.dismiss();
                            }
                        });
                        dropExchange.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                logTimerEvent(Entry.EventType.DROPPED_CUBE_1,
                                        Entry.CubeDropType.EXCHANGE_4, getTimeStampFromInput(timePressed));
                                alertDialog.dismiss();
                            }
                        });

                    } else {
                        hasCube = true;
                        logTimerEvent(Entry.EventType.PICKED_CUBE_0);
                        cubeDrop.setText("Dropped Cube");
                        cubeDrop.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_drop_cube ,0 ,0);
                    }
                    ListElementsArrayList.add(timer.getText().toString());
                }

            }
        });

        super.onStart();
    }

    public void updateButtonEnabled() {
        if(timerIsRunning) {
            cubeDrop.setEnabled(true);
        } else {
            cubeDrop.setEnabled(false);
        }
    }

    public void logTimerEvent(Entry.EventType type) {
        logTimerEvent(type, Entry.CubeDropType.NONE_0);
    }

    public void logTimerEvent(Entry.EventType type, Entry.CubeDropType cubeType) {
        ((TabbedActivity) getActivity()).newEntry.timeEvents.add(new Entry.TimeEvent(getCurrentTimeStamp(), type, cubeType));
        ((TabbedActivity) getActivity()).newEntry.timestamp = getCurrentTimeStamp();
        ((TabbedActivity) getActivity()).cacheEntry();
    }

    public void logTimerEvent(Entry.EventType type, Entry.CubeDropType cubeType, int timestamp) {
        ((TabbedActivity) getActivity()).newEntry.timeEvents.add(new Entry.TimeEvent(timestamp, type, cubeType));
        ((TabbedActivity) getActivity()).newEntry.timestamp = getCurrentTimeStamp();
        ((TabbedActivity) getActivity()).cacheEntry();
    }

    public int getCurrentTimeStamp() {
        return (int) (SystemClock.elapsedRealtime() - startTime + savedTime);
    }

    public int getTimeStampFromInput(long input) {
        return (int) (input - startTime + savedTime);
    }

}