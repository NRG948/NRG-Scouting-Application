package example.rankerandscanner;

import java.util.ArrayList;

/**
 * Created by valli on 7/19/17. Revised/Approved by Acchin 10/22/17
 */

public class Ranker {
	ArrayList<Team> sortedListOfTeams = new ArrayList<Team>();
	// Auto [Red1,Red2,Red3,Blue1,Blue2,Blue3]
	int[] expectedTotalBallsScoredAuto = { (15), (15), (15), (15), (15), (15) };// Our expectation
																// for
																// them(Currently
																// random
																// numbers)
	int[] expectedTotalGearsAuto = { 1, 1, 1, 1, 1, 1 };
	int expectedTotalBallsScoredTeleop = 15;// Our expectation for them
	int expectedTotalGearsTeleop = 5;
	double[] ballsScoredWeightAuto = { 6, 6, 6, 6, 6, 6 };// [Red1,Red2,Red3,Blue1,Blue2,Blue3]
	double[] gearsOnHookWeightAuto = { 10, 10, 10, 10, 10, 10};
	double crossedBaseLineWeight = 8;
	// TELEOP WEIGHTAGES
	double climbsRopeWeight = 30;
	double ballsScoredWeightTeleop = 10;
	double gearsWeightTeleop = 20;
	double totalDeathsWeight = -15;
	double totalYellowCardsWeight = -20;
	int totalDefenseWeight = 8;
	double totalChainProblemsWeight = -15;
	double totalDisconnectivityWeight = -15;
	double totalOtherProblemsWeight = -15;
	// FINAL WEIGHTAGES
	double teleopScoreWeight = 70;
	double autonomousScoreWeight = 30;

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
				+ ((team.totalBallsScoredTeleop / expectedTotalBallsScoredTeleop) * ballsScoredWeightTeleop)
				+ ((team.totalGearsRetrievedTeleop / expectedTotalGearsTeleop) * gearsWeightTeleop)
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
