package com.competitionapp.nrgscouting;

import java.util.ArrayList;

/**
 * Created by valli on 7/19/17.
 */

public class Ranker {
    ArrayList<Team> sortedListOfTeams=new ArrayList<Team>();
    //AUTONOMOUS score weightages                  //[  0 , 1 ,  2  ,  3  ,  4  ,  5  ]
    double[] ballsScoredWeightAuto={1,2,3,4,20,10};//[Red1,Red2,Red3,Blue1,Blue2,Blue3]
    double[] gearsOnHookWeightAuto={5,5,5,1,2,2};
    double crossedBaseLineWeight=20;
    double totalDeathsWeightAuto=-10;
    //TELEOP WEIGHTAGES
    double totalDeathsWeightTeleop=-10;
    double ballsScoredWeightTeleop=40;
    double totalYellowOrRedCardWeight=-10;
    double noConflictWeight=10;
    double shooterbotWeight=30;
    double gearScoringbotWeight=20;
    //FINAL WEIGHTAGES
    double teleopScoreWeight=60;
    double autonomousScoreWeight=40;
    //Weightages for each aspect of the comparison to see how much percent are they a good match for us
    //Higher percent better match. This percent is the rankscore of Team objects
    //Autonomous and telop scores are calculated using weightages and rank score is calculated by assigning a weightage to both of those scores
    Ranker(ArrayList<Team> teamsToRank){
        //Loop through all teams
        double autoScore=0;
        double teleopScore=0;
        for(Team team : teamsToRank){
            autoScore=autonomousScore(team);
            teleopScore=teleopScore(team);
            team.rankScore=rankScore(teleopScore,autoScore);
        }
        ArrayList<Team> sorted=new ArrayList<Team>();
        for(int i=0;i<teamsToRank.size();i++){
            int maxIndex=0;
            for(int j=0;j<teamsToRank.size();j++){
                if(teamsToRank.get(maxIndex).rankScore<teamsToRank.get(j).rankScore){
                    maxIndex=j;
                }
            }
            sorted.add(teamsToRank.get(maxIndex));
            teamsToRank.remove(teamsToRank.remove(maxIndex));
        }
        sortedListOfTeams=sorted;
    }
    public double autonomousScore(Team team){
        return ((team.totalBallsScoredRed1Auto/team.expectedTotalBallsScoredAuto[0])*ballsScoredWeightAuto[0])+
                ((team.totalBallsScoredRed2Auto/team.expectedTotalBallsScoredAuto[1])*ballsScoredWeightAuto[1])+
                ((team.totalBallsScoredRed3Auto/team.expectedTotalBallsScoredAuto[2])*ballsScoredWeightAuto[2])+
                ((team.totalBallsScoredBlue1Auto/team.expectedTotalBallsScoredAuto[3])*ballsScoredWeightAuto[3])+
                ((team.totalBallsScoredBlue2Auto/team.expectedTotalBallsScoredAuto[4])*ballsScoredWeightAuto[4])+
                ((team.totalBallsScoredBlue3Auto/team.expectedTotalBallsScoredAuto[5])*ballsScoredWeightAuto[5])+
                ((team.totalCrossesBaseLineMatches/team.totalMatchesPlayedInAllPositions)*crossedBaseLineWeight)+
                ((team.totalGearsOnHookAutoMatchesRed1/team.totalMatchesPlayedInRed1)*gearsOnHookWeightAuto[0])+
                ((team.totalGearsOnHookAutoMatchesRed2/team.totalMatchesPlayedInRed2)*gearsOnHookWeightAuto[1])+
                ((team.totalGearsOnHookAutoMatchesRed3/team.totalMatchesPlayedInRed3)*gearsOnHookWeightAuto[2])+
                ((team.totalGearsOnHookAutoMatchesBlue1/team.totalMatchesPlayedInBlue1)*gearsOnHookWeightAuto[3])+
                ((team.totalGearsOnHookAutoMatchesBlue2/team.totalMatchesPlayedInBlue2)*gearsOnHookWeightAuto[4])+
                ((team.totalGearsOnHookAutoMatchesBlue3/team.totalMatchesPlayedInBlue3)*gearsOnHookWeightAuto[5])+
                ((team.totalDeathsAutoMatches/team.totalMatchesPlayedInAllPositions)*totalDeathsWeightAuto);
    }
    public double teleopScore(Team team){
        return ((team.totalDeathsTeleopMatches/team.totalMatchesPlayedInAllPositions)*totalDeathsWeightTeleop)+
                ((team.totalBallsScoredTeleop/team.expectedTotalBallsScoredTeleop)*ballsScoredWeightTeleop)+
                ((team.totalRedOrYellowCardsMatches/team.totalMatchesPlayedInAllPositions)*totalYellowOrRedCardWeight)+
                ((team.noConflictBetweenTeams)*noConflictWeight)+((team.shooterRobot)*shooterbotWeight)+
                ((team.gearScoringRobot)*gearScoringbotWeight);
    }
    public double rankScore(double teleopScore,double autonomousScore){
        return (teleopScore*teleopScoreWeight)+(autonomousScore*autonomousScoreWeight);
    }
}
