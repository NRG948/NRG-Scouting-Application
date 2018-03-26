package com.competitionapp.nrgscouting;

import android.support.annotation.NonNull;
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
 * Created by valli on 8/2/17.
 * Represents a Entry that can be written to the memory card
 */

public class Entry implements Comparable<Entry>{

    public enum EventType {
        PICKED_CUBE_0,
        DROPPED_CUBE_1,
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
    String comments = "";
    int matchNumber = 0;
    double rate = 0;
    int penalties = 0;
    boolean baseline = false;
    boolean death = false;
    boolean platform = false;
    boolean soloClimb = false;
    boolean astClimb = false;
    boolean needAstClimb = false;
    boolean needLevitate = false;
    boolean cardYellow = false;
    boolean cardRed = false;
    boolean autoSwitch = false;
    boolean autoScale = false;
    boolean autoFarSwitch = false;
    boolean autoFarScale = false;
    int timestamp = 0;

    static int TOTAL_MATCH_TIME = 150000;

    ArrayList<TimeEvent> timeEvents = new ArrayList<TimeEvent>();
    //IMPORTANT NOTES FOR SAVING!!!
    //TE_0 is the timestamp of the timeEvent
    //TE_1 is the eventType
    //TE_2 is the type of cube drop (0 if not applicable)

    @Override
    public String toString(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("teamName", teamName);
            jsonObject.put("matchNumber", matchNumber);
            jsonObject.put("position", position);
            jsonObject.put("rate", rate);
            jsonObject.put("platform", platform);
            jsonObject.put("death", death);
            jsonObject.put("soloClimb", soloClimb);
            jsonObject.put("baseline", baseline);
            jsonObject.put("astClimb", astClimb);
            jsonObject.put("needAstClimb", needAstClimb);
            jsonObject.put("needLevitate", needLevitate);
            jsonObject.put("penalties", penalties);
            jsonObject.put("cardYellow", cardYellow);
            jsonObject.put("cardRed", cardRed);
            jsonObject.put("autoSwitch", autoSwitch);
            jsonObject.put("autoScale", autoScale);
            jsonObject.put("autoFarSwitch", autoFarSwitch);
            jsonObject.put("autoFarScale", autoFarScale);
            jsonObject.put("timestamp", timestamp);


            jsonObject.put("numTE", timeEvents.size());
            for(int i = 0; i < timeEvents.size(); i++) {
                jsonObject.put("TE" + i + "_0", timeEvents.get(i).timestamp);
                jsonObject.put("TE" + i + "_1", eventTypeToInt(timeEvents.get(i).type));
                jsonObject.put("TE" + i + "_2", cubeDropTypeToInt(timeEvents.get(i).cubeDropType));
            }
            jsonObject.put("comments", comments);

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
            entry.baseline = jsonObject.getBoolean("baseline");
            entry.teamName = jsonObject.getString("teamName");
            entry.matchNumber = jsonObject.getInt("matchNumber");
            entry.rate = jsonObject.getDouble("rate");
            entry.death = jsonObject.getBoolean("death");
            entry.soloClimb = jsonObject.getBoolean("soloClimb");
            entry.astClimb = jsonObject.getBoolean("astClimb");
            entry.needAstClimb = jsonObject.getBoolean("needAstClimb");
            entry.needLevitate = jsonObject.getBoolean("needLevitate");
            entry.penalties = jsonObject.getInt("penalties");
            entry.cardYellow = jsonObject.getBoolean("cardYellow");
            entry.cardRed = jsonObject.getBoolean("cardRed");
            entry.autoSwitch = jsonObject.getBoolean("autoSwitch");
            entry.autoScale = jsonObject.getBoolean("autoScale");
            entry.autoFarSwitch = jsonObject.getBoolean("autoFarSwitch");
            entry.autoFarScale = jsonObject.getBoolean("autoFarScale");
            entry.timestamp = jsonObject.getInt("timestamp");
            entry.platform = jsonObject.getBoolean("platform");

            int numTE = jsonObject.getInt("numTE");
            for (int i = 0; i < numTE; i++) {
                entry.timeEvents.add(new TimeEvent(
                        jsonObject.getInt("TE"+i+"_0"),
                        intToEventType(jsonObject.getInt("TE"+i+"_1")),
                        intToCubeDropType(jsonObject.getInt("TE"+i+"_2"))
                ));
            }
            entry.comments = jsonObject.getString("comments");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return entry;
    }

