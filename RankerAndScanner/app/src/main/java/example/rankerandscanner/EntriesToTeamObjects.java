package example.rankerandscanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class EntriesToTeamObjects {
    static ArrayList<Entry> listOfEntriesInFile=new ArrayList<Entry>();
    static ArrayList<Team> teams = new ArrayList<Team>();//List of summed up team data
    private static int leftOffAtIndex = 0;

    public static void addEntry(String QRString) {
        Entry entry = new Entry();
        entry.position = getPosition(getPositionFromCode(QRString));
        entry.teamName = String.valueOf(Integer.parseInt(QRString.substring(leftOffAtIndex, leftOffAtIndex + 5)));
        leftOffAtIndex += 5;
        entry.matchNumber = Integer.parseInt(QRString.substring(leftOffAtIndex, leftOffAtIndex + 5));
        leftOffAtIndex += 5;
        entry.gearsRetrieved = Integer.parseInt(QRString.substring(leftOffAtIndex, leftOffAtIndex + 2));
        leftOffAtIndex += 2;
        entry.ballsShot = Integer.parseInt(QRString.substring(leftOffAtIndex, leftOffAtIndex + 2));
        leftOffAtIndex += 2;
        entry.autoGearsRetrieved = Integer.parseInt(QRString.substring(leftOffAtIndex, leftOffAtIndex + 2));
        leftOffAtIndex += 2;
        entry.autoBallsShot = Integer.parseInt(QRString.substring(leftOffAtIndex, leftOffAtIndex + 2));
        leftOffAtIndex += 2;
        entry.rating = Double.parseDouble(QRString.substring(leftOffAtIndex, leftOffAtIndex + 3));
        leftOffAtIndex += 3;
        entry.crossedBaseline = (QRString.substring(leftOffAtIndex, leftOffAtIndex + 1).equals("T")) ? true : false;
        leftOffAtIndex += 1;
        entry.climbsRope = (QRString.substring(leftOffAtIndex, leftOffAtIndex + 1).equals("T")) ? true : false;
        listOfEntriesInFile.add(entry);
    }

    public static String getPositionFromCode(String QRString) {
        try {
            Integer.parseInt(QRString.substring(3, 4));
            leftOffAtIndex = 4;
            return QRString.substring(0, 4);
        } catch (NumberFormatException e) {
            leftOffAtIndex = 5;
            return QRString.substring(0, 5);
        }

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
            if (matchingTeam != null) {
                setValues(matchingTeam, a);
            } else {
                Team newOne = new Team();
                teams.add(newOne);
                setValues(newOne, a);
            }
        }
        return teams;
    }

    public static void setValues(Team b, Entry a) {
        b.name = a.teamName;
        if (a.position == Entry.Position.BLUE1) {
            b.totalBallsScoredBlue1Auto += a.autoBallsShot;
            b.totalGearsOnHookAutoMatchesBlue1 += a.autoGearsRetrieved;
            b.totalMatchesPlayedInBlue1 += 1;
        } else if (a.position == Entry.Position.BLUE2) {
            b.totalBallsScoredBlue2Auto += a.autoBallsShot;
            b.totalGearsOnHookAutoMatchesBlue2 += a.autoGearsRetrieved;
            b.totalMatchesPlayedInBlue2 += 1;
        } else if (a.position == Entry.Position.BLUE3) {
            b.totalBallsScoredBlue3Auto += a.autoBallsShot;
            b.totalGearsOnHookAutoMatchesBlue3 += a.autoGearsRetrieved;
            b.totalMatchesPlayedInBlue3 += 1;
        } else if (a.position == Entry.Position.RED1) {
            b.totalBallsScoredRed1Auto += a.autoBallsShot;
            b.totalGearsOnHookAutoMatchesRed1 += a.autoGearsRetrieved;
            b.totalMatchesPlayedInRed1 += 1;
        } else if (a.position == Entry.Position.RED2) {
            b.totalBallsScoredRed2Auto += a.autoBallsShot;
            b.totalGearsOnHookAutoMatchesRed2 += a.autoGearsRetrieved;
            b.totalMatchesPlayedInRed2 += 1;
        } else if (a.position == Entry.Position.RED3) {
            b.totalBallsScoredRed3Auto += a.autoBallsShot;
            b.totalGearsOnHookAutoMatchesRed3 += a.autoGearsRetrieved;
            b.totalMatchesPlayedInRed3 += 1;
        }
        b.totalRating += a.rating;
        b.totalMatchesPlayedInAllPositions += 1;
        b.totalBallsScoredTeleop += a.ballsShot;
        b.totalGearsRetrievedTeleop += a.gearsRetrieved;
        b.totalDeaths += (a.death == true) ? 1 : 0;
        b.totalCrossesBaseLineMatches += (a.crossedBaseline == true) ? 1 : 0;
    }

    public static Team teamNameThatMatches(Entry a) {
        for (Team i : teams) {
            if (i.name.equals(a.teamName)) {
                return i;
            }
        }
        return null;
    }
}
