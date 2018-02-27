package com.competitionapp.nrgscouting;

/**
 * Created by valli on 7/19/17.
 */

import android.support.annotation.NonNull;

public class Team implements Comparable<Team>{
    String teamName;
    double rankScore;

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


    @Override
    public int compareTo(@NonNull Team team) {
        if(team.rankScore > this.rankScore) {
            return -1;
        } else if (team.rankScore == this.rankScore) {
            return 0;
        } else {
            return 1;
        }
    }
}
