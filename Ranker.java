import java.util.ArrayList;

/**
 * Created by valli on 7/19/17.
 */

public class Ranker {
	ArrayList<Team> sortedListOfTeams = new ArrayList<Team>();
	// Auto [Red1,Red2,Red3,Blue1,Blue2,Blue3]
	int[] expectedTotalBallsScoredAuto = { 2, 3, 4, 5, 6, 7 };// Our expectation
																// for
																// them(Currently
																// random
																// numbers)
	int[] expectedTotalGearsAuto = { 4, 5, 6, 7, 8, 9 };
	int expectedTotalBallsScoredTeleop = 40;// Our expectation for them
	int expectedTotalGearsTeleop = 40;
	double[] ballsScoredWeightAuto = { 1, 2, 3, 4, 20, 10 };// [Red1,Red2,Red3,Blue1,Blue2,Blue3]
	double[] gearsOnHookWeightAuto = { 5, 5, 5, 1, 2, 2 };
	double crossedBaseLineWeight = 0.20;

	// TELEOP WEIGHTAGES
	double ballsScoredWeightTeleop = 40;
	double gearsWeightTeleop = 60;
	double totalDeathsWeight = -10;
	// FINAL WEIGHTAGES
	double teleopScoreWeight = 60;
	double autonomousScoreWeight = 40;

	// Weightages for each aspect of the comparison to see how much percent are
	// they a good match for us
	// Higher percent better match. This percent is the rankscore of Team
	// objects
	// Autonomous and telop scores are calculated using weightages and rank
	// score is calculated by assigning a weightage to both of thos scores
	Ranker(ArrayList<Team> teamsToRank) {
		// Loop through all teams
		double autoScore = 0;
		double teleopScore = 0;
		for (Team team : teamsToRank) {
			autoScore = autonomousScore(team);
			teleopScore = teleopScore(team);
			team.rankScore = rankScore(teleopScore, autoScore);
		}
		for (int i = 0; i < teamsToRank.size(); i++) {
			int maxIndex = i;
			for (Team a : teamsToRank) {
				if (a.rankScore > teamsToRank.get(maxIndex).rankScore) {
					maxIndex=teamsToRank.indexOf(a);
				}
			}
			Team oneOfTheBest=teamsToRank.get(maxIndex);
			teamsToRank.remove(maxIndex);
			teamsToRank.add(0, oneOfTheBest);
		}
		sortedListOfTeams=teamsToRank;
	}

	public double autonomousScore(Team team) {
		return ((team.totalBallsScoredRed1Auto / expectedTotalBallsScoredAuto[0]) * ballsScoredWeightAuto[0])
				+ ((team.totalBallsScoredRed2Auto / expectedTotalBallsScoredAuto[1]) * ballsScoredWeightAuto[1])
				+ ((team.totalBallsScoredRed3Auto / expectedTotalBallsScoredAuto[2]) * ballsScoredWeightAuto[2])
				+ ((team.totalBallsScoredBlue1Auto / expectedTotalBallsScoredAuto[3]) * ballsScoredWeightAuto[3])
				+ ((team.totalBallsScoredBlue2Auto / expectedTotalBallsScoredAuto[4]) * ballsScoredWeightAuto[4])
				+ ((team.totalBallsScoredBlue3Auto / expectedTotalBallsScoredAuto[5]) * ballsScoredWeightAuto[5])
				+ ((team.totalCrossesBaseLineMatches / team.totalMatchesPlayedInAllPositions) * crossedBaseLineWeight)
				+ autoGears(team);
	}

	public double teleopScore(Team team) {
		return ((team.totalDeaths / team.totalMatchesPlayedInAllPositions) * totalDeathsWeight)
				+ ((team.totalBallsScoredTeleop / expectedTotalBallsScoredTeleop) * ballsScoredWeightTeleop)
				+ ((team.totalGearsRetrievedTeleop / expectedTotalGearsTeleop) * gearsWeightTeleop);
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
