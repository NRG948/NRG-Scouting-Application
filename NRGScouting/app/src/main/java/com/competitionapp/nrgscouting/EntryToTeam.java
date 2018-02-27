package com.competitionapp.nrgscouting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Acchindra Thev on 2/26/18.
 */

public class EntryToTeam extends Fragment {

    String teamName = "";
//       AUTONOMOUS
    int autoExchange = 0;
    int autoDropScale = 0;
    int autoDropOpp = 0;
    int autoDropAlly = 0;
    int autoDropNone = 0;
    int autoCube = 0;
    int autoScaleEnd = 0;
    int autoAllyStart = 0;
    int autoAllyEnd = 0;
    int autoOppStart = 0;
    int autoOppEnd = 0;
    int autoScaleStart = 0;
//       Tele-Op
    int allyStart = 0;
    int allyEnd = 0;
    int oppStart = 0;
    int oppEnd = 0;
    int scaleStart = 0;
    int scaleEnd = 0;
    int cube = 0;
    int exchange = 0;
    int dropScale = 0;
    int dropOpp = 0;
    int dropAlly = 0;
    int dropNone = 0;
    int boost = 0;
    int force = 0;
//       Endgame
    int defense;
    int death;
    int soloClimb;
    int astClimb;
    int neededAstClimb;
    int levitate;
    int penalties;
    int yellowCard;
    int redCard;
    int numTE;


