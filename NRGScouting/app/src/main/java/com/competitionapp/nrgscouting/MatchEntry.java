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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private CheckBox death;
    private CheckBox baseline;
    private CheckBox ropeClimb;
    protected String teamName="";
    Entry newEntry=null;
    private CheckBox yellowCard;
    private CheckBox redCard;
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
    private Button plusFoulPoints;
    private Button minusFoulPoints;

    private RadioGroup defensiveStrategy;
    private CheckBox chainProblems;
    private CheckBox disconnectivity;
    private CheckBox otherProblems;

    private EditText foulPoints;

    private static ArrayList<Entry> listOfEntriesInFile=new ArrayList<Entry>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((ActivityUtility) getActivity()).setActionBarTitle(teamName);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_entry, container, false);
    }
    
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
        plusFoulPoints = (Button) (getView().findViewById(R.id.plusFoulPoints));
        minusFoulPoints = (Button) (getView().findViewById(R.id.minusFoulPoints));
        yellowCard = (CheckBox)(getView().findViewById(R.id.cardyellow));
        redCard = (CheckBox) (getView().findViewById(R.id.cardred));
        defensiveStrategy = (RadioGroup) (getView().findViewById(R.id.defensiveStrategyStrength));
        chainProblems = (CheckBox) (getView().findViewById(R.id.chain));
        disconnectivity = (CheckBox) (getView().findViewById(R.id.disconnection));
        otherProblems = (CheckBox) (getView().findViewById(R.id.otherProblems));
        foulPoints = (EditText) (getView().findViewById(R.id.foulPoints));


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
                    gears.setText(String.valueOf(Math.abs(gears1)));
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
                    autoGears.setText(String.valueOf(Math.abs(gears1)));
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
                    ballsShot.setText(String.valueOf(Math.abs(gears1)));
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
                    autoBallsShot.setText(String.valueOf(Math.abs(gears1)));
                }
            }
        });
        plusFoulPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (foulPoints.getText().toString().equals("")) {
                    foulPoints.setText("1");
                } else {
                    int gears1 = Integer.parseInt(String.valueOf(foulPoints.getText()));
                    gears1++;
                    foulPoints.setText(String.valueOf(gears1));
                }
            }
        });
        minusFoulPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (foulPoints.getText().toString().equals("")) {
                } else {
                    int gears1 = Integer.parseInt(String.valueOf(foulPoints.getText()));
                    gears1--;
                    foulPoints.setText(String.valueOf(Math.abs(gears1)));
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

        View radioButton = defensiveStrategy.findViewById(defensiveStrategy.getCheckedRadioButtonId());
        int strategyButtonIndex = defensiveStrategy.indexOfChild(radioButton);

        newOne = new Entry(getPosition(position.getSelectedItemPosition()), String.valueOf(teamName),
                Integer.parseInt(String.valueOf(matchNumber.getText())), Integer.parseInt(String.valueOf(gears.getText())),
                Integer.parseInt(String.valueOf(ballsShot.getText())), Integer.parseInt(String.valueOf(autoGears.getText())),
                Integer.parseInt(String.valueOf(autoBallsShot.getText())), death.isChecked(), baseline.isChecked(),
                ropeClimb.isChecked(),yellowCard.isChecked(), redCard.isChecked(), strategyButtonIndex,
                chainProblems.isChecked(), disconnectivity.isChecked(), otherProblems.isChecked(),
                Integer.parseInt(String.valueOf((foulPoints.getText()))));
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
        View radioButton = defensiveStrategy.findViewById(defensiveStrategy.getCheckedRadioButtonId());
        int strategyButtonIndex = defensiveStrategy.indexOfChild(radioButton);

        newEntry = new Entry(getPosition(position.getSelectedItemPosition()), String.valueOf(teamName),
                Integer.parseInt(String.valueOf(matchNumber.getText())), Integer.parseInt(String.valueOf(gears.getText())),
                Integer.parseInt(String.valueOf(ballsShot.getText())), Integer.parseInt(String.valueOf(autoGears.getText())),
                Integer.parseInt(String.valueOf(autoBallsShot.getText())), death.isChecked(), baseline.isChecked(),
                ropeClimb.isChecked(),yellowCard.isChecked(),redCard.isChecked(), strategyButtonIndex,
                chainProblems.isChecked(), disconnectivity.isChecked(), otherProblems.isChecked(),
                Integer.parseInt(String.valueOf(foulPoints.getText())));
        mainSave();
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

        editor.putString("SAVED_VERSION", MainActivity.CURRENT_VERSION);

        editor.apply();
        Toast.makeText(this.getContext(), "New entry '" + keyName + "' saved.", Toast.LENGTH_LONG).show();
    }


    public void displayQRCode(Entry entry){
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
                +twoDigitization(a.gearsRetrieved)+twoDigitization(a.ballsShot)+twoDigitization(a.autoGearsRetrieved)+twoDigitization(a.autoBallsShot)+(a.crossedBaseline?"T":"F")+(a.climbsRope?"T":"F")
                +(a.death?"T":"F")+(a.yellowCard?"T":"F")+(a.redCard?"T":"F")+(a.chainProblems?"T":"F")+(a.disconnectivity?"T":"F")+(a.otherProblems?"T":"F")+twoDigitization(a.defensiveStrategy)
                +twoDigitization(a.foulPoints);
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

            String[] foulPoints = properties[5].split(":");
            newEntry.foulPoints = Integer.parseInt(foulPoints[1]);

            String[] death = properties[6].split(":");
            newEntry.death = Boolean.parseBoolean(death[1]);
            String[] baseline = properties[7].split(":");
            newEntry.crossedBaseline = Boolean.parseBoolean(baseline[1]);
            String[] rope = properties[8].split(":");
            newEntry.climbsRope = Boolean.parseBoolean(rope[1]);
            String[] name = properties[9].split(":");
            newEntry.teamName = name[1];
            String[] position = properties[10].split(":");
            newEntry.position = getPosition(position[1]);
            String[] ycard = properties[11].split(":");
            newEntry.yellowCard=Boolean.parseBoolean(ycard[1]);

            String[] rcard = properties[12].split(":");
            newEntry.redCard=Boolean.parseBoolean(rcard[1]);
            String[] dStrategy = properties[13].split(":");
            newEntry.defensiveStrategy=Integer.parseInt(dStrategy[1]);
            String[] chain = properties[14].split(":");
            newEntry.chainProblems=Boolean.parseBoolean(chain[1]);
            String[] disconnectivity = properties[15].split(":");
            newEntry.disconnectivity=Boolean.parseBoolean(disconnectivity[1]);
            String[] other = properties[16].split(":");
            newEntry.otherProblems=Boolean.parseBoolean(other[1]);

            //ADD THE NEW ENTRY IN THE LINE TO THE LISTOFENTRIES ARRAYLIST OBJECT
            listOfEntriesInFile.add(newEntry);
        }
        return listOfEntriesInFile;
    }

    public static String strategyStrengthFromInt(int strength) {
        if(strength <= 0 ) { return "Weak"; }
        else if (strength == 1) {return "Average"; }
        else { return "Strong";}
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
