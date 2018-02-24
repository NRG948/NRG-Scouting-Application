package com.competitionapp.nrgscouting;

/**
 * Created by Acchindra Thev on 2/24/18.
 */

public class SwitchAlgorithm extends Algorithm {
    int autoAllySwitch = 4;
    int autoOppSwitch = 8;
    double autoCube = -0.5;
    int allySwitch = 2;
    int oppSwitch = 4;
    int cube = -1;

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
