package com.competitionapp.nrgscouting;

import android.graphics.drawable.Icon;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Acchindra Thev on 2/22/18.
 */

public class Algorithm{
    
    //Auto
    double autoCubeWeight = -0.5;

    //Tele-Op
    int cubeWeight = -1;

    //Endgame
    int DefenseWeight = 0;
    int DeathsWeight = -20;
    int soloClimbWeight = 0;
    int astClimbWeight = 0;
    int neededAstClimbWeight = 0;
    int levitateWeight = 0;
    int penaltiesWeight = -1;
    int yellowCardWeight = -20;
    int redCardWeight = -40;
    // FINAL WEIGHTAGES
    int teleopScoreWeight = 70;
    int autonomousScoreWeight = 30;

    //       AUTONOMOUS
    int autoCube = 0;
    int autoDropExchange = 0;
    int autoDropScale = 0;
    int autoDropOpp = 0;
    int autoDropAlly = 0;
    int autoDropNone = 0;
   
    //       Tele-Op
    
    int cube = 0;
    int dropExchange = 0;
    int dropScale = 0;
    int dropOpp = 0;
    int dropAlly = 0;
    int dropNone = 0;
   
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
    

    public String rankType() {
        return "None";
    }
    
    public double autonomousScore() {
        return (((autoDropExchange+autoDropScale+autoDropOpp+autoDropAlly+autoDropNone) - autoCube)*autoCubeWeight);
    }

    public double teleopScore() {
        return  ((redCardWeight*redCard) + (yellowCardWeight*yellowCard) + (penaltiesWeight*penalties) + (levitateWeight*levitate) + (neededAstClimbWeight*neededAstClimb) +
                (astClimbWeight*astClimb) + (soloClimbWeight*soloClimb) + (DeathsWeight*death) + (DefenseWeight*defense) +
                (((dropExchange+dropScale+dropOpp+dropAlly+dropNone) - cube)*cubeWeight));
    }

    public double rankScore(Entry entry) {
        addEntry(entry);
        return ((teleopScore()/100.0) * teleopScoreWeight) + ((autonomousScore()/100.0) * autonomousScoreWeight);
    }

    public void addEntry(Entry entry) {
        try {
            JSONObject jsonObject = new JSONObject(entry.toString());
            defense = jsonObject.getInt("defensiveStrategy");
            death = jsonObject.getBoolean("death") ? 1 : 0;
            soloClimb = jsonObject.getBoolean("soloClimb") ? 1 : 0;
            astClimb = jsonObject.getBoolean("astClimb") ? 1 : 0;
            neededAstClimb = jsonObject.getBoolean("needAstClimb") ? 1 : 0;
            levitate = jsonObject.getBoolean("needLevitate") ? 1 : 0;
            yellowCard = jsonObject.getBoolean("cardYellow") ? 1 : 0;
            redCard = jsonObject.getBoolean("cardRed") ? 1 : 0;
            penalties = jsonObject.getInt("penalties");
            numTE = jsonObject.getInt("numTE");

            for (int i = 0; i < numTE; i++) {
                if (jsonObject.getInt("TE" + i + "_0") <= 15000) {
                      if (jsonObject.getInt("TE" + i + "_1") == 0) {
                        autoCube += (jsonObject.getInt("TE" + i + "_0")/1000);
                        if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                            autoCube = autoCube / 2;
                        }
                    } else if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 0)) {
                        autoDropNone += (jsonObject.getInt("TE" + i + "_0")/1000);
                        if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                            autoDropNone = autoDropNone / 2;
                        }
                    } else if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 1)) {
                        autoDropAlly += (jsonObject.getInt("TE" + i + "_0")/1000);
                        if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                            autoDropAlly = autoDropAlly / 2;
                        }
                    } else if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 2)) {
                        autoDropOpp += (jsonObject.getInt("TE" + i + "_0")/1000);
                        if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                            autoDropOpp = autoDropOpp / 2;
                        }
                    } else if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 3)) {
                        autoDropScale += (jsonObject.getInt("TE" + i + "_0")/1000);
                        if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                            autoDropScale = autoDropScale / 2;
                        }
                    } else if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 4)) {
                        autoDropExchange += (jsonObject.getInt("TE" + i + "_0")/1000);
                        if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                            autoDropExchange = autoDropExchange / 2;
                        }
                    }
                } else {
                    if (jsonObject.getInt("TE" + i + "_1") == 0) {
                        cube += (jsonObject.getInt("TE" + i + "_0")/1000);
                    } else if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 0)) {
                        dropNone += (jsonObject.getInt("TE" + i + "_0")/1000);
                    } else if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 1)) {
                        dropAlly += (jsonObject.getInt("TE" + i + "_0")/1000);
                    } else if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 2)) {
                        dropOpp += (jsonObject.getInt("TE" + i + "_0")/1000);
                    } else if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 3)) {
                        dropScale += (jsonObject.getInt("TE" + i + "_0")/1000);
                    } else if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 4)) {
                        dropExchange += (jsonObject.getInt("TE" + i + "_0")/1000);
                    } 
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
