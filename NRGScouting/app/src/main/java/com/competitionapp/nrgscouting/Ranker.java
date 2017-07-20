package com.competitionapp.nrgscouting;

import java.util.ArrayList;

/**
 * Created by valli on 7/19/17.
 */

public class Ranker {
    ArrayList<Team> sortedListOfTeams=new ArrayList<Team>();
    //AUTONOMOUS score weightages
    double ballsScoredWeightAuto=40;
    double gearsOnHookWeightAuto=20;
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
    //Autonomous and telop scores are calculated using weightages and rank score is calculated by assigning a weightage to both of thos scores
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
        return ((team.totalBallsScoredAuto/team.expectedTotalBallsScoredAuto)*ballsScoredWeightAuto)+
                ((team.totalCrossesBaseLineMatches/team.totalMatchesPlayed)*crossedBaseLineWeight)+
                ((team.totalGearsOnHookAutoMatches/team.totalMatchesPlayed)*gearsOnHookWeightAuto)+
                ((team.totalDeathsAutoMatches/team.totalMatchesPlayed)*totalDeathsWeightAuto);
    }
    public double teleopScore(Team team){
        return ((team.totalDeathsTeleopMatches/team.totalMatchesPlayed)*totalDeathsWeightTeleop)+
                ((team.totalBallsScoredTeleop/team.expectedTotalBallsScoredTeleop)*ballsScoredWeightTeleop)+
                ((team.totalRedOrYellowCardsMatches/team.totalMatchesPlayed)*totalYellowOrRedCardWeight)+
                ((team.noConflictBetweenTeams)*noConflictWeight)+((team.shooterRobot)*shooterbotWeight)+
                ((team.gearScoringRobot)*gearScoringbotWeight);
    }
    public double rankScore(double teleopScore,double autonomousScore){
        return (teleopScore*teleopScoreWeight)+(autonomousScore*autonomousScoreWeight);
    }
}
