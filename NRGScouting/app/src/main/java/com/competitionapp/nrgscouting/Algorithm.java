package com.competitionapp.nrgscouting;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by Acchindra Thev on 2/22/18.
 */

public class Algorithm extends Team{
    
    //Auto
    int autoAllySwitch = 2; // Score per second for possesion of ally switch during auto.
    int autoScale = 4; // Score per second for possesion of scale during auto.
    int autoOppSwitch = 4; // Score per second for possesion of opp switch during auto.
    //                       [Red1,Red2,Red3,Blue1,Blue2,Blue3] **Positions**
    double autoCube = -0.5;

    //Tele-Op
    int allySwitch = 1; // Score per second for possesion of ally switch during teleop.
    int scale = 2; // Score per second for possesion of scale during teleop.
    int oppSwitch = 2; // Score per second for possesion of opponent switch during teleop.
    int force = 10; //Set Score.
    int boost = 10; //Set Score.
    int cube = -1;

    //Endgame
    int totalDefenseWeight = 15;
    int totalDeathsWeight = -20;
    int soloClimb = 30;
    int astClimb = 60;
    int neededAstClimb = 10;
    int levitate = 20;
    int penalties = -1;
    int yellowCard = -20;
    int redCard = -40;
    // FINAL WEIGHTAGES
    double teleopScoreWeight = 70;
    double autonomousScoreWeight = 30;

    public double autonomousScore() {
        return  ((totalAutoAllyEnd - totalAutoAllyStart)*autoAllySwitch) + ((totalAutoScaleEnd-totalAutoScaleStart)*autoScale) + ((totalAutoOppEnd-totalAutoOppStart)*autoOppSwitch)+
                (((totalAutoExchange+totalAutoDropScale+totalAutoDropOpp+totalAutoDropAlly+totalAutoDropNone) - totalAutoCube)*autoCube);
    }

    public double teleopScore() {
        return  ((redCard*totalRedCard) + (yellowCard*totalYellowCard) + (penalties*totalPenalties) + (levitate*totalLevitate) + (neededAstClimb*totalNeededAstClimb) +
                (astClimb*totalAstClimb) + (soloClimb*totalSoloClimb) + (totalDeathsWeight*totalDeath) + (totalDefenseWeight*totalDefense) + (boost*totalBoost) +
                (force*totalForce) + ((totalAllyEnd - totalAllyStart)*allySwitch) + ((totalOppEnd-totalOppStart)*oppSwitch) + ((totalScaleEnd - totalScaleStart)*scale)+
                (((totalExchange+totalDropScale+totalDropOpp+totalDropAlly+totalDropNone) - totalCube)*cube));
    }

    public double rankScore(double teleopScore, double autonomousScore) {
        return ((teleopScore/100.0) * teleopScoreWeight) + ((autonomousScore/100.0) * autonomousScoreWeight);
    }
}
