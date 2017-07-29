package com.competitionapp.nrgscouting;

/**
 * Created by valli on 7/19/17.
 */

public class Team {
    String name;
    int totalMatchesPlayedInAllPositions;
    int totalMatchesPlayedInRed1;
    int totalMatchesPlayedInRed2;
    int totalMatchesPlayedInRed3;
    int totalMatchesPlayedInBlue1;
    int totalMatchesPlayedInBlue2;
    int totalMatchesPlayedInBlue3;
    double rankScore;
    //Auto                             [0,1,2,3,4,5] [Red1,Red2,Red3,Blue1,Blue2,Blue3]
    int[] expectedTotalBallsScoredAuto={2,3,4,5,6,7};//Our expectation for them(Currently random numbers)
    int totalBallsScoredRed1Auto;
    int totalBallsScoredRed2Auto;
    int totalBallsScoredRed3Auto;
    int totalBallsScoredBlue1Auto;
    int totalBallsScoredBlue2Auto;
    int totalBallsScoredBlue3Auto;
    int totalCrossesBaseLineMatches;
    int totalGearsOnHookAutoMatchesRed1;
    int totalGearsOnHookAutoMatchesRed2;
    int totalGearsOnHookAutoMatchesRed3;
    int totalGearsOnHookAutoMatchesBlue1;
    int totalGearsOnHookAutoMatchesBlue2;
    int totalGearsOnHookAutoMatchesBlue3;
    int totalDeathsAutoMatches;
    //Tele-operated
    int totalDeathsTeleopMatches;
    int expectedTotalBallsScoredTeleop;//Our expectation for them
    int totalBallsScoredTeleop;
    int totalRedOrYellowCardsMatches;
    int noConflictBetweenTeams;//1 or 0(True or false)
    int shooterRobot;//1 or 0(True or false)
    int gearScoringRobot;//1 or 0(True or false)

}