    ArrayList<Entry> listOfEntriesInFile=new ArrayList<Entry>();
    ArrayList<Team> teams = new ArrayList<Team>();//List of summed up team data
    //list of team names to display
    ArrayList<String> matchTeams = new ArrayList<String>();
    ArrayAdapter<String> teamAdapter;
    View rootView;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_match, container, false);
        listView = (ListView)rootView.findViewById(R.id.teams);
        listView.setEmptyView(rootView.findViewById(R.id.emptyView));

        return rootView;
    }

    public void refreshFragment() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        if (!sharedPref.contains("MatchEntryList") || sharedPref.getStringSet("MatchEntryList", null) == null) {
            listView.setVisibility(View.GONE);
            rootView.findViewById(R.id.emptyView).setVisibility(View.VISIBLE);
            return;
        }

        listView.setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.emptyView).setVisibility(View.GONE);

        String exportedData = MatchFragment.exportEntryData(getActivity());
        ArrayList<Entry> entryList = Entry.getEntriesFromString(exportedData);
        listOfEntriesInFile = entryList;
        combineTeams();
        Algorithm ranker = new ScaleAlgorithm();

        for(Team a: teams){
            a.rankScore = ranker.rankScore(ranker.teleopScore(),ranker.autonomousScore());
        }

        Collections.sort(teams);
        for(Team x : teams) {
            //x.teamName
        }

        if(teams.size()>0) {
            teamAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, matchTeams);
            listView.setAdapter(teamAdapter);
        }
    }

    /*
    public void rank(View v){
        textView=(TextView)findViewById(R.id.text_view);
        EntryToTeam.teams=new ArrayList<>();
        EntryToTeam.combineTeams();
        Algorithm ranker = new Algorithm();
        for(Team a:EntryToTeam.teams){
            a.rankScore = ranker.rankScore(ranker.teleopScore(),ranker.autonomousScore());
        }
        Collections.sort(EntryToTeam.teams);
        String toDisplay="";
        for(Team a:EntryToTeam.teams){
            toDisplay+="Team:"+a.teamName+" Score:"+a.rankScore+"\n";
        }
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setLines(100);
        textView.setText(toDisplay);
        System.out.print(EntryToTeam.teams);
    }
    */

    public void setValues(Team a) {
        a.teamName = this.teamName;
        a.totalAutoExchange += autoExchange;
        a.totalAutoDropScale += autoDropScale;
        a.totalAutoDropOpp += autoDropOpp;
        a.totalAutoDropAlly += autoDropAlly;
        a.totalAutoDropNone += autoDropNone;
        a.totalAutoCube += autoCube;
        a.totalAutoScaleEnd += autoScaleEnd;
        a.totalAutoAllyStart += autoAllyStart;
        a.totalAutoAllyEnd += autoAllyEnd;
        a.totalAutoOppStart += autoOppStart;
        a.totalAutoOppEnd += autoOppEnd;
        a.totalAutoScaleStart += autoScaleStart;
        //       Tele-Op
        a.totalAllyStart += allyStart;
        a.totalAllyEnd += allyEnd;
        a.totalOppStart += oppStart;
        a.totalOppEnd += oppEnd;
        a.totalScaleStart += scaleStart;
        a.totalScaleEnd += scaleEnd;
        a.totalCube += cube;
        a.totalExchange += exchange;
        a.totalDropScale += dropScale;
        a.totalDropOpp += dropOpp;
        a.totalDropAlly += dropAlly;
        a.totalDropNone += dropNone;
        a.totalBoost += boost;
        a.totalForce += force;
        //       Endgame
        a.totalDefense += defense;
        a.totalDeath += death;
        a.totalSoloClimb += soloClimb;
        a.totalAstClimb += astClimb;
        a.totalNeededAstClimb += neededAstClimb;
        a.totalLevitate += levitate;
        a.totalPenalties += penalties;
        a.totalYellowCard += yellowCard;
        a.totalRedCard += redCard;
    }

    public ArrayList<Team> combineTeams() {
        for (Entry a : listOfEntriesInFile) {
            Team matchingTeam = teamNameThatMatches(a);
            if (matchingTeam != null) {
                setValues(matchingTeam);
            } else {
                Team newOne = new Team();
                teams.add(newOne);
                setValues(newOne);
            }
        }
        return teams;
    }

    public Team teamNameThatMatches(Entry a) {
        for (Team i : teams) {
            if (i.teamName.equals(a.teamName)) {
                return i;
            }
        }
        return null;
    }


    public void addEntry(Entry entry) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            if(sharedPref.contains(String.valueOf(entry))) {
                try {
                    JSONObject jsonObject = new JSONObject(entry.toString());
                    teamName = jsonObject.getString("teamName");
                    defense = jsonObject.getInt("defensiveStrategy");
                    death = jsonObject.getBoolean("death")? 1 : 0;
                    soloClimb = jsonObject.getBoolean("soloClimb")? 1 : 0;
                    astClimb = jsonObject.getBoolean("astClimb")? 1 : 0;
                    neededAstClimb= jsonObject.getBoolean("needAstClimb")? 1 : 0;
                    levitate = jsonObject.getBoolean("needLevitate")? 1 : 0;
                    yellowCard = jsonObject.getBoolean("cardYellow")? 1 : 0;
                    redCard = jsonObject.getBoolean("cardRed")? 1 : 0;
                    penalties = jsonObject.getInt("penalties");
                    numTE = jsonObject.getInt("numTE");

                    for (int i = 0; i < numTE; i++) {
                        if (jsonObject.getInt("TE"+i+"_0") <= 15000){
                            if (jsonObject.getInt("TE"+i+"_1") == 2){
                                autoAllyStart += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 3){
                                autoAllyEnd += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 4){
                                autoOppStart += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 5){
                                autoOppEnd += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 6){
                                autoScaleStart += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 7){
                                autoScaleEnd += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 0){
                                autoCube += jsonObject.getInt("TE"+i+"_0");
                                if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                                    autoCube = autoCube/2;
                                }
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 0)) {
                                autoDropNone += jsonObject.getInt("TE"+i+"_0");
                                if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                                    autoDropNone = autoDropNone/2;
                                }
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 1)) {
                                autoDropAlly += jsonObject.getInt("TE"+i+"_0");
                                if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                                    autoDropAlly = autoDropAlly/2;
                                }
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 2)) {
                                autoDropOpp += jsonObject.getInt("TE"+i+"_0");
                                if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                                    autoDropOpp = autoDropOpp/2;
                                }
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 3)) {
                                autoDropScale += jsonObject.getInt("TE"+i+"_0");
                                if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                                    autoDropScale = autoDropScale/2;
                                }
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 4)) {
                                autoExchange += jsonObject.getInt("TE"+i+"_0");
                                if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                                    autoExchange = autoExchange/2;
                                }
                            }
                        } else {

                            if (jsonObject.getInt("TE"+i+"_1") == 2){
                                allyStart += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 3){
                                allyEnd += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 4){
                                oppStart += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 5){
                                oppEnd += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 6){
                                scaleStart += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 7){
                                scaleEnd += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 0){
                                cube += jsonObject.getInt("TE"+i+"_0");
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 0)) {
                                dropNone += jsonObject.getInt("TE"+i+"_0");
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 1)) {
                                dropAlly += jsonObject.getInt("TE"+i+"_0");
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 2)) {
                                dropOpp += jsonObject.getInt("TE"+i+"_0");
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 3)) {
                                dropScale += jsonObject.getInt("TE"+i+"_0");
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 4)) {
                                exchange += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 8){
                                boost = jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 9){
                                force = jsonObject.getInt("TE"+i+"_0");
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for(Entry a:listOfEntriesInFile){
                    if(a.matchNumber==entry.matchNumber && a.teamName.equals(entry.teamName)){
                        listOfEntriesInFile.remove(a);
                        break;
                    }
                }
                listOfEntriesInFile.add(entry);
                teams=new ArrayList<>();
                combineTeams();
            }
    }

}
