package example.rankerandscanner;

import java.util.ArrayList;

/**
 * Created by valli on 7/19/17.
 */

public class Ranker {
	ArrayList<Team> sortedListOfTeams = new ArrayList<Team>();
	// Auto [Red1,Red2,Red3,Blue1,Blue2,Blue3]
	int[] expectedTotalBallsScoredAuto = { (20*6), (20*6), (20*6), (20*6), (20*6), (20*6) };// Our expectation
																// for
																// them(Currently
																// random
																// numbers)
	int[] expectedTotalGearsAuto = { 6, 6, 6, 6, 6, 6 };
	int expectedTotalBallsScoredTeleop = 40;// Our expectation for them
	int expectedTotalGearsTeleop = 8;
	double[] ballsScoredWeightAuto = { 5, 5, 5, 5, 5, 5 };// [Red1,Red2,Red3,Blue1,Blue2,Blue3]
	double[] gearsOnHookWeightAuto = { 10, 10, 10, 10, 10, 10};
	double crossedBaseLineWeight = 5;
    double climbsRopeWeight = 5;
	// TELEOP WEIGHTAGES
	double ratingWeight=25;
	double ballsScoredWeightTeleop = 30;
	double gearsWeightTeleop = 50;
	double totalDeathsWeight = -5;
	double totalYellowCardsWeight = -5;
	double totalRedCardsWeight = -10;
	int totalDefenseWeight = 5;
	double totalChainProblemsWeight = -5;
	double totalDisconnectivityWeight = -10;
	double totalOtherProblemsWeight = -5;
	// FINAL WEIGHTAGES
	double teleopScoreWeight = 85;
	double autonomousScoreWeight = 15;

	// Weightages for each aspect of the comparison to see how much percent are
	// they a good match for us
	// Higher percent better match. This percent is the rankscore of Team
	// objects
	// Autonomous and telop scores are calculated using weightages and rank
	// score is calculated by assigning a weightage to both of those scores

	public double autonomousScore(Team team) {
		return ((team.totalBallsScoredRed1Auto / expectedTotalBallsScoredAuto[0]) * ballsScoredWeightAuto[0])
				+ ((team.totalBallsScoredRed2Auto / expectedTotalBallsScoredAuto[1]) * ballsScoredWeightAuto[1])
				+ ((team.totalBallsScoredRed3Auto / expectedTotalBallsScoredAuto[2]) * ballsScoredWeightAuto[2])
				+ ((team.totalBallsScoredBlue1Auto / expectedTotalBallsScoredAuto[3]) * ballsScoredWeightAuto[3])
				+ ((team.totalBallsScoredBlue2Auto / expectedTotalBallsScoredAuto[4]) * ballsScoredWeightAuto[4])
				+ ((team.totalBallsScoredBlue3Auto / expectedTotalBallsScoredAuto[5]) * ballsScoredWeightAuto[5])
				+ ((team.totalCrossesBaseLineMatches / team.getTotalMatchesPlayedInAllPositions()) * crossedBaseLineWeight)
				+ autoGears(team);
	}

	public double teleopScore(Team team) {
		return ((team.totalDeaths / team.getTotalMatchesPlayedInAllPositions()) * totalDeathsWeight)
				+ ((team.totalClimbsRopeMatches / team.getTotalMatchesPlayedInAllPositions()) * climbsRopeWeight)
				+ ((team.totalYellowCards / team.getTotalMatchesPlayedInAllPositions()) * totalYellowCardsWeight)
				+ ((team.totalRedCards / team.getTotalMatchesPlayedInAllPositions()) * totalRedCardsWeight)
				+ ((team.totalBallsScoredTeleop / expectedTotalBallsScoredTeleop) * ballsScoredWeightTeleop)
				+ ((team.totalGearsRetrievedTeleop / expectedTotalGearsTeleop) * gearsWeightTeleop)
				+ ((team.totalRating / (5.0 * team.getTotalMatchesPlayedInAllPositions())) * ratingWeight)
				+ ((team.totalDefense / team.getTotalMatchesPlayedInAllPositions()) * totalDefenseWeight)
				+ ((team.totalChainProblems / team.getTotalMatchesPlayedInAllPositions()) * totalChainProblemsWeight)
				+ ((team.totalDisconnectivity/ team.getTotalMatchesPlayedInAllPositions()) * totalDisconnectivityWeight)
				+ ((team.totalOtherProblems / team.getTotalMatchesPlayedInAllPositions()) * totalOtherProblemsWeight);
	}

	public double autoGears(Team team) {
		return ((team.totalGearsOnHookAutoMatchesRed1 / expectedTotalGearsAuto[0]) * gearsOnHookWeightAuto[0])
				+ ((team.totalGearsOnHookAutoMatchesRed2 / expectedTotalGearsAuto[1]) * gearsOnHookWeightAuto[1])
				+ ((team.totalGearsOnHookAutoMatchesRed3 / expectedTotalGearsAuto[2]) * gearsOnHookWeightAuto[2])
				+ ((team.totalGearsOnHookAutoMatchesBlue1 / expectedTotalGearsAuto[3]) * gearsOnHookWeightAuto[3])
				+ ((team.totalGearsOnHookAutoMatchesBlue2 / expectedTotalGearsAuto[4]) * gearsOnHookWeightAuto[4])
				+ ((team.totalGearsOnHookAutoMatchesBlue3 / expectedTotalGearsAuto[5]) * gearsOnHookWeightAuto[5]);
	}

	public double rankScore(double teleopScore, double autonomousScore) {
		return ((teleopScore/100.0) * teleopScoreWeight) + ((autonomousScore/100.0) * autonomousScoreWeight);
	}
}
