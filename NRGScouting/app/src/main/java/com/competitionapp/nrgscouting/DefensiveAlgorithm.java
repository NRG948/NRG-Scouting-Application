package com.competitionapp.nrgscouting;

/**
 * Created by acchindrathev on 2/24/18.
 */

public class DefensiveAlgorithm extends Algorithm {
    int totalDefenseWeight = 40;

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
