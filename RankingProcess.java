import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class RankingProcess {
    
    public static void main(String[] args) {
        /**
         * The path for the folder that is storing all Entries text files goes
         * in the next line of code
         */
        File folder = new File("/Users/valli/Desktop/WorldOfEntries");
        File[] list = folder.listFiles();// List of files with Entries
        list[0] = new File("");//IF ALGORITHM IS RUN ON A MAC
        EntriesToTeamObjects.listOfEntriesInFile = new ArrayList<Entry>();
        for (File i : list) {
            try {// For the sake of satisfying compiler
                EntriesToTeamObjects.getAllEntriesInFileIntoObjectForm(i, EntriesToTeamObjects.readFile(i));
            } catch (FileNotFoundException e) {
                // Do nothing
            }
        }
        Ranker ranker = new Ranker(EntriesToTeamObjects.teams);
        /**
         * THE SORTED LIST IS THE ONE THAT IS PRINTED
         */
        for (Team team : ranker.sortedListOfTeams) {
            System.out.println("Score: " + team.rankScore + " Name:" + team.name);
        }
        /**
         * 
         */
    }
    
}

