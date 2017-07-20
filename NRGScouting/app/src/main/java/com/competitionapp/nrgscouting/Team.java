package com.competitionapp.nrgscouting;

/**
 * Created by valli on 7/19/17.
 */

public class Team {
    int maxTotalPointsPossiblePerMatch;
    String name;
    int totalMatchesPlayed;
    double rankScore;
    //Auto
    int expectedTotalBallsScoredAuto;//Our expectation for them
    int totalBallsScoredAuto;
    int totalCrossesBaseLineMatches;
    int totalGearsOnHookAutoMatches;
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
