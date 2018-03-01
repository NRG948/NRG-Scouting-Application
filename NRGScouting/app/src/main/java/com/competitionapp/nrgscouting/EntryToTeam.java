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
 * Created by Acchindra Thev on 2/26/18.
 */

public class EntryToTeam {

    String teamName = "";


    public static void setValues(Team a, Entry b) {
        a.teamName = b.teamName;
        /*
        a.totalAutoExchange += autoExchange;
        a.totalAutoDropScale += autoDropScale;
        a.totalAutoDropOpp += autoDropOpp;
        a.totalAutoDropAlly += autoDropAlly;
        a.totalAutoDropNone += autoDropNone;
        a.totalAutoCube += autoCube;
        a.totalAutoScaleEnd += autoScaleEnd;
        a.totalAutoAllyStart += autoAllyStart;
        a.totalAutoAllyEnd += autoAllyEnd;
        a.totalAutoOppStart += autoOppStart;
        a.totalAutoOppEnd += autoOppEnd;
        a.totalAutoScaleStart += autoScaleStart;
        //       Tele-Op
        a.totalAllyStart += allyStart;
        a.totalAllyEnd += allyEnd;
        a.totalOppStart += oppStart;
        a.totalOppEnd += oppEnd;
        a.totalScaleStart += scaleStart;
        a.totalScaleEnd += scaleEnd;
        a.totalCube += cube;
        a.totalExchange += exchange;
        a.totalDropScale += dropScale;
        a.totalDropOpp += dropOpp;
        a.totalDropAlly += dropAlly;
        a.totalDropNone += dropNone;
        a.totalBoost += boost;
        a.totalForce += force;
        //       Endgame
        a.totalDefense += defense;
        a.totalDeath += death;
        a.totalSoloClimb += soloClimb;
        a.totalAstClimb += astClimb;
        a.totalNeededAstClimb += neededAstClimb;
        a.totalLevitate += levitate;
        a.totalPenalties += penalties;
        a.totalYellowCard += yellowCard;
        a.totalRedCard += redCard;
        */
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
