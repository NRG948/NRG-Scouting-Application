import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class EntriesToTeamObjects {
	static ArrayList<Entry> listOfEntriesInFile;
	static ArrayList<Team> teams = new ArrayList<Team>();//List of summed up team data

	public static void getAllEntriesInFileIntoObjectForm(File entries, String fileText)
			throws FileNotFoundException {
		String[] lines = fileText.split("\tn");
		ArrayList<String> lineList = new ArrayList<String>();
		for (String a : lines) {
			if (!a.equals("")) {
				lineList.add(a);
			}
		}
		for (String line : lineList) {
			String[] properties = line.split("\t");
			Entry newEntry = new Entry();
			// USE AN INITIALIZER TO DO THE THING DONE BELOW USING A LOOP AND
			// WITH A LOT LESS CODE
			String[] matchNum = properties[0].split(":");
			newEntry.matchNumber = Integer.parseInt(matchNum[1]);
			String[] gears = properties[1].split(":");
			newEntry.gearsRetrieved = Integer.parseInt(gears[1]);
			String[] autoGears = properties[2].split(":");
			newEntry.autoGearsRetrieved = Integer.parseInt(autoGears[1]);
			String[] balls = properties[3].split(":");
			newEntry.ballsShot = Integer.parseInt(balls[1]);
			String[] autoBalls = properties[4].split(":");
			newEntry.autoBallsShot = Integer.parseInt(autoBalls[1]);
			String[] rating = properties[5].split(":");
			newEntry.rating = Integer.parseInt(rating[1].replaceAll(".0", ""));
			String[] death = properties[6].split(":");
			newEntry.death = Boolean.getBoolean(death[1]);
			String[] baseline = properties[7].split(":");
			newEntry.crossedBaseline = Boolean.getBoolean(baseline[1]);
			String[] rope = properties[8].split(":");
			newEntry.climbsRope = Boolean.getBoolean(rope[1]);
			String[] name = properties[9].split(":");
			newEntry.teamName = name[1];
			String[] position = properties[10].split(":");
			newEntry.position = getPosition(position[1]);
			// ADD THE NEW ENTRY IN THE LINE TO THE LISTOFENTRIES ARRAYLIST
			// OBJECT
			listOfEntriesInFile.add(newEntry);
		}
		combineTeams();
	}

	public static Entry.Position getPosition(String str) {
		if (str.equals("RED1"))
			return Entry.Position.RED1;
		else if (str.equals("RED2"))
			return Entry.Position.RED2;
		else if (str.equals("RED3"))
			return Entry.Position.RED3;
		else if (str.equals("BLUE1"))
			return Entry.Position.BLUE1;
		else if (str.equals("BLUE2"))
			return Entry.Position.BLUE2;
		return Entry.Position.BLUE3;
	}

	public static ArrayList<Team> combineTeams() {
		for (Entry a : listOfEntriesInFile) {
			Team matchingTeam = teamNameThatMatches(a);
			if(matchingTeam!=null){
				setValues(matchingTeam,a);
			}
			else{
				Team newOne = new Team();
				teams.add(newOne);
				setValues(newOne,a);
			}
		}
		return teams;
	}

	public static void setValues(Team b, Entry a) {
		b.name=a.teamName;
		if (a.position == Entry.Position.BLUE1) {
			b.totalBallsScoredBlue1Auto += a.autoBallsShot;
			b.totalGearsOnHookAutoMatchesBlue1 += a.gearsRetrieved;
			b.totalMatchesPlayedInBlue1+=1;
		} else if (a.position == Entry.Position.BLUE2) {
			b.totalBallsScoredBlue2Auto += a.autoBallsShot;
			b.totalGearsOnHookAutoMatchesBlue2 += a.gearsRetrieved;
			b.totalMatchesPlayedInBlue2+=1;
		} else if (a.position == Entry.Position.BLUE3) {
			b.totalBallsScoredBlue3Auto += a.autoBallsShot;
			b.totalGearsOnHookAutoMatchesBlue3 += a.gearsRetrieved;
			b.totalMatchesPlayedInBlue3+=1;
		} else if (a.position == Entry.Position.RED1) {
			b.totalBallsScoredRed1Auto += a.autoBallsShot;
			b.totalGearsOnHookAutoMatchesRed1 += a.gearsRetrieved;
			b.totalMatchesPlayedInRed1+=1;
		} else if (a.position == Entry.Position.RED2) {
			b.totalBallsScoredRed2Auto += a.autoBallsShot;
			b.totalGearsOnHookAutoMatchesRed2 += a.gearsRetrieved;
			b.totalMatchesPlayedInRed2+=1;
		} else if (a.position == Entry.Position.RED3) {
			b.totalBallsScoredRed3Auto += a.autoBallsShot;
			b.totalGearsOnHookAutoMatchesRed3 += a.gearsRetrieved;
			b.totalMatchesPlayedInRed3+=1;
		}
		b.totalMatchesPlayedInAllPositions+=1;
		b.totalBallsScoredTeleop += a.ballsShot;
		b.totalGearsRetrievedTeleop+=a.gearsRetrieved;
		b.totalDeaths += (a.death == true) ? 1 : 0;
		b.totalCrossesBaseLineMatches += (a.crossedBaseline == true) ? 1 : 0;
	}
	public static Team teamNameThatMatches(Entry a){
		for(Team i:teams){
			if(i.name.equals(a.teamName)){
				return i;
			}
		}
		return null;
	}
	public static String readFile(File entries) throws FileNotFoundException{
		String fileText="";
		Scanner scanner=new Scanner(entries);
		while(scanner.hasNextLine()){
			fileText+=scanner.nextLine();
		}
		return fileText;
	}
}
