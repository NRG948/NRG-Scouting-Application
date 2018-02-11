package com.competitionapp.nrgscouting;

import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Created by valli on 8/2/17. ***Verified and Approved*** 
 * Represents a Entry that can be written to the memory card
 */

public class Entry {
    enum Position{RED1,RED2,RED3,BLUE1,BLUE2,BLUE3};
    Position position;
    String teamName;
    int matchNumber;

    int foulPoints;

    @Override
    public String toString(){
        /*
        return "match:"+matchNumber+"\t"+"gearsRet:"+gearsRetrieved+"\t"+"autoGearsRet:"+autoGearsRetrieved+"\t"+"ballsShot:"+ballsShot+"\t"
                +"autoBallsShot:"+autoBallsShot+"\t"+"foulPoints:" + foulPoints + "\t" +"death:"+death+"\t"+"crossedBaseline:"+crossedBaseline+"\t"
                +"climbsRope:"+climbsRope+"\t"+"Name:"+teamName+"\t"+"position:"+position+"\t"+"yellowCard:"+yellowCard+ "\t"
                + "redCard:" + redCard + "\t" + "defensiveStrategy:" + defensiveStrategy + "\t"
                + "chainProblems:" + chainProblems + "\t" + "disconnectivity:" + disconnectivity + "\t"
                + "otherProblems:" + otherProblems + "\t" + "n";
                */

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("teamName", teamName);
            jsonObject.put("matchNumber", matchNumber);
            jsonObject.put("position", position.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    public Entry(){

    }
}