    public static ArrayList<ArrayList<Integer>> getTimeEvents(Entry entry) {
        //Returns an array of ArrayLists of Floats, with each float in each different ArrayList referring to the time for a different event type.
        //Index Guide: 0 is None, 1 is Switch, 2 is Scale, 3 is Exchange
        //Index 4 is Climb Time
        int cubePickedTimeStamp = 0;
        ArrayList<ArrayList<Integer>> eventTimings = new ArrayList<ArrayList<Integer> >();
        for(int i = 0; i <=4; i++) {
            eventTimings.add(new ArrayList<Integer>());
        }

        for(int i = 0; i < entry.timeEvents.size(); i++) {
            switch (entry.timeEvents.get(i).type) {
                case CLIMB_START_10:
                    eventTimings.get(4).add(TOTAL_MATCH_TIME - entry.timeEvents.get(i).timestamp);
                    continue;
                case PICKED_CUBE_0:
                    cubePickedTimeStamp = entry.timeEvents.get(i).timestamp;
                    continue;
                case DROPPED_CUBE_1:
                    switch(entry.timeEvents.get(i).cubeDropType) {
                        case NONE_0:
                            eventTimings.get(0).add(entry.timeEvents.get(i).timestamp - cubePickedTimeStamp);
                            continue;
                        case ALLY_SWITCH_1:
                            eventTimings.get(1).add(entry.timeEvents.get(i).timestamp - cubePickedTimeStamp);
                            continue;
                        case OPP_SWITCH_2:
                            eventTimings.get(1).add(entry.timeEvents.get(i).timestamp - cubePickedTimeStamp);
                            continue;
                        case SCALE_3:
                            eventTimings.get(2).add(entry.timeEvents.get(i).timestamp - cubePickedTimeStamp);
                            continue;
                        case EXCHANGE_4:
                            eventTimings.get(3).add(entry.timeEvents.get(i).timestamp - cubePickedTimeStamp);
                            continue;
                    }
            }
        }

        return eventTimings;
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
            Entry entry = Entry.retrieveFromString(x);
            if(!entry.teamName.equals("")) {
                entryList.add(entry);
            }
        }

        return entryList;
    }

    @Override
    public int compareTo(@NonNull Entry o) {
        if(o.matchNumber > this.matchNumber) {
            return -1;
        } else if (o.matchNumber < this.matchNumber) {
            return 1;
        }
        return 0;
    }

    public static int eventTypeToInt(EventType eventType) {
        switch (eventType) {
            case PICKED_CUBE_0: return 0;
            case DROPPED_CUBE_1: return 1;
            case CLIMB_START_10: return 10;
        }
        return 0;
    }

    public static EventType intToEventType(int input) {
        switch (input) {
            case 0: return EventType.PICKED_CUBE_0;
            case 1: return EventType.DROPPED_CUBE_1;
            case 10: return EventType.CLIMB_START_10;
        }
        return EventType.PICKED_CUBE_0;
    }

    public static String getEventName(TimeEvent timeEvent) {
        switch (timeEvent.type) {
            case PICKED_CUBE_0: return "Gained Cube";
            case DROPPED_CUBE_1:
                switch(timeEvent.cubeDropType) {
                    case NONE_0: return "Dropped Cube (None)";
                    case ALLY_SWITCH_1: return "Dropped Cube (Ally Switch)";
                    case OPP_SWITCH_2: return "Dropped Cube (Opponent Switch)";
                    case SCALE_3: return "Dropped Cube (Scale)";
                    case EXCHANGE_4: return "Dropped Cube (Exchange)";
                }
            case CLIMB_START_10: return "Started Climbing";
        }
        return "";
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

    public static class TimeEvent implements Comparable<TimeEvent>{

        public int timestamp;
        public EventType type;
        public CubeDropType cubeDropType;

        public TimeEvent(int timestamp, EventType eventType, CubeDropType cubeDropType) {
            this.timestamp = timestamp;
            this.type = eventType;
            this.cubeDropType = cubeDropType;
        }

        @Override
        public int compareTo(@NonNull TimeEvent timeEvent) {
            if(timeEvent.timestamp > this.timestamp) {
                return -1;
            } else if (timeEvent.timestamp == this.timestamp) {
                return 0;
            } else {
                return 1;
            }
        }

    }

    public static String positionToString(int position) {
        switch (position) {
            case 0:
                return "Red 1";
            case 1:
                return "Red 2";
            case 2:
                return "Red 3";
            case 3:
                return "Blue 1";
            case 4:
                return "Blue 2";
            case 5:
                return "Blue 3";
        }
        return "";
    }
}
