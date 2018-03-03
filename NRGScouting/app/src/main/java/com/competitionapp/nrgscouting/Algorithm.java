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
    int autoAllySwitchWeight = 0; // Score per second for possesion of ally switch during auto.
    int autoScaleWeight = 0; // Score per second for possesion of scale during auto.
    int autoOppSwitchWeight = 0; // Score per second for possesion of opp switch during auto.
    double autoCubeWeight = -0.5;

    //Tele-Op
    int allySwitchWeight = 0; // Score per second for possesion of ally switch during teleop.
    int scaleWeight = 0; // Score per second for possesion of scale during teleop.
    int oppSwitchWeight = 0; // Score per second for possesion of opponent switch during teleop.
    int forceWeight = 10; //Set Score.
    int boostWeight = 10; //Set Score.
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

    public String rankType() {
        return "None";
    }
    
    public double autonomousScore() {
        return  ((autoAllyEnd - autoAllyStart)*autoAllySwitchWeight) + ((autoScaleEnd-autoScaleStart)*autoScaleWeight) + ((autoOppEnd-autoOppStart)*autoOppSwitchWeight)+
                (((autoExchange+autoDropScale+autoDropOpp+autoDropAlly+autoDropNone) - autoCube)*autoCubeWeight);
    }

    public double teleopScore() {
        return  ((redCardWeight*redCard) + (yellowCardWeight*yellowCard) + (penaltiesWeight*penalties) + (levitateWeight*levitate) + (neededAstClimbWeight*neededAstClimb) +
                (astClimbWeight*astClimb) + (soloClimbWeight*soloClimb) + (DeathsWeight*death) + (DefenseWeight*defense) + (boostWeight*boost) +
                (forceWeight*force) + ((allyEnd - allyStart)*allySwitchWeight) + ((oppEnd-oppStart)*oppSwitchWeight) + ((scaleEnd - scaleStart)*scaleWeight)+
                (((exchange+dropScale+dropOpp+dropAlly+dropNone) - cube)*cubeWeight));
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
                    if (jsonObject.getInt("TE" + i + "_1") == 2) {
                        autoAllyStart += (jsonObject.getInt("TE" + i + "_0")/1000);
                    } else if (jsonObject.getInt("TE" + i + "_1") == 3) {
                        autoAllyEnd += (jsonObject.getInt("TE" + i + "_0")/1000);
                    } else if (jsonObject.getInt("TE" + i + "_1") == 4) {
                        autoOppStart += (jsonObject.getInt("TE" + i + "_0")/1000);
                    } else if (jsonObject.getInt("TE" + i + "_1") == 5) {
                        autoOppEnd += (jsonObject.getInt("TE" + i + "_0")/1000);
                    } else if (jsonObject.getInt("TE" + i + "_1") == 6) {
                        autoScaleStart += (jsonObject.getInt("TE" + i + "_0")/1000);
                    } else if (jsonObject.getInt("TE" + i + "_1") == 7) {
                        autoScaleEnd += (jsonObject.getInt("TE" + i + "_0")/1000);
                    } else if (jsonObject.getInt("TE" + i + "_1") == 0) {
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
                        autoExchange += (jsonObject.getInt("TE" + i + "_0")/1000);
                        if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                            autoExchange = autoExchange / 2;
                        }
                    }
                } else {

                    if (jsonObject.getInt("TE" + i + "_1") == 2) {
                        allyStart += (jsonObject.getInt("TE" + i + "_0")/1000);
                    } else if (jsonObject.getInt("TE" + i + "_1") == 3) {
                        allyEnd += (jsonObject.getInt("TE" + i + "_0")/1000);
                    } else if (jsonObject.getInt("TE" + i + "_1") == 4) {
                        oppStart += (jsonObject.getInt("TE" + i + "_0")/1000);
                    } else if (jsonObject.getInt("TE" + i + "_1") == 5) {
                        oppEnd += (jsonObject.getInt("TE" + i + "_0")/1000);
                    } else if (jsonObject.getInt("TE" + i + "_1") == 6) {
                        scaleStart += (jsonObject.getInt("TE" + i + "_0")/1000);
                    } else if (jsonObject.getInt("TE" + i + "_1") == 7) {
                        scaleEnd += (jsonObject.getInt("TE" + i + "_0")/1000);
                    } else if (jsonObject.getInt("TE" + i + "_1") == 0) {
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
                        exchange += (jsonObject.getInt("TE" + i + "_0")/1000);
                    } else if (jsonObject.getInt("TE" + i + "_1") == 8) {
                        boost = (jsonObject.getInt("TE" + i + "_0")/1000);
                    } else if (jsonObject.getInt("TE" + i + "_1") == 9) {
                        force = (jsonObject.getInt("TE" + i + "_0")/1000);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
