package com.competitionapp.nrgscouting;

import android.app.Fragment;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Chronometer;

/**
 * Created by valli on 8/12/17.
 */

public class SpecialistEntry extends Fragment {
    private Button mStartButton;
    private Button mPuaseButton;
    private Button mResetButton;
    private Chronometer mchronometer;

    private long lastPuase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mStartButton = (Button) getView().findViewById (R.id.Start_Button);
        mPuaseButton = (Button) getView().findViewById (R.id.Puase_Button);
        mResetButton = (Button) getView().findViewById (R.id.Start_Button);
        mchronometer = (Chronometer) getView().findViewById(R.id.chronometer3);

        mStartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(lastPuase != 0){
                    mchronometer.setBase(mchronometer.getBase() + SystemClock.elapsedRealtime() - lastPuase);
                }else{
                    mchronometer.setBase(SystemClock.elapsedRealtime());
                }
                mchronometer.start();
                mStartButton.setEnabled(false);
                mPuaseButton.setEnabled(true);
            }

        });

        mPuaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPuase = SystemClock.elapsedRealtime();
                mchronometer.stop();
                mPuaseButton.setEnabled(false);
                mStartButton.setEnabled(true);
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mchronometer.setBase(SystemClock.elapsedRealtime());
                lastPuase = 0;
                mStartButton.setEnabled(true);
                mPuaseButton.setEnabled(false);
            }
        });



        return inflater.inflate(R.layout.fragment_specialist_entry, container, false);
    }
}
