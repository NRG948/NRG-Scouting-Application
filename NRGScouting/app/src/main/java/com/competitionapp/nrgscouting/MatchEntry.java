package com.competitionapp.nrgscouting;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Exchanger;

public class MatchEntry extends Fragment {
    public static String fileText="";
    private EditText matchNumber;
    private Spinner position;
    private EditText gears;
    private EditText ballsShot;
    private EditText autoGears;
    private EditText autoBallsShot;
    private RatingBar rating;
    private CheckBox death;
    private CheckBox baseline;
    private CheckBox ropeClimb;
    protected String teamName="";
    Entry newEntry=null;
    private Button save;
    private Button back;
    private Button plusGears;
    private Button minusGears;
    private Button plusAutoGears;
    private Button minusAutoGears;
    private Button minusBallsShot;
    private Button plusBallsShot;
    private Button plusAutoBallsShot;
    private Button minusAutoBallsShot;
    private static ArrayList<Entry> listOfEntriesInFile=new ArrayList<Entry>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((ActivityUtility) getActivity()).setActionBarTitle(teamName);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_entry, container, false);
    }
<<<<<<< HEAD

    @Override
    public void onStart () {
        matchNumber = (EditText) (getView().findViewById(R.id.matchNumber));
        position = (Spinner) (getView().findViewById(R.id.teamPosition));

        //Get Current Default Position
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        if(sharedPref.contains("DefaultTeamPosition")) {
            position.setSelection(sharedPref.getInt("DefaultTeamPosition", 0));
        }

        gears = (EditText) (getView().findViewById(R.id.gearsRetrieved));
        ballsShot = (EditText) (getView().findViewById(R.id.ballsShot));
        autoGears = (EditText) (getView().findViewById(R.id.autoGearsRetrieved));
        autoBallsShot = (EditText) (getView().findViewById(R.id.autoBallsShot));
        ropeClimb = (CheckBox) (getView().findViewById((R.id.ropeClimb)));
        baseline = (CheckBox) (getView().findViewById(R.id.baseline));
        death = (CheckBox) (getView().findViewById((R.id.death)));
        rating = (RatingBar) (getView().findViewById(R.id.sportsmanship));
        save = (Button) (getView().findViewById(R.id.save));
        back = (Button) (getView().findViewById(R.id.back));
        plusGears = (Button) (getView().findViewById(R.id.plusGears));
        minusGears = (Button) (getView().findViewById(R.id.minusGears));
        plusAutoGears = (Button) (getView().findViewById(R.id.plusAutoGears));
        minusAutoGears = (Button) (getView().findViewById(R.id.minusAutoGears));
        minusBallsShot = (Button) (getView().findViewById(R.id.minusBallsShot));
        plusBallsShot = (Button) (getView().findViewById(R.id.plusBallsShot));
        plusAutoBallsShot = (Button) (getView().findViewById(R.id.plusAutoBallsShot));
        minusAutoBallsShot = (Button) (getView().findViewById(R.id.minusAutoBallsShot));

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Toast.makeText(getActivity(), "Trying to save...", Toast.LENGTH_SHORT);
                    SaveNewEntryToPrefs();

                    //initialCheck();
                    Toast.makeText(getActivity(), "Trying to exit...", Toast.LENGTH_SHORT);

                   /* MatchFragment matchFragment = new MatchFragment();
                    FragmentTransaction fragmentTransaction =
                            getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.fragment_container, matchFragment);
                    fragmentTransaction.commit(); */ //lol
                } catch (Exception e) {

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        plusGears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gears.getText().toString().equals("")) {
                    gears.setText("1");
                } else {
                    int gears1 = Integer.parseInt(String.valueOf(gears.getText()));
                    gears1++;
                    gears.setText(String.valueOf(gears1));
                }
            }
        });
        minusGears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gears.getText().toString().equals("")) {

                } else {
                    int gears1 = Integer.parseInt(String.valueOf(gears.getText()));
                    gears1--;
                    gears.setText(String.valueOf(gears1));
                }
            }
        });
        plusAutoGears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoGears.getText().toString().equals("")) {
                    autoGears.setText("1");
                } else {
                    int gears1 = Integer.parseInt(String.valueOf(autoGears.getText()));
                    gears1++;
                    autoGears.setText(String.valueOf(gears1));
                }
            }
        });
        minusAutoGears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoGears.getText().toString().equals("")) {

                } else {
                    int gears1 = Integer.parseInt(String.valueOf(autoGears.getText()));
                    gears1--;
                    autoGears.setText(String.valueOf(gears1));
                }
            }
        });
        minusBallsShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ballsShot.getText().toString().equals("")) {

                } else {
                    int gears1 = Integer.parseInt(String.valueOf(ballsShot.getText()));
                    gears1--;
                    ballsShot.setText(String.valueOf(gears1));
                }
            }
        });
        plusBallsShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ballsShot.getText().toString().equals("")) {
                    ballsShot.setText("1");
                } else {
                    int gears1 = Integer.parseInt(String.valueOf(ballsShot.getText()));
                    gears1++;
                    ballsShot.setText(String.valueOf(gears1));
                }
            }
        });
        plusAutoBallsShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoBallsShot.getText().toString().equals("")) {
                    autoBallsShot.setText("1");
                } else {
                    int gears1 = Integer.parseInt(String.valueOf(autoBallsShot.getText()));
                    gears1++;
                    autoBallsShot.setText(String.valueOf(gears1));
                }
            }
        });
        minusAutoBallsShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoBallsShot.getText().toString().equals("")) {
                } else {
                    int gears1 = Integer.parseInt(String.valueOf(autoBallsShot.getText()));
                    gears1--;
                    autoBallsShot.setText(String.valueOf(gears1));
                }
            }
        });
        super.onStart();
    }

    public void initialCheck()throws IOException{
        final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"/NRGScouting/");
        final File entryFile = new File(dir,"Entries.txt");
        try{
            FileInputStream fis = new FileInputStream(entryFile);
        }
        catch(FileNotFoundException h){
            dir.mkdir();
            entryFile.createNewFile();
        }
        fileText=getFileContent(entryFile);
        saveEntry();
    }


    public void saveEntry() throws IOException{
        final Entry newOne;
        ArrayList<Entry> listToWrite=new ArrayList<Entry>();
        final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"/NRGScouting/");
        final File entryFile = new File(dir,"Entries.txt");
        newOne = new Entry(getPosition(position.getSelectedItemPosition()), String.valueOf(teamName),
                Integer.parseInt(String.valueOf(matchNumber.getText())), Integer.parseInt(String.valueOf(gears.getText())),
                Integer.parseInt(String.valueOf(ballsShot.getText())), Integer.parseInt(String.valueOf(autoGears.getText())),
                Integer.parseInt(String.valueOf(autoBallsShot.getText())), rating.getNumStars(), death.isChecked(), baseline.isChecked(),
                ropeClimb.isChecked());
        final PrintStream printer = new PrintStream(entryFile);
            /**
             * Process 1 should run before Process 2
             */
        listOfEntriesInFile = getAllEntriesInFileIntoObjectForm(fileText);
        for (Entry a : listOfEntriesInFile) {
            if (a.matchNumber != newOne.matchNumber) {
                listToWrite.add(a);
            }
        }
        listToWrite.add(newOne);
        for (Entry a : listToWrite) {
            printer.println(a.toString());
        }
        MatchFragment matchFragment = new MatchFragment();
        FragmentTransaction fragmentTransaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, matchFragment);
        fragmentTransaction.commit();

    }

    public void SaveNewEntryToPrefs() {
        newEntry = new Entry(getPosition(position.getSelectedItemPosition()), String.valueOf(teamName),
                Integer.parseInt(String.valueOf(matchNumber.getText())), Integer.parseInt(String.valueOf(gears.getText())),
                Integer.parseInt(String.valueOf(ballsShot.getText())), Integer.parseInt(String.valueOf(autoGears.getText())),
                Integer.parseInt(String.valueOf(autoBallsShot.getText())), rating.getRating(), death.isChecked(), baseline.isChecked(),
                ropeClimb.isChecked());
        displayQRCode(newEntry);
    }


    public void mainSave() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        String keyName = getKeyName(newEntry);

        Set<String> entryList;

        if (sharedPref.contains("MatchEntryList") && sharedPref.getStringSet("MatchEntryList", null) != null) {
            entryList = sharedPref.getStringSet("MatchEntryList", null);
            entryList.add(keyName);
        } else {
            entryList = new HashSet<String>(Arrays.asList(new String[]{keyName}));
        }

        editor.putStringSet("MatchEntryList", entryList);
        editor.putString(keyName, newEntry.toString());
        editor.putInt(keyName + ":index", entryList.size() - 1);
        editor.putInt("DefaultTeamPosition", position.getSelectedItemPosition());

        editor.apply();
        Toast.makeText(this.getContext(), "New entry '" + keyName + "' saved.", Toast.LENGTH_LONG).show();
    }


    public void displayQRCode(Entry entry){
        mainSave();
        String code=getCode(entry);
        AlertDialog.Builder alertadd = new AlertDialog.Builder(getContext());
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View view = factory.inflate(R.layout.qr, null);
        ((ImageView)(view.findViewById(R.id.qrCodeImageView))).setImageBitmap(QRCodeGenerator.qrCodeMapFor(code));
        alertadd.setView(view);
        alertadd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int nothing) {
                ((TeamSearchPop) getActivity()).finishActivity();
            }
        });

        alertadd.show();
    }


    public String getCode(Entry a){
        return (a.position)+teamAndMatchNumber(a.teamName.substring(0,a.teamName.indexOf("-")-1))+teamAndMatchNumber(Integer.toString(a.matchNumber).substring(0,Integer.toString(a.matchNumber).length()))
                +twoDigitization(a.gearsRetrieved)+twoDigitization(a.ballsShot)+twoDigitization(a.autoGearsRetrieved)+twoDigitization(a.autoBallsShot)+rating.getRating()+(a.crossedBaseline?"T":"F")+(a.climbsRope?"T":"F");
    }


    public String twoDigitization(int number){
        return (Integer.toString(number).length()==2)?(Integer.toString(number)):("0"+number);
    }


    public String teamAndMatchNumber(String number){
        int zeroesToAdd=5-(number.length());
        String team="";
        for(int i=0;i<zeroesToAdd;i++){
            team+="0";
        }
        return team+=number;
    }


    public static String getKeyName(Entry entry) {
        return "match:"+entry.teamName + ":" + entry.matchNumber;
    }



    public static ArrayList<Entry> getAllEntriesInFileIntoObjectForm (String fileText){
        listOfEntriesInFile = new ArrayList<Entry>();
        String[] lines = fileText.split("\tn");
        ArrayList<String> lineList = new ArrayList<String>();
        for(String a:lines){
            if(!a.equals("")){
                lineList.add(a);
            }
        }
        for (String line : lineList) {
            String[] properties = line.split("\t");
            Entry newEntry = new Entry();
            //USE AN INITIALIZER TO DO THE THING DONE BELOW USING A LOOP AND WITH A LOT LESS CODE
            String[] matchNum = properties[0].split(":");
            newEntry.matchNumber = Integer.parseInt(matchNum[1]);
            String[] gears = properties[1].split(":");
            newEntry.gearsRetrieved = Integer.parseInt(gears[1]);
            String[] autoGears = properties[2].split(":");
            newEntry.autoGearsRetrieved = Integer.parseInt(autoGears[1]);
            String[] balls = properties[3].split(":");
            newEntry.ballsShot = Integer.parseInt(balls[1]);
            String[] autoBalls = properties[4].split(":");
            newEntry.autoBallsShot = Integer.parseInt(autoBalls[1]);
            String[] rating = properties[5].split(":");
            newEntry.rating = Double.parseDouble(rating[1].replaceAll(".0",""));
            String[] death = properties[6].split(":");
            newEntry.death = Boolean.getBoolean(death[1]);
            String[] baseline = properties[7].split(":");
            newEntry.crossedBaseline = Boolean.getBoolean(baseline[1]);
            String[] rope = properties[8].split(":");
            newEntry.climbsRope = Boolean.getBoolean(rope[1]);
            String[] name = properties[9].split(":");
            newEntry.teamName = name[1];
            String[] position = properties[10].split(":");
            newEntry.position = getPosition(position[1]);
            //ADD THE NEW ENTRY IN THE LINE TO THE LISTOFENTRIES ARRAYLIST OBJECT
            listOfEntriesInFile.add(newEntry);
        }
        return listOfEntriesInFile;
    }


    public Entry.Position getPosition (int row){
        if (row == 0)
            return Entry.Position.RED1;
        else if (row == 1)
            return Entry.Position.RED2;
        else if (row == 2)
            return Entry.Position.RED3;
        else if (row == 3)
            return Entry.Position.BLUE1;
        else if (row == 4)
            return Entry.Position.BLUE2;
        return Entry.Position.BLUE3;
    }


    public static Entry.Position getPosition(String str){
        if (str.equals("RED1"))
            return Entry.Position.RED1;
        else if (str.equals("RED2"))
            return Entry.Position.RED2;
        else if (str.equals("RED3"))
            return Entry.Position.RED3;
        else if (str.equals("BLUE1"))
            return Entry.Position.BLUE1;
        else if (str.equals("BLUE2"))
            return Entry.Position.BLUE2;
        return Entry.Position.BLUE3;
    }


    public static String getFileContent(File file) throws FileNotFoundException{
        try{
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder result = new StringBuilder();
        String oneLine="";
        while((oneLine = br.readLine()) != null){
            result.append(oneLine);
        }
        br.close();
        fis.close();
        isr.close();
        return result.toString();
        }
        catch (IOException e){
         return null;
        }
    }
}
