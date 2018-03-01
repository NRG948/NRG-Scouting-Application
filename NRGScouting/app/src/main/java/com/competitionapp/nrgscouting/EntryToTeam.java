package com.competitionapp.nrgscouting;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Acchindra Thev on 2/22/18.
 */

public class EntryToTeam {

    String teamName = "";


    public static void setValues(Team a, Entry b) {
        a.teamName = b.teamName;
    }

    public static ArrayList<Team> combineTeams(ArrayList<Team> teams, ArrayList<Entry> entryList) {
        for (Entry a : entryList) {
            Team matchingTeam = teamNameThatMatches(a, teams);
            if (matchingTeam != null) {
                matchingTeam.teamEntryList.add(a);
            } else {
                Team newOne = new Team();
                newOne.teamEntryList.add(a);
                newOne.teamName = a.teamName;
                teams.add(newOne);
            }
        }
        return teams;
    }

    public static Team teamNameThatMatches(Entry a, ArrayList<Team> teams) {
        for (Team i : teams) {
            if (i.teamName.equals(a.teamName)) {
                return i;
            }
        }
        return null;
    }
}
