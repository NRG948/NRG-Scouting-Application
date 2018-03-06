package com.competitionapp.nrgscouting;

/**
 * Created by valli on 7/19/17.
 * Remade by Peyton Lee 2/28/18.
 */

import android.support.annotation.NonNull;

import java.util.ArrayList;

public class Team implements Comparable<Team>{
    String teamName = "";
    ArrayList<Entry> teamEntryList = new ArrayList<Entry>();
    ArrayList<Double> teamEntryScores = new ArrayList<Double>();


    public double getRankScore() {
        double total = 0;
        for(double x : teamEntryScores) {
            total += x;
        }
        return total/teamEntryScores.size();
    }

    public void scoreEntries(Algorithm ranker) {
        teamEntryScores.clear();
        for(int i = 0; i < teamEntryList.size(); i++) {
            teamEntryScores.add(ranker.rankScore(teamEntryList.get(i)));
        }
    }

    @Override
    public int compareTo(@NonNull Team team) {
        if(team.getRankScore() > this.getRankScore()) {
            return -1;
        } else if (team.getRankScore() == this.getRankScore()) {
            return 0;
        } else {
            return 1;
        }
    }
}
