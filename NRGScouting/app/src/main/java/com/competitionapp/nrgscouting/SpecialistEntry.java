package com.competitionapp.nrgscouting;

import android.app.Fragment;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;

/**
 * Created by valli on 8/12/17.
 */

public class SpecialistEntry extends Fragment {
    private Button mStartButton;
    private Button mPauseButton;
    private Button mResetButton;
    private Button PlusButton;
    private Button MinusButton;
    private Chronometer mchronometer;
    private EditText PilotFouls;
    private Button back;

    private long lastPause;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_specialist_entry, container, false);

        ((ActivityUtility) getActivity()).setActionBarTitle("Team Name Here");

        back = (Button) rootView.findViewById(R.id.back);
        mPauseButton = (Button) rootView.findViewById(R.id.Pause_Button);
        mchronometer = (Chronometer) rootView.findViewById(R.id.chronometer3);
        mStartButton = (Button) rootView.findViewById(R.id.StartButton);
        mResetButton = (Button) rootView.findViewById(R.id.Reset_Button);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastPause != 0) {
                    mchronometer.setBase(mchronometer.getBase() + SystemClock.elapsedRealtime() - lastPause);
                } else {
                    mchronometer.setBase(SystemClock.elapsedRealtime());
                }
                mchronometer.start();
                mStartButton.setEnabled(false);
                mPauseButton.setEnabled(true);
            }

        });

        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPause = SystemClock.elapsedRealtime();
                mchronometer.stop();
                mPauseButton.setEnabled(false);
                mStartButton.setEnabled(true);
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mchronometer.setBase(SystemClock.elapsedRealtime());
                lastPause = 0;
                mStartButton.setEnabled(true);
                mPauseButton.setEnabled(false);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        return rootView;
    }

    @Override
    public void onStart() {

        PilotFouls=(EditText)(getView().findViewById(R.id.pilotFouls));
        PlusButton = (Button)(getView().findViewById(R.id.plusPilotFouls));
        MinusButton = (Button)(getView().findViewById(R.id.minusPilotFouls));
        MinusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (PilotFouls.getText().toString().equals("")){
                    PilotFouls.setText("1");
                }
                else {
                    int PilotFouls1=Integer.parseInt(String.valueOf(PilotFouls.getText()));
                    PilotFouls1--;
                    if (PilotFouls1 < 0){
                        PilotFouls.setText(String.valueOf(PilotFouls1));
                    }
                    else{
                        PilotFouls.setText(String.valueOf(PilotFouls1));
                    }
                }
            }

        });
        PlusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (PilotFouls.getText().toString().equals("")){
                    PilotFouls.setText("1");
                }
                else {
                    int PilotFouls1=Integer.parseInt(String.valueOf(PilotFouls.getText()));
                    PilotFouls1++;
                    PilotFouls.setText(String.valueOf(PilotFouls1));
                }
            }

        });

        super.onStart();
    }

}
