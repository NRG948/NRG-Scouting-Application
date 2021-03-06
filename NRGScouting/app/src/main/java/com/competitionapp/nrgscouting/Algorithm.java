package com.competitionapp.nrgscouting;

import android.graphics.drawable.Icon;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Acchindra Thev on 2/22/18.
 */

public class Algorithm {

    int DefenseWeight = 0;
    int DeathsWeight = -20;
    int soloClimbWeight = 0;
    int astClimbWeight = 0;
    int baselineWeight = 10;
    int platformWeight = 0;
    int neededAstClimbWeight = 0;
    int levitateWeight = 0;
    int penaltiesWeight = -5;
    int yellowCardWeight = -20;
    int redCardWeight = -40;
    int switchScoreWeight = 0;
    int scaleScoreWeight = 0;
    int exhangeScoreWeight = 0;

    //       Total Timings
    int toNone = 0;
    int toAlly = 0;
    int toOpp = 0;
    int toScale = 0;
    int toExchange = 0;
    //       Endgame
    double defense;
    int death;
    int soloClimb;
    int astClimb;
    int neededAstClimb;
    int levitate;
    int penalties;
    int yellowCard;
    int redCard;
    int baseline;
    int platform;
    int numTE;
    int numAlly = 0;
    int numOpp = 0;
    int numScale = 0;
    int scaleScore = 0;
    int switchScore = 0;

    public String rankType() {
        return "None";
    }

    public double rankScore(Entry entry) {
        addEntry(entry);
        return ((redCardWeight*redCard) + (yellowCardWeight*yellowCard) + (penaltiesWeight*penalties) + (levitateWeight*levitate) + (-1*toNone) +
                (toExchange*exhangeScoreWeight) + (neededAstClimbWeight*neededAstClimb) + (platform*platformWeight) + (astClimbWeight*astClimb) + (soloClimbWeight*soloClimb)+
                (baseline*baselineWeight)+ (DeathsWeight*death) + (DefenseWeight*defense) + (switchScore*switchScoreWeight) + (scaleScore*scaleScoreWeight));
    }

    public void addEntry(Entry entry) {

        this.toNone = 0;
        this.toAlly = 0;
        this.toOpp = 0;
        this.toScale = 0;
        this.toExchange = 0;

        this.defense = 0;
        this.soloClimb = 0;
        this.astClimb = 0;
        this.neededAstClimb = 0;
        this.levitate = 0;
        this.penalties = 0;
        this.yellowCard = 0;
        this.redCard = 0;
        this.platform = 0;
        this.numTE = 0;
        this.numAlly = 0;
        this.numOpp = 0;
        this.numScale = 0;

        scaleScore = 0;
        switchScore = 0;

        try {
            JSONObject jsonObject = new JSONObject(entry.toString());
            defense = jsonObject.getDouble("rate");
            death = jsonObject.getBoolean("death") ? 1 : 0;
            soloClimb = jsonObject.getBoolean("soloClimb") ? 1 : 0;
            astClimb = jsonObject.getBoolean("astClimb") ? 1 : 0;
            neededAstClimb = jsonObject.getBoolean("needAstClimb") ? 1 : 0;
            levitate = jsonObject.getBoolean("needLevitate") ? 1 : 0;
            yellowCard = jsonObject.getBoolean("cardYellow") ? 1 : 0;
            redCard = jsonObject.getBoolean("cardRed") ? 1 : 0;
            penalties = jsonObject.getInt("penalties");
            baseline = jsonObject.getBoolean("baseline") ? 1 : 0;
            platform = jsonObject.getBoolean("platform") ? 1 : 0;
            numTE = jsonObject.getInt("numTE");

            for (int i = 0; i < numTE; i++) {
                if (jsonObject.getInt("TE" + i + "_1") == 10){
                    numTE--;
                }
            }
                for (int i = 1; i <= numTE; i++) {

                if (jsonObject.getInt("TE" + (i-1) + "_0") <= 15000) {

                    if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 0)) {
                        toNone += ((jsonObject.getInt("TE" + i + "_0") / 1000) - (jsonObject.getInt("TE" + (i-1) + "_0") / 1000))/2;
                        i++;

                    } else if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 1)) {
                        if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                            toAlly += ((jsonObject.getInt("TE" + i + "_0") / 1000) - (jsonObject.getInt("TE" + (i-1) + "_0") / 1000))/4;
                        }
                        toAlly += ((jsonObject.getInt("TE" + i + "_0") / 1000) - (jsonObject.getInt("TE" + (i-1) + "_0") / 1000))/2;
                        i++;
                        numAlly++;

                    } else if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 2)) {
                         if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                            toOpp += ((jsonObject.getInt("TE" + i + "_0") / 1000) - (jsonObject.getInt("TE" + (i-1) + "_0") / 1000))/4;
                        }
                        toOpp += ((jsonObject.getInt("TE" + i + "_0") / 1000) - (jsonObject.getInt("TE" + (i-1) + "_0") / 1000))/2;
                        i++;
                        numOpp++;

                    } else if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 3)) {
                        if ((jsonObject.getInt("position") == 1) || (jsonObject.getInt("position") == 4)) {
                            toScale += ((jsonObject.getInt("TE" + i + "_0") / 1000) - (jsonObject.getInt("TE" + (i-1) + "_0") / 1000))/4;
                        }
                        toScale += ((jsonObject.getInt("TE" + i + "_0") / 1000) - (jsonObject.getInt("TE" + (i-1) + "_0") / 1000))/2;
                        i++;
                        numScale++;

                    } else if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 4)) {
                        toExchange += 10;
                        i++;
                    }

                } else {

                    if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 0)) {
                        toNone += ((jsonObject.getInt("TE" + i + "_0") / 1000) - (jsonObject.getInt("TE" + (i - 1) + "_0") / 1000));
                        i++;

                    } else if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 1)) {
                        toAlly += ((jsonObject.getInt("TE" + i + "_0") / 1000) - (jsonObject.getInt("TE" + (i - 1) + "_0") / 1000));
                        i++;
                        numAlly++;

                    } else if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 2)) {
                        toOpp += ((jsonObject.getInt("TE" + i + "_0") / 1000) - (jsonObject.getInt("TE" + (i - 1) + "_0") / 1000));
                        i++;
                        numOpp++;

                    } else if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 3)) {
                        toScale += ((jsonObject.getInt("TE" + i + "_0") / 1000) - (jsonObject.getInt("TE" + (i - 1) + "_0") / 1000));
                        i++;
                        numScale++;

                    } else if ((jsonObject.getInt("TE" + i + "_1") == 1) && (jsonObject.getInt("TE" + i + "_2") == 4)) {
                        toExchange += 10;
                        i++;
                    }

                }
            }

            if(numAlly == 0){
                numAlly++;
            }
            if(numOpp == 0){
                numOpp++;
            }
            if(numScale == 0){
                numScale++;
            }
            if(numAlly > 0){
                toAlly = toAlly/numAlly;
            }
            if(numOpp > 0){
                toOpp = toOpp/numOpp;
            }
            if(numScale > 0){
                toScale = toScale/numScale;
            }
            if (toAlly+toOpp > 0){
                switchScore += (60 - (toAlly+toOpp));
            }
            if (toScale > 0){
                scaleScore += (60 - toScale);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
