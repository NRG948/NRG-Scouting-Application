package com.competitionapp.nrgscouting;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by valli on 8/12/17.
 */

public class SpecialistEntry extends Fragment {
    public String teamName="";
    private Button mStartButton;
    private Button mPauseButton;
    private Button mResetButton;
    private Button PlusButton;
    private Button MinusButton;
    private Chronometer timer;
    private EditText PilotFouls;
    private Spinner positions;
    private EditText specMatchNum;
    private EditText intentionalFouls;
    private RatingBar driverSkill;
    private CheckBox autoGears;
    private TextView autoComments;
    private RatingBar GPRating;
    private RatingBar reliabilityBar;
    private RatingBar antagonism;
    private TextView specComments;
    private Button back;
    private Button save;
    private long lastPause;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_specialist_entry, container, false);

        ((ActivityUtility) getActivity()).setActionBarTitle(teamName);

        back = (Button) rootView.findViewById(R.id.back);
        save = (Button) rootView.findViewById(R.id.save);
        mPauseButton = (Button) rootView.findViewById(R.id.Pause_Button);
        timer = (Chronometer) rootView.findViewById(R.id.timer);
        mStartButton = (Button) rootView.findViewById(R.id.StartButton);
        mResetButton = (Button) rootView.findViewById(R.id.Reset_Button);
        positions = (Spinner) rootView.findViewById(R.id.specTeamPos);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        if(sharedPref.contains("DefaultTeamPosition")) {
            positions.setSelection(sharedPref.getInt("DefaultTeamPosition", 0));
        }

        specMatchNum = (EditText)  rootView.findViewById(R.id.specMatchNum);
        intentionalFouls = (EditText) rootView.findViewById(R.id.intFouls);
        driverSkill = (RatingBar) rootView.findViewById(R.id.SkillOfDriver);
        autoGears = (CheckBox) rootView.findViewById(R.id.GearsInAuto);
        autoComments = (EditText) rootView.findViewById(R.id.CommentsAboutAuto);
        GPRating = (RatingBar) rootView.findViewById(R.id.GPBar);
        reliabilityBar = (RatingBar) rootView.findViewById(R.id.ReliabilityRatingBar);
        antagonism = (RatingBar) rootView.findViewById(R.id.AntRatingBar);
        specComments = (EditText) rootView.findViewById(R.id.SpecComments);
        

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastPause != 0) {
                    timer.setBase(timer.getBase() + SystemClock.elapsedRealtime() - lastPause);
                } else {
                    timer.setBase(SystemClock.elapsedRealtime());
                }
                timer.start();
                mStartButton.setEnabled(false);
                mPauseButton.setEnabled(true);
            }

        });

        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPause = SystemClock.elapsedRealtime();
                timer.stop();
                mPauseButton.setEnabled(false);
                mStartButton.setEnabled(true);
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.setBase(SystemClock.elapsedRealtime());
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

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {

                    //initialCheck();

                    SaveNewEntryToPrefs();

                    ((TeamSearchPopSpec) getActivity()).finishActivity();

                       /* MatchFragment matchFragment = new MatchFragment();
                        FragmentTransaction fragmentTransaction =
                                getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.fragment_container, matchFragment);
                        fragmentTransaction.commit(); */ //lol
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        super.onStart();
    }

    public int getTimeFromChronometer(Chronometer mChronometer) {
        //returns in MILLISECONDS

        int stoppedMilliseconds = 0;

        String chronoText = mChronometer.getText().toString();
        String array[] = chronoText.split(":");
        if (array.length == 2) {
            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
                    + Integer.parseInt(array[1]) * 1000;
        } else if (array.length == 3) {
            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
                    + Integer.parseInt(array[1]) * 60 * 1000
                    + Integer.parseInt(array[2]) * 1000;
        }

        return stoppedMilliseconds;
    }

    public void SaveNewEntryToPrefs() {

        SEntry entry = new SEntry();
        entry.teamName = String.valueOf(this.teamName);
        entry.position = SEntry.getPosition(positions.getVerticalScrollbarPosition());
        entry.matchNumber = Integer.parseInt(String.valueOf(specMatchNum.getText()));
        entry.pilotFouls = Integer.parseInt(String.valueOf(PilotFouls.getText()));
        entry.intentFouls = Integer.parseInt(String.valueOf(intentionalFouls.getText()));
        entry.driverSkill = Double.parseDouble(String.valueOf(driverSkill.getRating()));
        entry.autoGear = autoGears.isChecked();
        entry.autoGearComment = String.valueOf(autoComments.getText());
        entry.GPRating = GPRating.getRating();
        entry.reliability = reliabilityBar.getRating();
        entry.antagonism = antagonism.getRating();
        entry.ropeDropTime = getTimeFromChronometer(timer);
        entry.specComments = String.valueOf(this.specComments.getText());


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        String keyName = getKeyName(entry);

        Set<String> entryList;

        if (sharedPref.contains("SpecialistEntryList") &&
                sharedPref.getStringSet("SpecialistEntryList", null) != null){
            entryList = sharedPref.getStringSet("SpecialistEntryList", null);
            entryList.add(keyName);

        } else {
            entryList =  new HashSet<String>(Arrays.asList(new String[] {keyName}));
        }

        editor.putStringSet("SpecialistEntryList", entryList);
        editor.putString(keyName, entry.toString());
        editor.putInt(keyName + ":index", entryList.size() - 1);
        editor.putInt("DefaultTeamPosition", positions.getSelectedItemPosition());

        Toast.makeText(getActivity().getApplicationContext(), "New entry '" + keyName + "' saved.", Toast.LENGTH_LONG).show();

        editor.putInt("DefaultTeamPosition", positions.getSelectedItemPosition());

        editor.apply();

    }

    public static ArrayList<SEntry> getAllEntriesInFileIntoObjectForm(String fileText) {
        ArrayList<SEntry> entries = new ArrayList<SEntry>();
        String[] lines = fileText.split("\tn");
        ArrayList<String> lineList = new ArrayList<String>();
        for(String a:lines){
            if(!a.equals("")){
                lineList.add(a);
            }
        }
        for (String line : lineList) {
            SEntry newEntry = SEntry.Deserialize(line);
            entries.add(newEntry);
        }

        return entries;
    }

    public static String getKeyName(SEntry entry) {
        return "specialist:" + entry.teamName + ":" + entry.matchNumber;
    }

}
