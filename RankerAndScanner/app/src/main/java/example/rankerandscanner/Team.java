package example.rankerandscanner;

import android.support.annotation.NonNull;

public class Team implements Comparable<Team>{
    String name;
    int totalMatchesPlayedInAllPositions;
    int totalMatchesPlayedInRed1;
    int totalMatchesPlayedInRed2;
    int totalMatchesPlayedInRed3;
    int totalMatchesPlayedInBlue1;
    int totalMatchesPlayedInBlue2;
    int totalMatchesPlayedInBlue3;
    double totalRating;
    double rankScore;
    int totalBallsScoredRed1Auto;
    int totalBallsScoredRed2Auto;
    int totalBallsScoredRed3Auto;
    int totalBallsScoredBlue1Auto;
    int totalBallsScoredBlue2Auto;
    int totalBallsScoredBlue3Auto;
    int totalCrossesBaseLineMatches;
    int totalClimbsRopeMatches;
    int totalGearsOnHookAutoMatchesRed1;
    int totalGearsOnHookAutoMatchesRed2;
    int totalGearsOnHookAutoMatchesRed3;
    int totalGearsOnHookAutoMatchesBlue1;
    int totalGearsOnHookAutoMatchesBlue2;
    int totalGearsOnHookAutoMatchesBlue3;
    //Tele-operated
    int totalDeaths;
    int totalBallsScoredTeleop;
    int totalGearsRetrievedTeleop;
    int totalYelloOrRedCards;


    @Override
    public int compareTo(@NonNull Team team) {
        return (int)(team.rankScore-this.rankScore);
    }
}
