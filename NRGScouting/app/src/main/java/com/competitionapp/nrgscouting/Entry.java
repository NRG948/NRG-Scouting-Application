package com.competitionapp.nrgscouting;

import android.util.EventLog;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by valli on 8/2/17. ***Verified and Approved*** 
 * Represents a Entry that can be written to the memory card
 */

public class Entry {

    public enum EventType {
        PICKED_CUBE_0,
        DROPPED_CUBE_1,
        ALLY_START_2,
        ALLY_END_3,
        OPP_START_4,
        OPP_END_5,
        SCALE_START_6,
        SCALE_END_7,
        BOOST_8,
        FORCE_9,
        CLIMB_START_10
    }
    public enum CubeDropType {
        NONE_0,
        ALLY_SWITCH_1,
        OPP_SWITCH_2,
        SCALE_3,
        EXCHANGE_4,
    }

    //RED1 = 0, RED2 = 1, RED3 = 2
    //BLUE1 = 3, BLUE2 = 4, BLUE3 = 5
    //Based on position in the dropdown
    int position = 0;
    String teamName = "";
    int matchNumber = 0;
    int defensiveStrategy = 1;
    int penalties = 0;
    boolean death = false;
    boolean soloClimb = false;
    boolean astClimb = false;
    boolean needAstClimb = false;
    boolean needLevitate = false;
    boolean cardYellow = false;
    boolean cardRed = false;

    int timestamp = 0;

    ArrayList<TimeEvent> timeEvents = new ArrayList<TimeEvent>();
    //IMPORTANT NOTES FOR SAVING!!!
    //TE_0 is the timestamp of the timeEvent
    //TE_1 is the eventType
    //TE_2 is the type of cube drop (0 if not applicable)

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
            jsonObject.put("position", position);
            jsonObject.put("defensiveStrategy", 1);
            jsonObject.put("death", death);
            jsonObject.put("soloClimb", soloClimb);
            jsonObject.put("astClimb", astClimb);
            jsonObject.put("needAstClimb", needAstClimb);
            jsonObject.put("needLevitate", needLevitate);
            jsonObject.put("penalties", penalties);
            jsonObject.put("cardYellow", cardYellow);
            jsonObject.put("cardRed", cardRed);
            jsonObject.put("timestamp", timestamp);


            jsonObject.put("numTE", timeEvents.size());
            for(int i = 0; i < timeEvents.size(); i++) {
                jsonObject.put("TE" + i + "_0", timeEvents.get(i).timestamp);
                jsonObject.put("TE" + i + "_1", eventTypeToInt(timeEvents.get(i).type));
                jsonObject.put("TE" + i + "_2", cubeDropTypeToInt(timeEvents.get(i).cubeDropType));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    public static Entry retrieveFromString(String input) {
        Entry entry = new Entry();
        try {
            JSONObject jsonObject = new JSONObject(input);

            entry.position = jsonObject.getInt("position");
            entry.teamName = jsonObject.getString("teamName");
            entry.matchNumber = jsonObject.getInt("matchNumber");
            entry.defensiveStrategy = jsonObject.getInt("defensiveStrategy");
            entry.death = jsonObject.getBoolean("death");
            entry.soloClimb = jsonObject.getBoolean("soloClimb");
            entry.astClimb = jsonObject.getBoolean("astClimb");
            entry.needAstClimb = jsonObject.getBoolean("needAstClimb");
            entry.needLevitate = jsonObject.getBoolean("needLevitate");
            entry.penalties = jsonObject.getInt("penalties");
            entry.cardYellow = jsonObject.getBoolean("cardYellow");
            entry.cardRed = jsonObject.getBoolean("cardRed");
            entry.timestamp = jsonObject.getInt("timestamp");

            int numTE = jsonObject.getInt("numTE");
            for (int i = 0; i < numTE; i++) {
                entry.timeEvents.add(new TimeEvent(
                        jsonObject.getInt("TE"+i+"_0"),
                        intToEventType(jsonObject.getInt("TE"+i+"_1")),
                        intToCubeDropType(jsonObject.getInt("TE"+i+"_2"))
                ));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return entry;
    }


    public Entry(){

    }

    public void addTimeEvent(int timestamp, EventType eventType, CubeDropType cubeDropType) {
        timeEvents.add(new TimeEvent(timestamp, eventType, cubeDropType));
        return;
    }

    public static ArrayList<Entry> getEntriesFromString(String input) {
        String[] splitInput = input.split(MatchFragment.SPLITKEY);
        ArrayList<Entry> entryList = new ArrayList<Entry>();
        for(String x : splitInput) {
            entryList.add(Entry.retrieveFromString(x));
        }

        return entryList;
    }

    public static int eventTypeToInt(EventType eventType) {
        switch (eventType) {
            case PICKED_CUBE_0: return 0;
            case DROPPED_CUBE_1: return 1;
            case ALLY_START_2: return 2;
            case ALLY_END_3: return 3;
            case OPP_START_4: return 4;
            case OPP_END_5: return 5;
            case SCALE_START_6: return 6;
            case SCALE_END_7: return 7;
            case BOOST_8: return 8;
            case FORCE_9: return 9;
            case CLIMB_START_10: return 10;
        }
        return 0;
    }

    public static EventType intToEventType(int input) {
        switch (input) {
            case 0: return EventType.PICKED_CUBE_0;
            case 1: return EventType.DROPPED_CUBE_1;
            case 2: return EventType.ALLY_START_2;
            case 3: return EventType.ALLY_END_3;
            case 4: return EventType.OPP_START_4;
            case 5: return EventType.OPP_END_5;
            case 6: return EventType.SCALE_START_6;
            case 7: return EventType.SCALE_END_7;
            case 8: return EventType.BOOST_8;
            case 9: return EventType.FORCE_9;
            case 10: return EventType.CLIMB_START_10;
        }
        return EventType.PICKED_CUBE_0;
    }

    public static int cubeDropTypeToInt(CubeDropType cubeDropType) {
        switch (cubeDropType) {
            case NONE_0:return 0;
            case ALLY_SWITCH_1:return 1;
            case OPP_SWITCH_2:return 2;
            case SCALE_3:return 3;
            case EXCHANGE_4:return 4;
        }
        return 0;
    }

    public static CubeDropType intToCubeDropType(int input) {
        switch (input) {
            case 0: return CubeDropType.NONE_0;
            case 1: return CubeDropType.ALLY_SWITCH_1;
            case 2: return CubeDropType.OPP_SWITCH_2;
            case 3: return CubeDropType.SCALE_3;
            case 4: return CubeDropType.EXCHANGE_4;
        }
        return CubeDropType.NONE_0;
    }

    public static class TimeEvent {

        public int timestamp;
        public EventType type;
        public CubeDropType cubeDropType;

        public TimeEvent(int timestamp, EventType eventType, CubeDropType cubeDropType) {
            this.timestamp = timestamp;
            this.type = eventType;
            this.cubeDropType = cubeDropType;
        }

    }
}
