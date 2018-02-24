package com.competitionapp.nrgscouting;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Acchindra Thev on 2/23/18.
 */

public class EntryToAlgorithm extends Fragment {

    String teamName;

//       AUTONOMOUS
    int totalAutoExchange = 0;
    int totalAutoDropScale = 0;
    int totalAutoDropOpp = 0;
    int totalAutoDropAlly = 0;
    int totalAutoDropNone = 0;
    int totalAutoCube = 0;
    int totalAutoScaleEnd = 0;
    int totalAutoAllyStart = 0;
    int totalAutoAllyEnd = 0;
    int totalAutoOppStart = 0;
    int totalAutoOppEnd = 0;
    int totalAutoScaleStart = 0;
//       Tele-Op
    int totalAllyStart = 0;
    int totalAllyEnd = 0;
    int totalOppStart = 0;
    int totalOppEnd = 0;
    int totalScaleStart = 0;
    int totalScaleEnd = 0;
    int totalCube = 0;
    int totalExchange = 0;
    int totalDropScale = 0;
    int totalDropOpp = 0;
    int totalDropAlly = 0;
    int totalDropNone = 0;
    int totalBoost = 0;
    int totalForce = 0;
//       Endgame
    int totalDefense;
    int totalDeath;
    int totalSoloClimb;
    int totalAstClimb;
    int totalNeededAstClimb;
    int totalLevitate;
    int totalPenalties;
    int totalYellowCard;
    int totalRedCard;
    int numTE;

// __________________________________________________________________________________________________________________________
// __________________________________________________________________________________________________________________________
// __________________________________________________________________________________________________________________________

    public void algorithmData() {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        for(String x : sharedPref.getStringSet("MatchEntryList", null)) {
            if(sharedPref.contains(x)) {
                try {
                    JSONObject jsonObject = new JSONObject(x);

                    teamName = jsonObject.getString("teamName");
                    totalDefense = jsonObject.getInt("defensiveStrategy");
                    totalDeath = jsonObject.getBoolean("death")? 1 : 0;
                    totalSoloClimb = jsonObject.getBoolean("soloClimb")? 1 : 0;
                    totalAstClimb = jsonObject.getBoolean("astClimb")? 1 : 0;
                    totalNeededAstClimb= jsonObject.getBoolean("needAstClimb")? 1 : 0;
                    totalLevitate = jsonObject.getBoolean("needLevitate")? 1 : 0;
                    totalYellowCard = jsonObject.getBoolean("cardYellow")? 1 : 0;
                    totalRedCard = jsonObject.getBoolean("cardRed")? 1 : 0;
                    totalPenalties = jsonObject.getInt("penalties");
                    numTE = jsonObject.getInt("numTE");

                    for (int i = 0; i < numTE; i++) {
                        if (jsonObject.getInt("TE"+i+"_0") <= 15000){
                            if (jsonObject.getInt("TE"+i+"_1") == 2){
                                totalAutoAllyStart += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 3){
                                totalAutoAllyEnd += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 4){
                                totalAutoOppStart += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 5){
                                totalAutoOppEnd += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 6){
                                totalAutoScaleStart += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 7){
                                totalAutoScaleEnd += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 0){
                                totalAutoCube += jsonObject.getInt("TE"+i+"_0");
                                if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                                    totalAutoCube = totalAutoCube/2;
                                }
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 0)) {
                                totalAutoDropNone += jsonObject.getInt("TE"+i+"_0");
                                if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                                    totalAutoDropNone = totalAutoDropNone/2;
                                }
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 1)) {
                                totalAutoDropAlly += jsonObject.getInt("TE"+i+"_0");
                                if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                                    totalAutoDropAlly = totalAutoDropAlly/2;
                                }
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 2)) {
                                totalAutoDropOpp += jsonObject.getInt("TE"+i+"_0");
                                if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                                    totalAutoDropOpp = totalAutoDropOpp/2;
                                }
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 3)) {
                                totalAutoDropScale += jsonObject.getInt("TE"+i+"_0");
                                if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                                    totalAutoDropScale = totalAutoDropScale/2;
                                }
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 4)) {
                                totalAutoExchange += jsonObject.getInt("TE"+i+"_0");
                                if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                                    totalAutoExchange = totalAutoExchange/2;
                                }
                            }
                        } else {

                            if (jsonObject.getInt("TE"+i+"_1") == 2){
                                totalAllyStart += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 3){
                                totalAllyEnd += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 4){
                                totalOppStart += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 5){
                                totalOppEnd += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 6){
                                totalScaleStart += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 7){
                                totalScaleEnd += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 0){
                                totalCube += jsonObject.getInt("TE"+i+"_0");
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 0)) {
                                totalDropNone += jsonObject.getInt("TE"+i+"_0");
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 1)) {
                                totalDropAlly += jsonObject.getInt("TE"+i+"_0");
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 2)) {
                                totalDropOpp += jsonObject.getInt("TE"+i+"_0");
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 3)) {
                                totalDropScale += jsonObject.getInt("TE"+i+"_0");
                            } else if ((jsonObject.getInt("TE"+i+"_1") == 1) && (jsonObject.getInt("TE"+i+"_2") == 4)) {
                                totalExchange += jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 8){
                                totalBoost = jsonObject.getInt("TE"+i+"_0");
                            } else if (jsonObject.getInt("TE"+i+"_1") == 9){
                                totalForce = jsonObject.getInt("TE"+i+"_0");
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
